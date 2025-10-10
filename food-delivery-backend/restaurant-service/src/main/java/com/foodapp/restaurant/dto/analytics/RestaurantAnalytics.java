package com.foodapp.restaurant.dto.analytics;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantAnalytics {
    private Long restaurantId;
    private Integer totalOrders;
    private BigDecimal totalRevenue;
    private Double averageOrderValue;
    private Map<String, Integer> ordersByStatus;
    private Double customerRetentionRate;
    private Map<String, Double> menuItemPerformance;
    private Map<String, Integer> peakHours;
    private Double averagePreparationTime;
    private Map<String, BigDecimal> revenueByCategory;
}