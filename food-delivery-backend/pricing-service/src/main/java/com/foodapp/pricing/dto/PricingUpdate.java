package com.foodapp.pricing.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PricingUpdate {
    private Long orderId;
    private BigDecimal newTotal;
    private String reason;
    private String updateType; // SURGE_PRICING, DISCOUNT_APPLIED, DELIVERY_FEE_CHANGE
}