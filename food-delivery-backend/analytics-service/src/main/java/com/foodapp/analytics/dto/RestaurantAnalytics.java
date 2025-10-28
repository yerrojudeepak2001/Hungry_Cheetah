package com.foodapp.analytics.dto;

import java.time.LocalDateTime;

public class RestaurantAnalytics {
    private String restaurantId;
    private String name;
    private Integer totalOrders;
    private Double totalRevenue;
    private Double averageRating;
    private Integer totalItems;
    private Double averageOrderValue;
    private Integer preparationTime;
    private String cuisine;
    private String location;
    private LocalDateTime lastOrderTime;
    private Boolean isActive;
    private Double popularityScore;

    public RestaurantAnalytics() {
    }

    public RestaurantAnalytics(String restaurantId, String name) {
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

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public Double getAverageOrderValue() {
        return averageOrderValue;
    }

    public void setAverageOrderValue(Double averageOrderValue) {
        this.averageOrderValue = averageOrderValue;
    }

    public Integer getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(Integer preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getLastOrderTime() {
        return lastOrderTime;
    }

    public void setLastOrderTime(LocalDateTime lastOrderTime) {
        this.lastOrderTime = lastOrderTime;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Double getPopularityScore() {
        return popularityScore;
    }

    public void setPopularityScore(Double popularityScore) {
        this.popularityScore = popularityScore;
    }
}