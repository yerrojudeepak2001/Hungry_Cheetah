package com.foodapp.ai.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

public class FeedbackRequest {
    @NotBlank(message = "Recommendation ID is required")
    private String recommendationId;
    
    @NotNull(message = "Feedback (liked) is required")
    private Boolean liked;
    
    private String notes;

    // Constructors
    public FeedbackRequest() {}

    public FeedbackRequest(String recommendationId, Boolean liked) {
        this.recommendationId = recommendationId;
        this.liked = liked;
    }

    public FeedbackRequest(String recommendationId, Boolean liked, String notes) {
        this.recommendationId = recommendationId;
        this.liked = liked;
        this.notes = notes;
    }

    // Getters and Setters
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}