package com.foodapp.admin.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardStats {
    private long totalUsers;
    private long totalRestaurants;
    private long totalOrders;
    private long activeDeliveries;
    private double totalRevenue;
    private long pendingPayments;
    private double averageOrderValue;
    private long todayOrders;
    private long monthlyOrders;
    private double customerSatisfactionRate;
}