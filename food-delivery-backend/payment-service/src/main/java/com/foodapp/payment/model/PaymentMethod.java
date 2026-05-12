package com.foodapp.payment.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethod {
    private Long userId;
    private String type;
    private String details;
    private boolean isDefault;
}