package com.foodapp.user.dto;

public class UserOrderStats {
    private Integer totalOrders;
    private Double totalSpent;
    private Integer ordersThisMonth;
    private Double averageOrderValue;
    private String mostOrderedRestaurant;
    private String favoriteCategory;

    // Getters and Setters
    public Integer getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(Integer totalOrders) {
        this.totalOrders = totalOrders;
    }

    public Double getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(Double totalSpent) {
        this.totalSpent = totalSpent;
    }

    public Integer getOrdersThisMonth() {
        return ordersThisMonth;
    }

    public void setOrdersThisMonth(Integer ordersThisMonth) {
        this.ordersThisMonth = ordersThisMonth;
    }

    public Double getAverageOrderValue() {
        return averageOrderValue;
    }

    public void setAverageOrderValue(Double averageOrderValue) {
        this.averageOrderValue = averageOrderValue;
    }

    public String getMostOrderedRestaurant() {
        return mostOrderedRestaurant;
    }

    public void setMostOrderedRestaurant(String mostOrderedRestaurant) {
        this.mostOrderedRestaurant = mostOrderedRestaurant;
    }

    public String getFavoriteCategory() {
        return favoriteCategory;
    }

    public void setFavoriteCategory(String favoriteCategory) {
        this.favoriteCategory = favoriteCategory;
    }
import lombok.Data;

@Data
public class UserOrderStats {
    private String userId;
    private Long totalOrders;
    private Double totalSpent;
    private String favoriteRestaurant;
    private String favoriteCategory;
}