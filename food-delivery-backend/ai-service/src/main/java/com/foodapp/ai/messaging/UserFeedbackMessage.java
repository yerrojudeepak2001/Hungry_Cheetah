package com.foodapp.ai.messaging;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UserFeedbackMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long userId;
    private String recommendationId;
    private Boolean liked;
    private String feedbackNotes;
    private String feedbackType;
    private LocalDateTime timestamp;

    // Constructors
    public UserFeedbackMessage() {
        this.timestamp = LocalDateTime.now();
    }

    public UserFeedbackMessage(Long userId, String recommendationId, Boolean liked) {
        this();
        this.userId = userId;
        this.recommendationId = recommendationId;
        this.liked = liked;
        this.feedbackType = "recommendation_feedback";
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRecommendationId() {
        return recommendationId;
    }

    public void setRecommendationId(String recommendationId) {
        this.recommendationId = recommendationId;
    }

    public Boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    public String getFeedbackNotes() {
        return feedbackNotes;
    }

    public void setFeedbackNotes(String feedbackNotes) {
        this.feedbackNotes = feedbackNotes;
    }

    public String getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(String feedbackType) {
        this.feedbackType = feedbackType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "UserFeedbackMessage{" +
                "userId=" + userId +
                ", recommendationId='" + recommendationId + '\'' +
                ", liked=" + liked +
                ", feedbackNotes='" + feedbackNotes + '\'' +
                ", feedbackType='" + feedbackType + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}