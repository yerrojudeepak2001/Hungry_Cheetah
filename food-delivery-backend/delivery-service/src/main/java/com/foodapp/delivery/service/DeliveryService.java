package com.foodapp.delivery.service;

import com.foodapp.delivery.model.Delivery;
import com.foodapp.delivery.model.DeliveryPartner;
import com.foodapp.delivery.model.DeliveryStatus;
import com.foodapp.delivery.repository.DeliveryRepository;
import com.foodapp.delivery.repository.DeliveryPartnerRepository;
import com.foodapp.common.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliveryService {
    private static final Logger logger = LoggerFactory.getLogger(DeliveryService.class);
    
    private final DeliveryRepository deliveryRepository;
    private final DeliveryPartnerRepository partnerRepository;
    private final GoogleMapsClient googleMapsClient;

    public DeliveryService(DeliveryRepository deliveryRepository,
                          DeliveryPartnerRepository partnerRepository,
                          GoogleMapsClient googleMapsClient) {
        this.deliveryRepository = deliveryRepository;
        this.partnerRepository = partnerRepository;
        this.googleMapsClient = googleMapsClient;
    }

    /**
     * Calculate delivery estimate using Google Maps
     */
    public Mono<GoogleMapsClient.DeliveryEstimate> calculateDeliveryEstimate(Delivery delivery) {
        return googleMapsClient.calculateDeliveryEstimate(
                delivery.getPickupLatitude(),
                delivery.getPickupLongitude(),
                delivery.getDeliveryLatitude(),
                delivery.getDeliveryLongitude()
        );
    }

    /**
     * Find optimal delivery partner using Google Distance Matrix
     */
    public Mono<DeliveryPartner> findOptimalDeliveryPartner(Delivery delivery) {
        logger.info("Finding optimal delivery partner for delivery location: ({}, {})", 
                   delivery.getDeliveryLatitude(), delivery.getDeliveryLongitude());
        
        List<DeliveryPartner> availablePartners = partnerRepository.findByIsAvailableTrue();
        
        if (availablePartners.isEmpty()) {
            return Mono.error(new RuntimeException("No available delivery partners"));
        }
        
        // Convert to GoogleMapsClient format
        List<GoogleMapsClient.DeliveryPartnerLocation> partnerLocations = availablePartners.stream()
                .map(partner -> new GoogleMapsClient.DeliveryPartnerLocation(
                        partner.getId().toString(),
                        partner.getName(),
                        partner.getCurrentLatitude(),
                        partner.getCurrentLongitude()
                ))
                .collect(Collectors.toList());
        
        return googleMapsClient.findNearestDeliveryPartners(
                        delivery.getPickupLatitude(),
                        delivery.getPickupLongitude(),
                        partnerLocations
                )
                .map(distances -> {
                    if (distances.isEmpty()) {
                        throw new RuntimeException("No delivery partners found for the location");
                    }
                    
                    // Find the partner with minimum distance/time
                    GoogleMapsClient.DeliveryPartnerDistance nearest = distances.get(0);
                    Long partnerId = Long.parseLong(nearest.getPartnerId());
                    
                    DeliveryPartner optimalPartner = availablePartners.stream()
                            .filter(partner -> partner.getId().equals(partnerId))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Partner not found"));
                    
                    logger.info("Selected optimal partner: {} at distance: {} meters", 
                              optimalPartner.getName(), nearest.getDistanceMeters());
                    
                    return optimalPartner;
                });
    }

    /**
     * Calculate optimized route for multiple deliveries
     */
    public Mono<OptimizedRoute> optimizeMultipleDeliveries(Long partnerId, List<Delivery> deliveries) {
        logger.info("Optimizing route for partner {} with {} deliveries", partnerId, deliveries.size());
        
        DeliveryPartner partner = partnerRepository.findById(partnerId)
                .orElseThrow(() -> new ResourceNotFoundException("Partner not found"));
        
        // Create origin (partner's current location)
        com.foodapp.delivery.dto.googlemaps.Location origin = 
                new com.foodapp.delivery.dto.googlemaps.Location(
                        partner.getCurrentLatitude(), 
                        partner.getCurrentLongitude());
        
        // Create waypoints for pickups and deliveries
        List<com.foodapp.delivery.dto.googlemaps.Location> waypoints = deliveries.stream()
                .flatMap(delivery -> List.of(
                        new com.foodapp.delivery.dto.googlemaps.Location(
                                delivery.getPickupLatitude(), 
                                delivery.getPickupLongitude()),
                        new com.foodapp.delivery.dto.googlemaps.Location(
                                delivery.getDeliveryLatitude(), 
                                delivery.getDeliveryLongitude())
                ).stream())
                .collect(Collectors.toList());
        
        if (waypoints.isEmpty()) {
            return Mono.error(new RuntimeException("No waypoints to optimize"));
        }
        
        // Use last delivery location as destination
        com.foodapp.delivery.dto.googlemaps.Location destination = waypoints.remove(waypoints.size() - 1);
        
        return googleMapsClient.calculateOptimizedRoute(origin, destination, waypoints)
                .map(response -> convertToOptimizedRoute(response, deliveries));
    }

    @Transactional
    public Delivery updateDeliveryStatus(Long deliveryId, DeliveryStatus status) {
        Delivery delivery = getDelivery(deliveryId);
        delivery.setStatus(status);
        
        // Update timestamps based on status
        updateDeliveryTimestamps(delivery, status);
        
        // Save delivery
        Delivery updatedDelivery = deliveryRepository.save(delivery);
        
        // If completed, update partner status
        if (status == DeliveryStatus.COMPLETED) {
            completeDelivery(delivery);
        }
        
        return updatedDelivery;
    }

    public Delivery getDelivery(Long deliveryId) {
        return deliveryRepository.findById(deliveryId)
            .orElseThrow(() -> new ResourceNotFoundException("Delivery not found with id: " + deliveryId));
    }

    private void updateDeliveryTimestamps(Delivery delivery, DeliveryStatus status) {
        switch (status) {
            case ASSIGNED:
                // No specific timestamp for assignment
                break;
            case PICKED_UP:
                delivery.setPickupTime(LocalDateTime.now());
                break;
            case IN_TRANSIT:
                // No specific timestamp for in transit
                break;
            case COMPLETED:
                delivery.setDeliveryTime(LocalDateTime.now());
                break;
            case CANCELLED:
                // Could add cancellation time if needed
                break;
        }
    }

    private void completeDelivery(Delivery delivery) {
        DeliveryPartner partner = partnerRepository.findById(delivery.getDeliveryPartnerId())
            .orElseThrow(() -> new ResourceNotFoundException("Partner not found"));
            
        partner.setActiveDeliveries(partner.getActiveDeliveries() - 1);
        partner.setTotalDeliveries(partner.getTotalDeliveries() + 1);
        partner.setIsAvailable(true);
        
        partnerRepository.save(partner);
    }

    private OptimizedRoute convertToOptimizedRoute(com.foodapp.delivery.dto.googlemaps.RoutesResponse response, List<Delivery> deliveries) {
        OptimizedRoute route = new OptimizedRoute();
        
        if (response.getRoutes() != null && !response.getRoutes().isEmpty()) {
            com.foodapp.delivery.dto.googlemaps.RoutesResponse.Route firstRoute = response.getRoutes().get(0);
            
            route.setTotalDistanceMeters(firstRoute.getDistanceMeters());
            route.setTotalDurationMinutes(parseDurationToMinutes(firstRoute.getDuration()));
            route.setRoutePolyline(firstRoute.getPolyline() != null ? 
                                   firstRoute.getPolyline().getEncodedPolyline() : null);
            route.setDeliveries(deliveries);
            route.setOptimized(true);
        }
        
        return route;
    }

    private int parseDurationToMinutes(String duration) {
        if (duration != null && duration.endsWith("s")) {
            try {
                int seconds = Integer.parseInt(duration.substring(0, duration.length() - 1));
                return (int) Math.ceil(seconds / 60.0);
            } catch (NumberFormatException e) {
                logger.warn("Could not parse duration: {}", duration);
            }
        }
        return 0;
    }

    // Helper class for optimized route response
    public static class OptimizedRoute {
        private List<Delivery> deliveries;
        private int totalDistanceMeters;
        private int totalDurationMinutes;
        private String routePolyline;
        private boolean optimized;
        
        // Getters and setters
        public List<Delivery> getDeliveries() { return deliveries; }
        public void setDeliveries(List<Delivery> deliveries) { this.deliveries = deliveries; }
        
        public int getTotalDistanceMeters() { return totalDistanceMeters; }
        public void setTotalDistanceMeters(int totalDistanceMeters) { this.totalDistanceMeters = totalDistanceMeters; }
        
        public int getTotalDurationMinutes() { return totalDurationMinutes; }
        public void setTotalDurationMinutes(int totalDurationMinutes) { this.totalDurationMinutes = totalDurationMinutes; }
        
        public String getRoutePolyline() { return routePolyline; }
        public void setRoutePolyline(String routePolyline) { this.routePolyline = routePolyline; }
        
        public boolean isOptimized() { return optimized; }
        public void setOptimized(boolean optimized) { this.optimized = optimized; }
    }
}