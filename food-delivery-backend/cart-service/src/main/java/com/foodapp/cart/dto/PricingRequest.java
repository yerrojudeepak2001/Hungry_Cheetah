package com.foodapp.cart.dto;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PricingRequest {
    private String customerId;
    private String restaurantId;
    private List<PricingItem> items;
    private String couponCode;
    private String deliveryLocation;
    private String orderType; // DELIVERY, PICKUP
    
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PricingItem {
        private String itemId;
        private Integer quantity;
        private BigDecimal unitPrice;
        private String category;
    }
}