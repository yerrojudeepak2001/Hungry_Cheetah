package com.foodapp.pricing.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPricingInfo {
    private Long orderId;
    private BigDecimal subTotal;
    private BigDecimal deliveryFee;
    private BigDecimal serviceFee;
    private BigDecimal taxes;
    private BigDecimal discount;
    private BigDecimal total;
    private String status;
}