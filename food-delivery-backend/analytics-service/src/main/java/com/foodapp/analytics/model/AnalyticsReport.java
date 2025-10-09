package com.foodapp.analytics.model;

import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.Map;

@Data
@Document(collection = "analytics_reports")
public class AnalyticsReport {
    private String id;
    private String reportType; // DAILY, WEEKLY, MONTHLY
    private LocalDateTime periodStart;
    private LocalDateTime periodEnd;
    
    // Order metrics
    private Integer totalOrders;
    private BigDecimal totalRevenue;
    private Double averageOrderValue;
    private Integer uniqueCustomers;
    
    // Restaurant metrics
    private Map<String, Integer> ordersByRestaurant;
    private Map<String, BigDecimal> revenueByRestaurant;
    
    // Delivery metrics
    private Double averageDeliveryTime;
    private Integer totalDeliveries;
    private Map<String, Integer> deliveriesByArea;
    
    // Menu metrics
    private Map<String, Integer> topSellingItems;
    private Map<String, Integer> leastSellingItems;
    
    // Customer metrics
    private Integer newCustomers;
    private Integer repeatCustomers;
    private Map<String, Integer> ordersByCustomerSegment;
    
    // Payment metrics
    private Map<String, Integer> ordersByPaymentMethod;
    private BigDecimal totalRefunds;
    
    // AI metrics
    private Integer aiRecommendationConversions;
    private Integer voiceOrdersProcessed;
    private Map<String, Integer> weatherBasedOrderPatterns;
}