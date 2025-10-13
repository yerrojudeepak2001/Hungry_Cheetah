package com.foodapp.cart.dto;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryCheck {
    private List<ItemCheck> items;
    private String restaurantId;
    private String sessionId;
    
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemCheck {
        private String itemId;
        private Integer quantity;
        private String restaurantId;
    }
}