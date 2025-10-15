package com.foodapp.payment.dto;

import lombok.Data;
import com.foodapp.payment.model.PaymentMethod;
import java.util.List;

@Data
public class UserPaymentInfo {
    private Long userId;
    private List<PaymentMethod> paymentMethods;
}