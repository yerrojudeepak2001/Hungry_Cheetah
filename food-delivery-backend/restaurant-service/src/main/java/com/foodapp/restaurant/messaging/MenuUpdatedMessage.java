package com.foodapp.restaurant.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuUpdatedMessage implements Serializable {
    private String restaurantId;
    private String restaurantName;
    private String ownerEmail;
    private String updateType; // ITEM_ADDED, ITEM_REMOVED, ITEM_UPDATED, PRICE_CHANGED, AVAILABILITY_CHANGED
    private List<MenuItemUpdate> updates;
    private String updatedBy;
    private Map<String, Object> metadata;
    private long timestamp;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MenuItemUpdate implements Serializable {
        private String menuItemId;
        private String itemName;
        private String action; // ADDED, REMOVED, UPDATED
        private Map<String, Object> oldValues;
        private Map<String, Object> newValues;
    }
}