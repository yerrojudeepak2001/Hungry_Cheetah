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
public class PaymentProcessedMessage implements Serializable {
    private String orderId;
    private String paymentId;
    private String customerId;
    private String paymentMethod;
    private BigDecimal amount;
    private String currency;
    private String paymentStatus; // SUCCESS, FAILED, PENDING, REFUNDED
    private String transactionId;
    private String gatewayResponse;
    private Map<String, Object> paymentDetails;
    private long timestamp;
}