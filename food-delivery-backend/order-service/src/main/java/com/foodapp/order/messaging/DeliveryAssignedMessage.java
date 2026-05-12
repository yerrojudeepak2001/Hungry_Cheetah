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
public class DeliveryAssignedMessage implements Serializable {
    private String orderId;
    private String customerId;
    private String customerEmail;
    private String driverId;
    private String driverName;
    private String driverPhone;
    private String vehicleInfo;
    private String estimatedPickupTime;
    private String estimatedDeliveryTime;
    private String trackingUrl;
    private Map<String, Object> metadata;
    private long timestamp;
}