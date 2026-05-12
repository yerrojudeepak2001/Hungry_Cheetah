package com.foodapp.order.messaging;

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
public class OrderCreatedMessage implements Serializable {
    private String orderId;
    private String customerId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String restaurantId;
    private String restaurantName;
    private String restaurantEmail;
    private BigDecimal orderTotal;
    private BigDecimal subtotal;
    private BigDecimal taxAmount;
    private BigDecimal deliveryFee;
    private BigDecimal serviceCharge;
    private List<OrderItem> items;
    private String deliveryAddress;
    private String paymentMethod;
    private String paymentStatus;
    private String specialInstructions;
    private String estimatedDeliveryTime;
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
        private BigDecimal unitPrice;
        private BigDecimal totalPrice;
        private String customizations;
        private String specialInstructions;
    }
}