package com.foodapp.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryImpact {
    private String severity;
    private int estimatedDelay;
    private List<String> recommendations;
    private boolean shouldPause;
    private String reason;
    private Map<String, Double> riskFactors;
}