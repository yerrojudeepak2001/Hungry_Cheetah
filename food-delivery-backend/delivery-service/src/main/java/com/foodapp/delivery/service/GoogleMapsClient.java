package com.foodapp.delivery.service;

import com.foodapp.delivery.config.GoogleMapsProperties;
import com.foodapp.delivery.dto.googlemaps.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoogleMapsClient {
    
    private static final Logger logger = LoggerFactory.getLogger(GoogleMapsClient.class);
    
    private final WebClient webClient;
    private final GoogleMapsProperties properties;
    
    public GoogleMapsClient(WebClient.Builder webClientBuilder, GoogleMapsProperties properties) {
        this.webClient = webClientBuilder
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024)) // 1MB
                .build();
        this.properties = properties;
    }
    
    /**
     * Calculate routes using Google Routes API v2
     */
    public Mono<RoutesResponse> calculateRoutes(RoutesRequest request) {
        logger.info("Calculating routes from {} to {}", 
                   getLocationDescription(request.getOrigin().getLocation()),
                   getLocationDescription(request.getDestination().getLocation()));
        
        return webClient.post()
                .uri(properties.getRoutes().getUrl())
                .header("Content-Type", "application/json")
                .header("X-Goog-Api-Key", properties.getApiKey())
                .header("X-Goog-FieldMask", 
                       "routes.duration,routes.distanceMeters,routes.polyline.encodedPolyline," +
                       "routes.legs.duration,routes.legs.distanceMeters,routes.legs.startLocation," +
                       "routes.legs.endLocation,routes.description,routes.warnings")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(RoutesResponse.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1))
                          .filter(throwable -> throwable instanceof WebClientResponseException &&
                                 ((WebClientResponseException) throwable).getStatusCode().is5xxServerError()))
                .doOnSuccess(response -> logger.info("Successfully calculated {} routes", 
                           response.getRoutes() != null ? response.getRoutes().size() : 0))
                .doOnError(error -> logger.error("Error calculating routes: {}", error.getMessage()));
    }
    
    /**
     * Calculate distance matrix using Google Distance Matrix API
     */
    public Mono<DistanceMatrixResponse> calculateDistanceMatrix(List<String> origins, 
                                                               List<String> destinations, 
                                                               String mode, 
                                                               boolean departureTime) {
        logger.info("Calculating distance matrix for {} origins and {} destinations", 
                   origins.size(), destinations.size());
        
        return webClient.get()
                .uri(uriBuilder -> {
                    uriBuilder = uriBuilder.path(properties.getDistanceMatrix().getUrl())
                            .queryParam("key", properties.getApiKey())
                            .queryParam("origins", String.join("|", origins))
                            .queryParam("destinations", String.join("|", destinations))
                            .queryParam("mode", mode != null ? mode : "driving")
                            .queryParam("units", "metric")
                            .queryParam("avoid", "tolls");
                    
                    if (departureTime) {
                        uriBuilder.queryParam("departure_time", "now")
                                 .queryParam("traffic_model", "best_guess");
                    }
                    
                    return uriBuilder.build();
                })
                .retrieve()
                .bodyToMono(DistanceMatrixResponse.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1))
                          .filter(throwable -> throwable instanceof WebClientResponseException &&
                                 ((WebClientResponseException) throwable).getStatusCode().is5xxServerError()))
                .doOnSuccess(response -> logger.info("Successfully calculated distance matrix with status: {}", 
                           response.getStatus()))
                .doOnError(error -> logger.error("Error calculating distance matrix: {}", error.getMessage()));
    }
    
    /**
     * Calculate optimized route for multiple stops
     */
    public Mono<RoutesResponse> calculateOptimizedRoute(Location origin, 
                                                       Location destination, 
                                                       List<Location> waypoints) {
        logger.info("Calculating optimized route with {} waypoints", waypoints.size());
        
        RoutesRequest request = new RoutesRequest();
        request.setOrigin(new RoutesRequest.Waypoint(origin));
        request.setDestination(new RoutesRequest.Waypoint(destination));
        
        if (waypoints != null && !waypoints.isEmpty()) {
            List<RoutesRequest.Waypoint> intermediates = waypoints.stream()
                    .map(RoutesRequest.Waypoint::new)
                    .collect(Collectors.toList());
            request.setIntermediates(intermediates);
        }
        
        request.setTravelMode("DRIVE");
        request.setRoutingPreference("TRAFFIC_AWARE");
        request.setComputeAlternativeRoutes(true);
        
        return calculateRoutes(request);
    }
    
    /**
     * Calculate delivery time estimate based on distance and traffic
     */
    public Mono<DeliveryEstimate> calculateDeliveryEstimate(double restaurantLat, 
                                                           double restaurantLng, 
                                                           double customerLat, 
                                                           double customerLng) {
        logger.info("Calculating delivery estimate from restaurant ({}, {}) to customer ({}, {})", 
                   restaurantLat, restaurantLng, customerLat, customerLng);
        
        Location origin = new Location(restaurantLat, restaurantLng);
        Location destination = new Location(customerLat, customerLng);
        
        RoutesRequest request = new RoutesRequest();
        request.setOrigin(new RoutesRequest.Waypoint(origin));
        request.setDestination(new RoutesRequest.Waypoint(destination));
        request.setTravelMode("DRIVE");
        request.setRoutingPreference("TRAFFIC_AWARE");
        
        return calculateRoutes(request)
                .map(this::convertToDeliveryEstimate)
                .doOnSuccess(estimate -> logger.info("Delivery estimate: {} minutes, {} meters", 
                           estimate.getEstimatedMinutes(), estimate.getDistanceMeters()));
    }
    
    /**
     * Find nearest delivery partners to a location
     */
    public Mono<List<DeliveryPartnerDistance>> findNearestDeliveryPartners(double latitude, 
                                                                           double longitude, 
                                                                           List<DeliveryPartnerLocation> partners) {
        logger.info("Finding nearest delivery partners to location ({}, {}) from {} partners", 
                   latitude, longitude, partners.size());
        
        String destination = latitude + "," + longitude;
        List<String> origins = partners.stream()
                .map(partner -> partner.getLatitude() + "," + partner.getLongitude())
                .collect(Collectors.toList());
        
        return calculateDistanceMatrix(origins, List.of(destination), "driving", true)
                .map(response -> convertToPartnerDistances(response, partners))
                .doOnSuccess(distances -> logger.info("Found {} partner distances", distances.size()));
    }
    
    private String getLocationDescription(Location location) {
        if (location.getAddress() != null) {
            return location.getAddress();
        } else if (location.getLatLng() != null) {
            return String.format("(%.6f, %.6f)", 
                   location.getLatLng().getLatitude(), 
                   location.getLatLng().getLongitude());
        }
        return "Unknown location";
    }
    
    private DeliveryEstimate convertToDeliveryEstimate(RoutesResponse response) {
        if (response.getRoutes() == null || response.getRoutes().isEmpty()) {
            throw new RuntimeException("No routes found");
        }
        
        RoutesResponse.Route route = response.getRoutes().get(0);
        
        DeliveryEstimate estimate = new DeliveryEstimate();
        estimate.setDistanceMeters(route.getDistanceMeters());
        
        // Parse duration from format "XXXs" to minutes
        String durationStr = route.getDuration();
        if (durationStr != null && durationStr.endsWith("s")) {
            int seconds = Integer.parseInt(durationStr.substring(0, durationStr.length() - 1));
            estimate.setEstimatedMinutes((int) Math.ceil(seconds / 60.0));
        }
        
        estimate.setTrafficCondition(determineTrafficCondition(route.getDuration(), route.getStaticDuration()));
        estimate.setRoutePolyline(route.getPolyline() != null ? route.getPolyline().getEncodedPolyline() : null);
        
        return estimate;
    }
    
    private String determineTrafficCondition(String duration, String staticDuration) {
        if (duration == null || staticDuration == null) {
            return "UNKNOWN";
        }
        
        try {
            int actualSeconds = Integer.parseInt(duration.replace("s", ""));
            int staticSeconds = Integer.parseInt(staticDuration.replace("s", ""));
            
            double ratio = (double) actualSeconds / staticSeconds;
            
            if (ratio <= 1.1) return "LIGHT";
            else if (ratio <= 1.3) return "MODERATE";
            else return "HEAVY";
        } catch (NumberFormatException e) {
            return "UNKNOWN";
        }
    }
    
    private List<DeliveryPartnerDistance> convertToPartnerDistances(DistanceMatrixResponse response, 
                                                                   List<DeliveryPartnerLocation> partners) {
        List<DeliveryPartnerDistance> result = new java.util.ArrayList<>();
        
        if (response.getRows() != null && !response.getRows().isEmpty()) {
            DistanceMatrixResponse.Row row = response.getRows().get(0);
            
            for (int i = 0; i < row.getElements().size() && i < partners.size(); i++) {
                DistanceMatrixResponse.Element element = row.getElements().get(i);
                DeliveryPartnerLocation partner = partners.get(i);
                
                if ("OK".equals(element.getStatus())) {
                    DeliveryPartnerDistance distance = new DeliveryPartnerDistance();
                    distance.setPartnerId(partner.getPartnerId());
                    distance.setPartnerName(partner.getPartnerName());
                    distance.setLatitude(partner.getLatitude());
                    distance.setLongitude(partner.getLongitude());
                    distance.setDistanceMeters(element.getDistance().getValue());
                    distance.setDurationSeconds(element.getDuration().getValue());
                    distance.setDistanceText(element.getDistance().getText());
                    distance.setDurationText(element.getDuration().getText());
                    
                    if (element.getDurationInTraffic() != null) {
                        distance.setDurationInTrafficSeconds(element.getDurationInTraffic().getValue());
                        distance.setDurationInTrafficText(element.getDurationInTraffic().getText());
                    }
                    
                    result.add(distance);
                }
            }
        }
        
        // Sort by distance
        result.sort((a, b) -> Integer.compare(a.getDistanceMeters(), b.getDistanceMeters()));
        
        return result;
    }
    
    // Helper classes
    public static class DeliveryEstimate {
        private int distanceMeters;
        private int estimatedMinutes;
        private String trafficCondition;
        private String routePolyline;
        
        // Getters and setters
        public int getDistanceMeters() { return distanceMeters; }
        public void setDistanceMeters(int distanceMeters) { this.distanceMeters = distanceMeters; }
        
        public int getEstimatedMinutes() { return estimatedMinutes; }
        public void setEstimatedMinutes(int estimatedMinutes) { this.estimatedMinutes = estimatedMinutes; }
        
        public String getTrafficCondition() { return trafficCondition; }
        public void setTrafficCondition(String trafficCondition) { this.trafficCondition = trafficCondition; }
        
        public String getRoutePolyline() { return routePolyline; }
        public void setRoutePolyline(String routePolyline) { this.routePolyline = routePolyline; }
    }
    
    public static class DeliveryPartnerLocation {
        private String partnerId;
        private String partnerName;
        private double latitude;
        private double longitude;
        
        // Constructors
        public DeliveryPartnerLocation() {}
        
        public DeliveryPartnerLocation(String partnerId, String partnerName, double latitude, double longitude) {
            this.partnerId = partnerId;
            this.partnerName = partnerName;
            this.latitude = latitude;
            this.longitude = longitude;
        }
        
        // Getters and setters
        public String getPartnerId() { return partnerId; }
        public void setPartnerId(String partnerId) { this.partnerId = partnerId; }
        
        public String getPartnerName() { return partnerName; }
        public void setPartnerName(String partnerName) { this.partnerName = partnerName; }
        
        public double getLatitude() { return latitude; }
        public void setLatitude(double latitude) { this.latitude = latitude; }
        
        public double getLongitude() { return longitude; }
        public void setLongitude(double longitude) { this.longitude = longitude; }
    }
    
    public static class DeliveryPartnerDistance extends DeliveryPartnerLocation {
        private int distanceMeters;
        private int durationSeconds;
        private String distanceText;
        private String durationText;
        private int durationInTrafficSeconds;
        private String durationInTrafficText;
        
        // Getters and setters
        public int getDistanceMeters() { return distanceMeters; }
        public void setDistanceMeters(int distanceMeters) { this.distanceMeters = distanceMeters; }
        
        public int getDurationSeconds() { return durationSeconds; }
        public void setDurationSeconds(int durationSeconds) { this.durationSeconds = durationSeconds; }
        
        public String getDistanceText() { return distanceText; }
        public void setDistanceText(String distanceText) { this.distanceText = distanceText; }
        
        public String getDurationText() { return durationText; }
        public void setDurationText(String durationText) { this.durationText = durationText; }
        
        public int getDurationInTrafficSeconds() { return durationInTrafficSeconds; }
        public void setDurationInTrafficSeconds(int durationInTrafficSeconds) { 
            this.durationInTrafficSeconds = durationInTrafficSeconds; 
        }
        
        public String getDurationInTrafficText() { return durationInTrafficText; }
        public void setDurationInTrafficText(String durationInTrafficText) { 
            this.durationInTrafficText = durationInTrafficText; 
        }
    }
}