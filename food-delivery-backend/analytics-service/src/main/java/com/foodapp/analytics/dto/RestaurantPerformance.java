package com.foodapp.analytics.dto;

import java.time.LocalDateTime;

public class RestaurantPerformance {
    private String restaurantId;
    private String name;
    private Integer totalOrders;
    private Double totalRevenue;
    private Double averageOrderValue;
    private Double averageRating;
    private Integer averagePreparationTime;
    private Double cancellationRate;
    private Integer popularItems;
    private String topSellingItem;
    private LocalDateTime lastOrderTime;
    private String performanceGrade;

    public RestaurantPerformance() {
    }

    public RestaurantPerformance(String restaurantId, String name) {
        this.restaurantId = restaurantId;
        this.name = name;
    }

    // Getters and setters
    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(Integer totalOrders) {
        this.totalOrders = totalOrders;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Double getAverageOrderValue() {
        return averageOrderValue;
    }

    public void setAverageOrderValue(Double averageOrderValue) {
        this.averageOrderValue = averageOrderValue;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getAveragePreparationTime() {
        return averagePreparationTime;
    }

    public void setAveragePreparationTime(Integer averagePreparationTime) {
        this.averagePreparationTime = averagePreparationTime;
    }

    public Double getCancellationRate() {
        return cancellationRate;
    }

    public void setCancellationRate(Double cancellationRate) {
        this.cancellationRate = cancellationRate;
    }

    public Integer getPopularItems() {
        return popularItems;
    }

    public void setPopularItems(Integer popularItems) {
        this.popularItems = popularItems;
    }

    public String getTopSellingItem() {
        return topSellingItem;
    }

    public void setTopSellingItem(String topSellingItem) {
        this.topSellingItem = topSellingItem;
    }

    public LocalDateTime getLastOrderTime() {
        return lastOrderTime;
    }

    public void setLastOrderTime(LocalDateTime lastOrderTime) {
        this.lastOrderTime = lastOrderTime;
    }

    public String getPerformanceGrade() {
        return performanceGrade;
    }

    public void setPerformanceGrade(String performanceGrade) {
        this.performanceGrade = performanceGrade;
    }
}