package com.foodapp.order.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PaymentRequest {
    private Long orderId;
    private BigDecimal amount;
    private String currency;
    private String paymentMethod;
    private String customerId;
}