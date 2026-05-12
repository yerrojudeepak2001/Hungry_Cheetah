package com.foodapp.admin.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardKPIs {
    private double totalRevenue;
    private long totalOrders;
    private double averageOrderValue;
    private double customerSatisfaction;
    private long restaurantPartners;
    private long activeUsers;
    private double revenueGrowth;
    private double orderGrowth;
    private double userGrowth;
    private double restaurantGrowth;
}