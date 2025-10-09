package com.foodapp.admin.dto;

import lombok.Data;
import java.util.Map;
import java.util.List;

@Data
public class DashboardData {
    private OrderMetrics orderMetrics;
    private UserMetrics userMetrics;
    private RestaurantMetrics restaurantMetrics;
    private DeliveryMetrics deliveryMetrics;
    private RevenueMetrics revenueMetrics;
    private Map<String, TrendData> trends;
    
    @Data
    public static class TrendData {
        private List<Double> values;
        private List<String> labels;
        private String trendType;
        private Double changePercentage;
    }
    
    @Data
    public static class OrderMetrics {
        private int totalOrders;
        private int pendingOrders;
        private int completedOrders;
        private double averageOrderValue;
    }
    
    @Data
    public static class UserMetrics {
        private int activeUsers;
        private int newUsers;
        private double userRetentionRate;
    }
    
    @Data
    public static class RestaurantMetrics {
        private int activeRestaurants;
        private double averageRating;
        private int totalMenuItems;
    }
    
    @Data
    public static class DeliveryMetrics {
        private double averageDeliveryTime;
        private int activeDrivers;
        private double deliverySuccessRate;
    }
    
    @Data
    public static class RevenueMetrics {
        private double totalRevenue;
        private double platformFees;
        private double deliveryFees;
        private Map<String, Double> revenueByCategory;
    }
}