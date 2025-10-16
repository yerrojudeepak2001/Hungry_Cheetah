package com.foodapp.pricing.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SevereCondition {
    private String type; // HURRICANE, BLIZZARD, FLOOD, EXTREME_HEAT
    private String location;
    private Integer severity; // 1-10 scale
    private String description;
    private Boolean isPricingImpacted;
}