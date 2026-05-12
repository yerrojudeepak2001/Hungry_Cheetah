package com.foodapp.payment.dto.stripe;

import lombok.Data;

@Data
public class ConfirmPaymentRequest {
    private String paymentMethodId;
    private String returnUrl;
}