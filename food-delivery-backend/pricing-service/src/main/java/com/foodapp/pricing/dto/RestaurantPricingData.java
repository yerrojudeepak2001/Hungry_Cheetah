package com.foodapp.pricing.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantPricingData {
    private Long restaurantId;
    private String name;
    private BigDecimal baseDeliveryFee;
    private BigDecimal serviceFeePercentage;
    private BigDecimal minimumOrderAmount;
    private Boolean isSurgePricingEnabled;
    private BigDecimal currentSurgeMultiplier;
    private String priceCategory; // BUDGET, MODERATE, PREMIUM
}