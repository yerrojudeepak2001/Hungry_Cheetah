package com.foodapp.delivery.controller;

import com.foodapp.common.dto.ApiResponse;
import com.foodapp.delivery.service.DeliveryService;
import com.foodapp.delivery.service.GoogleMapsClient;
import com.foodapp.delivery.dto.RouteOptimizationRequest;
import com.foodapp.delivery.dto.googlemaps.DistanceMatrixResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/delivery/maps")
public class GoogleMapsController {

    private final DeliveryService deliveryService;
    private final GoogleMapsClient googleMapsClient;

    public GoogleMapsController(DeliveryService deliveryService,
                              GoogleMapsClient googleMapsClient) {
        this.deliveryService = deliveryService;
        this.googleMapsClient = googleMapsClient;
    }

    /**
     * Calculate delivery estimate using Google Maps Routes API
     */
    @PostMapping("/estimate")
    public Mono<ResponseEntity<ApiResponse<Map<String, Object>>>> calculateDeliveryEstimate(
            @RequestParam double restaurantLat,
            @RequestParam double restaurantLng,
            @RequestParam double customerLat,
            @RequestParam double customerLng) {
        
        return googleMapsClient.calculateDeliveryEstimate(restaurantLat, restaurantLng, customerLat, customerLng)
                .map(estimate -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("estimatedMinutes", estimate.getEstimatedMinutes());
                    response.put("distanceMeters", estimate.getDistanceMeters());
                    response.put("trafficCondition", estimate.getTrafficCondition());
                    response.put("routePolyline", estimate.getRoutePolyline());
                    
                    return ResponseEntity.ok(new ApiResponse<>(true, 
                            "Delivery estimate calculated successfully", response));
                })
                .onErrorReturn(ResponseEntity.badRequest()
                        .body(new ApiResponse<>(false, "Error calculating delivery estimate", null)));
    }

    /**
     * Calculate distance matrix between multiple origins and destinations
     */
    @PostMapping("/distance-matrix")
    public Mono<ResponseEntity<ApiResponse<DistanceMatrixResponse>>> calculateDistanceMatrix(
            @RequestBody DistanceMatrixRequest request) {
        
        return googleMapsClient.calculateDistanceMatrix(
                        request.getOrigins(),
                        request.getDestinations(),
                        request.getMode(),
                        request.isIncludeTraffic())
                .map(response -> ResponseEntity.ok(new ApiResponse<>(true, 
                        "Distance matrix calculated successfully", response)))
                .onErrorReturn(ResponseEntity.badRequest()
                        .body(new ApiResponse<>(false, "Error calculating distance matrix", null)));
    }

    /**
     * Optimize route for multiple deliveries
     */
    @PostMapping("/optimize-route")
    public Mono<ResponseEntity<ApiResponse<DeliveryService.OptimizedRoute>>> optimizeRoute(
            @RequestBody RouteOptimizationRequest request) {
        
        return deliveryService.optimizeMultipleDeliveries(
                        request.getPartnerId(),
                        request.getDeliveries())
                .map(optimizedRoute -> ResponseEntity.ok(new ApiResponse<>(true, 
                        "Route optimized successfully", optimizedRoute)))
                .onErrorReturn(ResponseEntity.badRequest()
                        .body(new ApiResponse<>(false, "Error optimizing route", null)));
    }

    /**
     * Get real-time traffic conditions for a route
     */
    @GetMapping("/traffic")
    public Mono<ResponseEntity<ApiResponse<Map<String, Object>>>> getTrafficConditions(
            @RequestParam double originLat,
            @RequestParam double originLng,
            @RequestParam double destLat,
            @RequestParam double destLng) {
        
        return googleMapsClient.calculateDeliveryEstimate(originLat, originLng, destLat, destLng)
                .map(estimate -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("trafficCondition", estimate.getTrafficCondition());
                    response.put("estimatedMinutes", estimate.getEstimatedMinutes());
                    response.put("distanceMeters", estimate.getDistanceMeters());
                    
                    return ResponseEntity.ok(new ApiResponse<>(true, 
                            "Traffic conditions retrieved successfully", response));
                })
                .onErrorReturn(ResponseEntity.badRequest()
                        .body(new ApiResponse<>(false, "Error getting traffic conditions", null)));
    }

    /**
     * Calculate ETA for active delivery
     */
    @GetMapping("/deliveries/{deliveryId}/eta")
    public Mono<ResponseEntity<ApiResponse<Map<String, Object>>>> calculateDeliveryETA(
            @PathVariable Long deliveryId) {
        
        try {
            var delivery = deliveryService.getDelivery(deliveryId);
            
            return deliveryService.calculateDeliveryEstimate(delivery)
                    .map(estimate -> {
                        Map<String, Object> response = new HashMap<>();
                        response.put("deliveryId", deliveryId);
                        response.put("estimatedMinutes", estimate.getEstimatedMinutes());
                        response.put("distanceMeters", estimate.getDistanceMeters());
                        response.put("trafficCondition", estimate.getTrafficCondition());
                        
                        return ResponseEntity.ok(new ApiResponse<>(true, 
                                "ETA calculated successfully", response));
                    })
                    .onErrorReturn(ResponseEntity.badRequest()
                            .body(new ApiResponse<>(false, "Error calculating ETA", null)));
                            
        } catch (Exception e) {
            return Mono.just(ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Delivery not found: " + e.getMessage(), null)));
        }
    }

    /**
     * Health check endpoint for Google Maps integration
     */
    @GetMapping("/health")
    public ResponseEntity<ApiResponse<Map<String, Object>>> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "Google Maps Integration");
        response.put("features", List.of("Routes API", "Distance Matrix API", "Traffic Conditions"));
        
        return ResponseEntity.ok(new ApiResponse<>(true, 
                "Google Maps integration is healthy", response));
    }

    // Helper DTO for distance matrix request
    public static class DistanceMatrixRequest {
        private List<String> origins;
        private List<String> destinations;
        private String mode = "driving";
        private boolean includeTraffic = true;
        
        // Getters and setters
        public List<String> getOrigins() { return origins; }
        public void setOrigins(List<String> origins) { this.origins = origins; }
        
        public List<String> getDestinations() { return destinations; }
        public void setDestinations(List<String> destinations) { this.destinations = destinations; }
        
        public String getMode() { return mode; }
        public void setMode(String mode) { this.mode = mode; }
        
        public boolean isIncludeTraffic() { return includeTraffic; }
        public void setIncludeTraffic(boolean includeTraffic) { this.includeTraffic = includeTraffic; }
    }
}