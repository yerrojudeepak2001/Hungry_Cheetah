package com.foodapp.payment.dto.stripe;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreatePaymentIntentRequest {
    private BigDecimal amount;
    private String currency = "usd";
    private String customerId;
    private String description;
    private Long orderId;
}