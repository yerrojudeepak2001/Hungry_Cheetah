package com.foodapp.order.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusChangedMessage implements Serializable {
    private String orderId;
    private String customerId;
    private String customerEmail;
    private String restaurantId;
    private String restaurantEmail;
    private String oldStatus;
    private String newStatus; // PENDING, CONFIRMED, PREPARING, READY, OUT_FOR_DELIVERY, DELIVERED, CANCELLED
    private String statusMessage;
    private String updatedBy; // CUSTOMER, RESTAURANT, DRIVER, SYSTEM, ADMIN
    private String driverId; // if applicable
    private String estimatedTime; // if applicable
    private Map<String, Object> metadata;
    private long timestamp;
}