package com.foodapp.ai.messaging;

import java.io.Serializable;
import java.time.LocalDateTime;

public class NutritionAnalysisMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long userId;
    private String sessionId;
    private String foodItem;
    private String analysisType;
    private LocalDateTime timestamp;

    // Constructors
    public NutritionAnalysisMessage() {
        this.timestamp = LocalDateTime.now();
    }

    public NutritionAnalysisMessage(Long userId, String sessionId, String foodItem) {
        this();
        this.userId = userId;
        this.sessionId = sessionId;
        this.foodItem = foodItem;
        this.analysisType = "nutrition_info";
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(String foodItem) {
        this.foodItem = foodItem;
    }

    public String getAnalysisType() {
        return analysisType;
    }

    public void setAnalysisType(String analysisType) {
        this.analysisType = analysisType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "NutritionAnalysisMessage{" +
                "userId=" + userId +
                ", sessionId='" + sessionId + '\'' +
                ", foodItem='" + foodItem + '\'' +
                ", analysisType='" + analysisType + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}