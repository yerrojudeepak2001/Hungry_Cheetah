package com.foodapp.ai.messaging;

import java.io.Serializable;
import java.time.LocalDateTime;

public class RecipeGenerationMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long userId;
    private String sessionId;
    private String dishName;
    private Integer servings;
    private String difficulty;
    private LocalDateTime timestamp;

    // Constructors
    public RecipeGenerationMessage() {
        this.timestamp = LocalDateTime.now();
    }

    public RecipeGenerationMessage(Long userId, String sessionId, String dishName, Integer servings, String difficulty) {
        this();
        this.userId = userId;
        this.sessionId = sessionId;
        this.dishName = dishName;
        this.servings = servings;
        this.difficulty = difficulty;
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

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "RecipeGenerationMessage{" +
                "userId=" + userId +
                ", sessionId='" + sessionId + '\'' +
                ", dishName='" + dishName + '\'' +
                ", servings=" + servings +
                ", difficulty='" + difficulty + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}