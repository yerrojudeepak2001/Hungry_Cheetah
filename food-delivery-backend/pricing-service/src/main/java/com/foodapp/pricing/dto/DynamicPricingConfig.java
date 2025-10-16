package com.foodapp.pricing.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DynamicPricingConfig {
    private Long restaurantId;
    private BigDecimal surgeMultiplier;
    private BigDecimal demandMultiplier;
    private BigDecimal weatherMultiplier;
    private BigDecimal timeMultiplier;
    private Boolean isEnabled;
    private String strategy; // DEMAND_BASED, TIME_BASED, WEATHER_BASED
    private Integer maxSurgePercentage;
    private Integer minOrderThreshold;
    private String area;
    private LocalDateTime timestamp;
    private SurgePricingConfig surgeConfig;
    private WeatherImpact weatherImpact;
}