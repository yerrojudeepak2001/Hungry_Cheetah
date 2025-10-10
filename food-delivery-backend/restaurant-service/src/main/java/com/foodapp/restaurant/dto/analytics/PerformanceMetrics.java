package com.foodapp.restaurant.dto.analytics;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceMetrics {
    private Long restaurantId;
    private String timeFrame;
    private Double orderAcceptanceRate;
    private Double orderCompletionRate;
    private Double averageDeliveryTime;
    private Integer canceledOrders;
    private Integer delayedOrders;
    private BigDecimal totalRefunds;
    private Double customerSatisfactionScore;
    private Double operationalEfficiency;
}