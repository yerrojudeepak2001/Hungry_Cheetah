package com.foodapp.ai.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "food_recommendations")
public class FoodRecommendation {
    @Id
    private String id;
    
    @Indexed
    private Long userId;
    
    private String sessionId;
    private String requestPrompt;
    private String mood;
    private String occasion;
    private List<RecommendedItem> recommendedItems;
    private String explanation;
    private Double confidenceScore;
    private Boolean userFeedback; // true = liked, false = disliked, null = no feedback
    private String feedbackNotes;
    
    private LocalDateTime createdAt;
    private LocalDateTime feedbackAt;

    public static class RecommendedItem {
        private String name;
        private String description;
        private String category;
        private String estimatedPrice;
        private String prepTime;
        private Integer healthScore;
        private List<String> tags;
        private Long restaurantId;
        private Long menuItemId;

        // Constructors
        public RecommendedItem() {}

        public RecommendedItem(String name, String description, String category) {
            this.name = name;
            this.description = description;
            this.category = category;
        }

        // Getters and Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getEstimatedPrice() {
            return estimatedPrice;
        }

        public void setEstimatedPrice(String estimatedPrice) {
            this.estimatedPrice = estimatedPrice;
        }

        public String getPrepTime() {
            return prepTime;
        }

        public void setPrepTime(String prepTime) {
            this.prepTime = prepTime;
        }

        public Integer getHealthScore() {
            return healthScore;
        }

        public void setHealthScore(Integer healthScore) {
            this.healthScore = healthScore;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public Long getRestaurantId() {
            return restaurantId;
        }

        public void setRestaurantId(Long restaurantId) {
            this.restaurantId = restaurantId;
        }

        public Long getMenuItemId() {
            return menuItemId;
        }

        public void setMenuItemId(Long menuItemId) {
            this.menuItemId = menuItemId;
        }
    }

    // Constructors
    public FoodRecommendation() {
        this.createdAt = LocalDateTime.now();
    }

    public FoodRecommendation(Long userId, String sessionId) {
        this();
        this.userId = userId;
        this.sessionId = sessionId;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getRequestPrompt() {
        return requestPrompt;
    }

    public void setRequestPrompt(String requestPrompt) {
        this.requestPrompt = requestPrompt;
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

    public List<RecommendedItem> getRecommendedItems() {
        return recommendedItems;
    }

    public void setRecommendedItems(List<RecommendedItem> recommendedItems) {
        this.recommendedItems = recommendedItems;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Double getConfidenceScore() {
        return confidenceScore;
    }

    public void setConfidenceScore(Double confidenceScore) {
        this.confidenceScore = confidenceScore;
    }

    public Boolean getUserFeedback() {
        return userFeedback;
    }

    public void setUserFeedback(Boolean userFeedback) {
        this.userFeedback = userFeedback;
        if (userFeedback != null) {
            this.feedbackAt = LocalDateTime.now();
        }
    }

    public String getFeedbackNotes() {
        return feedbackNotes;
    }

    public void setFeedbackNotes(String feedbackNotes) {
        this.feedbackNotes = feedbackNotes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getFeedbackAt() {
        return feedbackAt;
    }

    public void setFeedbackAt(LocalDateTime feedbackAt) {
        this.feedbackAt = feedbackAt;
    }
}