package com.foodapp.pricing.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurgePricingConfig {
    private Long restaurantId;
    private String area;
    private BigDecimal surgeMultiplier;
    private String reason; // HIGH_DEMAND, WEATHER, PEAK_HOURS, SPECIAL_EVENT
    private Boolean isActive;
    private Integer estimatedDuration; // in minutes
    private String description;
    private BigDecimal multiplier;
    private Integer threshold;
    private String timeWindow;
    private boolean active;
    private int activeOrders;
}