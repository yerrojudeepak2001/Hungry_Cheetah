package com.foodapp.restaurant.dto.quality;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QualityMetrics {
    private Long id;
    private Long restaurantId;
    private Double foodSafetyScore;
    private Double hygieneMaintenance;
    private Double customerFeedbackScore;
    private Double overallRating;
}