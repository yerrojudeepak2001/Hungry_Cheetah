package com.foodapp.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartValidationResult {
    
    private Boolean isValid;
    private String overallStatus; // VALID, WARNING, ERROR
    private List<ValidationError> errors;
    private List<ValidationWarning> warnings;
    private List<ValidationInfo> infoMessages;
    
    // Price validation
    private Boolean pricesChanged;
    private BigDecimal originalTotal;
    private BigDecimal updatedTotal;
    private BigDecimal priceDifference;
    
    // Availability validation
    private Boolean allItemsAvailable;
    private List<String> unavailableItems;
    private List<ItemAvailability> itemAvailabilities;
    
    // Restaurant validation
    private Boolean allRestaurantsOpen;
    private List<RestaurantStatus> restaurantStatuses;
    
    // Inventory validation
    private Boolean sufficientStock;
    private Map<String, Integer> stockLevels;
    
    // Delivery validation
    private Boolean deliveryAvailable;
    private String deliveryStatus;
    private LocalDateTime earliestDelivery;
    private LocalDateTime latestDelivery;
    
    // Additional validation info
    private Map<String, Object> additionalInfo;
    private LocalDateTime validatedAt;
    private String validatorVersion;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ValidationError {
        private String code;
        private String message;
        private String field;
        private String itemId;
        private String severity; // HIGH, MEDIUM, LOW
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ValidationWarning {
        private String code;
        private String message;
        private String field;
        private String itemId;
        private String recommendation;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ValidationInfo {
        private String message;
        private String type; // PRICE_UPDATE, STOCK_INFO, DELIVERY_INFO, etc.
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemAvailability {
        private String menuItemId;
        private String itemName;
        private Boolean isAvailable;
        private Integer availableStock;
        private String reason; // OUT_OF_STOCK, DISCONTINUED, TEMPORARILY_UNAVAILABLE
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RestaurantStatus {
        private String restaurantId;
        private String restaurantName;
        private Boolean isOpen;
        private Boolean acceptingOrders;
        private String status; // OPEN, CLOSED, BUSY, DELIVERY_ONLY
        private LocalDateTime nextOpenTime;
    }
}