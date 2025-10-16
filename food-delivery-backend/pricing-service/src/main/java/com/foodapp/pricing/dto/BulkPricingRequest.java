package com.foodapp.pricing.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BulkPricingRequest {
    private Long restaurantId;
    private List<BulkOrderItem> items;
    private String deliveryAddress;
    private String promoCode;
    private Long userId;
    private Integer orderQuantity;
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BulkOrderItem {
        private Long menuItemId;
        private String name;
        private BigDecimal basePrice;
        private Integer quantity;
        private BigDecimal bulkDiscountPercentage;
    }
}