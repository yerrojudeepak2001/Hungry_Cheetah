package com.foodapp.payment.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class TaxCalculationRequest {
    private BigDecimal amount;
    private String region;
}