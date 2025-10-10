package com.foodapp.user.dto;

import lombok.Data;

@Data
public class UserOrderStats {
    private String userId;
    private Long totalOrders;
    private Double totalSpent;
    private String favoriteRestaurant;
    private String favoriteCategory;
}