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
public class OrderCancelledMessage implements Serializable {
    private String orderId;
    private String customerId;
    private String customerEmail;
    private String restaurantId;
    private String restaurantEmail;
    private String cancellationReason;
    private String cancelledBy; // CUSTOMER, RESTAURANT, SYSTEM, ADMIN
    private BigDecimal refundAmount;
    private String refundMethod;
    private String refundStatus;
    private Map<String, Object> metadata;
    private long timestamp;
}