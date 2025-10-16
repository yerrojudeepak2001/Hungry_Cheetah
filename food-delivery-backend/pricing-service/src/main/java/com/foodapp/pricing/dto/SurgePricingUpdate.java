package com.foodapp.pricing.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurgePricingUpdate {
    private Long restaurantId;
    private BigDecimal newSurgeMultiplier;
    private String reason;
    private Integer durationMinutes;
    private String area;
}