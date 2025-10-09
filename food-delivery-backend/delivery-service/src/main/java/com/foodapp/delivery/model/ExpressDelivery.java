package com.foodapp.delivery.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

@Data
@Document(collection = "express_deliveries")
public class ExpressDelivery {
    @Id
    private String id;
    private Long orderId;
    private String expressType; // FOOD, GROCERY, MEDICINE, PACKAGE
    private String priority; // SUPER_EXPRESS, EXPRESS, STANDARD
    
    // Time Windows
    private Integer promisedDeliveryMinutes;
    private LocalDateTime orderTime;
    private LocalDateTime pickupTime;
    private LocalDateTime deliveryDeadline;
    
    // Location
    private Location pickupLocation;
    private Location dropLocation;
    private Double distance;
    private String routeOptimization;
    
    // Assignment
    private Long driverId;
    private String assignmentStatus;
    private Integer nearbyDriversCount;
    private List<DriverMatch> potentialDrivers;
    
    // Pricing
    private BigDecimal basePrice;
    private BigDecimal distancePrice;
    private BigDecimal priorityPrice;
    private BigDecimal totalPrice;
    
    // Tracking
    private String currentStatus;
    private Location currentLocation;
    private Double completedDistance;
    private Integer remainingMinutes;
    private List<StatusUpdate> statusUpdates;
    
    @Data
    public static class Location {
        private String address;
        private Double latitude;
        private Double longitude;
        private String landmark;
        private String accessInstructions;
    }
    
    @Data
    public static class DriverMatch {
        private Long driverId;
        private Double distance;
        private Integer estimatedMinutes;
        private Double rating;
        private String vehicleType;
        private Boolean isPreferred;
    }
    
    @Data
    public static class StatusUpdate {
        private LocalDateTime timestamp;
        private String status;
        private Location location;
        private String description;
        private String driverNote;
    }
}