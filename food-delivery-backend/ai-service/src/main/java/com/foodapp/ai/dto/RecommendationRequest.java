package com.foodapp.ai.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

public class RecommendationRequest {
    @NotNull(message = "User ID is required")
    private Long userId;
    
    @NotBlank(message = "Mood is required")
    private String mood;
    
    @NotBlank(message = "Occasion is required")
    private String occasion;
    
    // Optional context parameters
    private String location;
    private String timeOfDay;
    private String weatherCondition;

    // Constructors
    public RecommendationRequest() {}

    public RecommendationRequest(Long userId, String mood, String occasion) {
        this.userId = userId;
        this.mood = mood;
        this.occasion = occasion;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getOccasion() {
        return occasion;
    }

    public void setOccasion(String occasion) {
        this.occasion = occasion;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(String timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }
}