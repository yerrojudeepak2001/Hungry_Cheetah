package com.foodapp.order.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DeliveryResponse {
    private Long deliveryId;
    private Long orderId;
    private String status;
    private String driverName;
    private String driverPhone;
    private String vehicleDetails;
    private LocalDateTime estimatedDeliveryTime;
    private LocalDateTime actualDeliveryTime;
    private String trackingLink;
}