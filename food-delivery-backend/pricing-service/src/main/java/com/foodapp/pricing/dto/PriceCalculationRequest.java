package com.foodapp.pricing.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceCalculationRequest {
    private Long restaurantId;
    private List<CartItem> items;
    private String deliveryAddress;
    private Double latitude;
    private Double longitude;
    private String promoCode;
    private Long userId;
    private String orderType; // DELIVERY, PICKUP
    private String deliveryTimeSlot;
    private String customerId;
    private BigDecimal subTotal;
    private String timeSlot;
    private String area;
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CartItem {
        private Long menuItemId;
        private String name;
        private BigDecimal basePrice;
        private Integer quantity;
        private List<String> customizations;
    }
}