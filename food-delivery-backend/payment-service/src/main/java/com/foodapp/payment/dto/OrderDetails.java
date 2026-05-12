package com.foodapp.payment.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderDetails {
    private Long id;
    private Long userId;
    private Long restaurantId;
    private BigDecimal totalAmount;
    private String status;
}