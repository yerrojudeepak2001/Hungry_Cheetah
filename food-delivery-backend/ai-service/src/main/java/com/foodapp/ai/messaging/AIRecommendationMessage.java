package com.foodapp.ai.messaging;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AIRecommendationMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long userId;
    private String sessionId;
    private String mood;
    private String occasion;
    private Integer recommendationCount;
    private Double confidenceScore;
    private LocalDateTime timestamp;

    // Constructors
    public AIRecommendationMessage() {
        this.timestamp = LocalDateTime.now();
    }

    public AIRecommendationMessage(Long userId, String sessionId, String mood, String occasion) {
        this();
        this.userId = userId;
        this.sessionId = sessionId;
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

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
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

    public Integer getRecommendationCount() {
        return recommendationCount;
    }

    public void setRecommendationCount(Integer recommendationCount) {
        this.recommendationCount = recommendationCount;
    }

    public Double getConfidenceScore() {
        return confidenceScore;
    }

    public void setConfidenceScore(Double confidenceScore) {
        this.confidenceScore = confidenceScore;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "AIRecommendationMessage{" +
                "userId=" + userId +
                ", sessionId='" + sessionId + '\'' +
                ", mood='" + mood + '\'' +
                ", occasion='" + occasion + '\'' +
                ", recommendationCount=" + recommendationCount +
                ", confidenceScore=" + confidenceScore +
                ", timestamp=" + timestamp +
                '}';
    }
}