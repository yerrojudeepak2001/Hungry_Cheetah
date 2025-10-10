package com.foodapp.restaurant.dto;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KitchenOrder {
    private Long orderId;
    private Long restaurantId;
    private List<KitchenOrderItem> items;
    private String priority; // HIGH, NORMAL, LOW
    private String status; // RECEIVED, PREPARING, READY, COMPLETED
    private Integer estimatedPrepTime;
    private java.time.LocalDateTime orderTime;
    private String specialInstructions;
    
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class KitchenOrderItem {
        private Long itemId;
        private String itemName;
        private Integer quantity;
        private String customizations;
        private String status;
    }
}