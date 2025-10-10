package com.foodapp.auth.dto;

import jakarta.validation.constraints.NotBlank;

public class StaffValidationRequest {
    @NotBlank
    private String userId;
    
    @NotBlank
    private String restaurantId;
    
    @NotBlank
    private String role;

    // Getters and setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}