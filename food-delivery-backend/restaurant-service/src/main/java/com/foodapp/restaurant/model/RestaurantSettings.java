package com.foodapp.restaurant.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
import java.time.LocalTime;

@Data
@Document(collection = "restaurant_settings")
public class RestaurantSettings {
    @Id
    private String id;
    private Long restaurantId;
    
    // Operating Hours
    private List<OperatingHours> regularHours;
    private List<OperatingHours> specialHours; // holidays, events
    private List<String> holidays;
    
    // Service Settings
    private Boolean isDeliveryEnabled;
    private Boolean isPickupEnabled;
    private Boolean isDineInEnabled;
    private Integer maxDeliveryRadius; // in kilometers
    private Integer prepTime; // average in minutes
    
    // Menu Settings
    private Boolean automaticMenuPublishing;
    private List<String> availableCuisines;
    private Boolean customizationEnabled;
    
    // Delivery Settings
    private Double minimumOrderAmount;
    private Double deliveryFee;
    private Boolean freeDeliveryEnabled;
    private Double freeDeliveryThreshold;
    
    // Commission Settings
    private Double platformCommissionRate;
    private Double deliveryCommissionRate;
    
    // Marketing Settings
    private Boolean promotionsEnabled;
    private List<String> activePromotions;
    private Boolean featuredRestaurant;
    
    // Quality Control
    private Double minimumRatingThreshold;
    private Integer maxConcurrentOrders;
    private Boolean autoAcceptOrders;
    
    // Integration Settings
    private Boolean posIntegrationEnabled;
    private String posSystem;
    private String printerSettings;
    
    @Data
    public static class OperatingHours {
        private String dayOfWeek;
        private LocalTime openTime;
        private LocalTime closeTime;
        private Boolean isClosed;
    }
}