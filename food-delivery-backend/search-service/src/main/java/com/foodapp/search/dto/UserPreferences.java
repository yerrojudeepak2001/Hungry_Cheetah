package com.foodapp.search.dto;

import java.util.Set;

public class UserPreferences {
    private Set<String> preferredCuisines;
    private Set<String> dietaryRestrictions;
    private Integer maxBudget;
    private String preferredLocation;
    private Double maxDistance;

    // Default constructor
    public UserPreferences() {}

    // Getters and setters
    public Set<String> getPreferredCuisines() { return preferredCuisines; }
    public void setPreferredCuisines(Set<String> preferredCuisines) { this.preferredCuisines = preferredCuisines; }

    public Set<String> getDietaryRestrictions() { return dietaryRestrictions; }
    public void setDietaryRestrictions(Set<String> dietaryRestrictions) { this.dietaryRestrictions = dietaryRestrictions; }

    public Integer getMaxBudget() { return maxBudget; }
    public void setMaxBudget(Integer maxBudget) { this.maxBudget = maxBudget; }

    public String getPreferredLocation() { return preferredLocation; }
    public void setPreferredLocation(String preferredLocation) { this.preferredLocation = preferredLocation; }

    public Double getMaxDistance() { return maxDistance; }
    public void setMaxDistance(Double maxDistance) { this.maxDistance = maxDistance; }
}