package com.foodapp.analytics.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.Map;
import java.util.List;

@Data
@Document(collection = "restaurant_analytics")
public class RestaurantAnalytics {
    @Id
    private String id;
    private Long restaurantId;
    private LocalDateTime period;
    private String periodType; // DAILY, WEEKLY, MONTHLY
    
    // Order Analytics
    private Integer totalOrders;
    private Integer completedOrders;
    private Integer cancelledOrders;
    private BigDecimal totalRevenue;
    private BigDecimal averageOrderValue;
    private Map<String, Integer> ordersByTimeSlot;
    
    // Menu Performance
    private List<ItemPerformance> topPerformingItems;
    private List<ItemPerformance> lowPerformingItems;
    private Map<String, Integer> ordersByCategory;
    private List<String> popularCombinations;
    
    // Customer Insights
    private Integer newCustomers;
    private Integer repeatCustomers;
    private Double customerRetentionRate;
    private Map<String, Integer> customerDemographics;
    
    // Operational Metrics
    private Integer averagePreparationTime;
    private Double acceptanceRate;
    private Double cancellationRate;
    private Map<String, Integer> peakHours;
    
    // Quality Metrics
    private Double averageRating;
    private Integer totalReviews;
    private Map<Integer, Integer> ratingDistribution;
    private List<String> commonFeedback;
    
    // Financial Metrics
    private BigDecimal grossSales;
    private BigDecimal netSales;
    private BigDecimal platformFees;
    private BigDecimal deliveryFees;
    private BigDecimal taxesPaid;
    
    // Promotion Impact
    private List<PromotionPerformance> promotionMetrics;
    private BigDecimal totalDiscountsGiven;
    private Double promotionConversionRate;
    
    @Data
    public static class ItemPerformance {
        private Long menuItemId;
        private String itemName;
        private Integer orderCount;
        private BigDecimal revenue;
        private Double rating;
        private Integer returnRate;
        private Map<String, String> customizationPreferences;
    }
    
    @Data
    public static class PromotionPerformance {
        private String promotionId;
        private String promotionName;
        private Integer usageCount;
        private BigDecimal discountAmount;
        private Double conversionRate;
        private BigDecimal revenueImpact;
        private List<String> popularItems;
    }
}