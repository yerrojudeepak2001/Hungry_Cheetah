package com.foodapp.notification.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetails {
    private String orderId;
    private Long userId;
    private String status;
    private Double totalAmount;
    private String restaurantName;
    private List<String> items;
    private String deliveryAddress;
    private LocalDateTime orderDate;
    private LocalDateTime estimatedDeliveryTime;
    private String driverName;
    private String driverPhone;
}