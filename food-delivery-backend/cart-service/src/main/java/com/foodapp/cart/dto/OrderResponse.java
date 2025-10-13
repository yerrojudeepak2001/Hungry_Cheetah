package com.foodapp.cart.dto;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private String orderId;
    private String customerId;
    private String restaurantId;
    private String status;
    private BigDecimal totalAmount;
    private Integer estimatedPreparationTime;
    private String paymentStatus;
    private String message;
    private java.time.LocalDateTime orderTime;
    private String trackingNumber;
}