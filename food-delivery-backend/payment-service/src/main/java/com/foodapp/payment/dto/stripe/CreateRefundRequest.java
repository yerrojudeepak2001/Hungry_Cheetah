package com.foodapp.payment.dto.stripe;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreateRefundRequest {
    private String paymentIntentId;
    private BigDecimal amount; // If null, full refund
    private String reason = "requested_by_customer"; // requested_by_customer, duplicate, fraudulent
    private String orderId;
}