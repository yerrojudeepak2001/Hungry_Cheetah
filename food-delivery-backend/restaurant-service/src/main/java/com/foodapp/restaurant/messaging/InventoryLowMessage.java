package com.foodapp.restaurant.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryLowMessage implements Serializable {
    private String restaurantId;
    private String restaurantName;
    private String ownerEmail;
    private List<LowStockItem> lowStockItems;
    private String alertLevel; // WARNING, CRITICAL
    private long timestamp;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LowStockItem implements Serializable {
        private String menuItemId;
        private String itemName;
        private int currentStock;
        private int minimumStock;
        private String category;
    }
}