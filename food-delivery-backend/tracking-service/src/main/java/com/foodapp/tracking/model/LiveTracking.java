package com.foodapp.tracking.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "live_tracking")
public class LiveTracking {
    @Id
    private String id;
    private Long orderId;
    private Long userId;
    private Long driverId;
    private String orderStatus;
    
    // Location Data
    private Location currentLocation;
    private Location restaurantLocation;
    private Location deliveryLocation;
    private List<Location> routeWaypoints;
    
    // Time Estimates
    private LocalDateTime orderPlacedTime;
    private LocalDateTime restaurantAcceptTime;
    private LocalDateTime foodReadyTime;
    private LocalDateTime pickupTime;
    private LocalDateTime estimatedDeliveryTime;
    private Integer remainingTime;
    
    // Route Details
    private Double totalDistance;
    private Double completedDistance;
    private Integer estimatedDuration;
    private String routePolyline;
    private List<String> alternateRoutes;
    
    // Status Updates
    private List<StatusUpdate> statusHistory;
    private String currentActivity;
    private Boolean isDelayed;
    private String delayReason;
    
    // Driver Details
    private String driverName;
    private String vehicleType;
    private String vehicleNumber;
    private String driverPhoto;
    private Double driverRating;
    
    // Communication
    private Boolean isChatEnabled;
    private List<ChatMessage> chatHistory;
    private List<String> predefinedMessages;
    
    @Data
    public static class Location {
        private Double latitude;
        private Double longitude;
        private String address;
        private LocalDateTime timestamp;
        private Double accuracy;
        private Double speed;
        private Double bearing;
    }
    
    @Data
    public static class StatusUpdate {
        private LocalDateTime timestamp;
        private String status;
        private String description;
        private Location location;
        private Map<String, Object> metadata;
    }
    
    @Data
    public static class ChatMessage {
        private LocalDateTime timestamp;
        private String sender; // USER, DRIVER, SYSTEM
        private String message;
        private String messageType; // TEXT, PHOTO, LOCATION
        private Boolean isRead;
        private String deliveryStatus;
    }
    
    // ETA Calculation
    private Map<String, Object> etaFactors;
    private List<String> delayFactors;
    private Boolean trafficConsidered;
    private String trafficCondition;
    private Integer trafficDelay;
    
    // Safety & Quality
    private Boolean contactlessDelivery;
    private Boolean temperatureMonitored;
    private List<String> safetyChecklist;
    private Map<String, Object> qualityMetrics;
}