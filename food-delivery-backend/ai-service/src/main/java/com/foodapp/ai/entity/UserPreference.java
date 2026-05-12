package com.foodapp.ai.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "user_preferences")
public class UserPreference {
    @Id
    private String id;
    
    @Indexed(unique = true)
    private Long userId;
    
    private List<String> favoriteCuisines;
    private List<String> dietaryRestrictions;
    private List<String> allergies;
    private String spiceLevel; // mild, medium, hot, extra-hot
    private List<String> dislikedFoods;
    private List<String> preferredMealTimes;
    private Integer budgetRange; // 1-5 scale
    private Boolean healthConscious;
    private String primaryGoal; // weight_loss, muscle_gain, maintenance, etc.
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public UserPreference() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public UserPreference(Long userId) {
        this();
        this.userId = userId;
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

    public List<String> getFavoriteCuisines() {
        return favoriteCuisines;
    }

    public void setFavoriteCuisines(List<String> favoriteCuisines) {
        this.favoriteCuisines = favoriteCuisines;
    }

    public List<String> getDietaryRestrictions() {
        return dietaryRestrictions;
    }

    public void setDietaryRestrictions(List<String> dietaryRestrictions) {
        this.dietaryRestrictions = dietaryRestrictions;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    public String getSpiceLevel() {
        return spiceLevel;
    }

    public void setSpiceLevel(String spiceLevel) {
        this.spiceLevel = spiceLevel;
    }

    public List<String> getDislikedFoods() {
        return dislikedFoods;
    }

    public void setDislikedFoods(List<String> dislikedFoods) {
        this.dislikedFoods = dislikedFoods;
    }

    public List<String> getPreferredMealTimes() {
        return preferredMealTimes;
    }

    public void setPreferredMealTimes(List<String> preferredMealTimes) {
        this.preferredMealTimes = preferredMealTimes;
    }

    public Integer getBudgetRange() {
        return budgetRange;
    }

    public void setBudgetRange(Integer budgetRange) {
        this.budgetRange = budgetRange;
    }

    public Boolean getHealthConscious() {
        return healthConscious;
    }

    public void setHealthConscious(Boolean healthConscious) {
        this.healthConscious = healthConscious;
    }

    public String getPrimaryGoal() {
        return primaryGoal;
    }

    public void setPrimaryGoal(String primaryGoal) {
        this.primaryGoal = primaryGoal;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}