package com.foodapp.restaurant.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderReceivedMessage implements Serializable {
    private String orderId;
    private String restaurantId;
    private String restaurantName;
    private String restaurantEmail;
    private String customerId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private BigDecimal orderTotal;
    private List<OrderItem> items;
    private String deliveryAddress;
    private String specialInstructions;
    private String paymentMethod;
    private Map<String, Object> metadata;
    private long timestamp;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItem implements Serializable {
        private String menuItemId;
        private String itemName;
        private int quantity;
        private BigDecimal price;
        private String customizations;
    }
}