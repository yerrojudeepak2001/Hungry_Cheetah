package com.foodapp.payment.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class TaxCalculationResponse {
    private BigDecimal taxAmount;
    private BigDecimal totalAmount;
}