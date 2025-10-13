package com.foodapp.user.dto;

import lombok.Data;

@Data
public class UserOrderStats {
    private String userId;
    private Long totalOrders;
    private Double totalSpent;
    private Long ordersThisMonth;
    private Double averageOrderValue;
    private String mostOrderedRestaurant;
    private String favoriteRestaurant;
    private String favoriteCategory;
}