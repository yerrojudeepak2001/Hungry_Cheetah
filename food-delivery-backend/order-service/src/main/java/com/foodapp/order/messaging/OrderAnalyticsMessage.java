package com.foodapp.order.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderAnalyticsMessage implements Serializable {
    private String eventType; // ORDER_CREATED, ORDER_COMPLETED, ORDER_CANCELLED, etc.
    private String orderId;
    private String customerId;
    private String restaurantId;
    private String driverId; // optional
    private BigDecimal orderValue;
    private String paymentMethod;
    private String deliveryMethod; // DELIVERY, PICKUP
    private int preparationTime; // in minutes
    private int deliveryTime; // in minutes
    private String orderSource; // WEB, MOBILE, API
    private Map<String, Object> eventData;
    private long timestamp;
}