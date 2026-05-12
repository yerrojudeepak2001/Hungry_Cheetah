package com.foodapp.search.dto;

import java.util.List;
import java.util.Map;

public class SearchPreferences {
    private List<String> preferredCuisines;
    private List<String> dislikedCuisines;
    private Integer maxPrice;
    private Integer minRating;
    private Boolean isVegetarian;
    private Boolean isVegan;
    private Boolean isGlutenFree;
    private String defaultLocation;
    private Double defaultRadiusKm;
    private String preferredSortBy;
    private Map<String, Object> customPreferences;

    // Default constructor
    public SearchPreferences() {}

    // Getters and setters
    public List<String> getPreferredCuisines() { return preferredCuisines; }
    public void setPreferredCuisines(List<String> preferredCuisines) { this.preferredCuisines = preferredCuisines; }

    public List<String> getDislikedCuisines() { return dislikedCuisines; }
    public void setDislikedCuisines(List<String> dislikedCuisines) { this.dislikedCuisines = dislikedCuisines; }

    public Integer getMaxPrice() { return maxPrice; }
    public void setMaxPrice(Integer maxPrice) { this.maxPrice = maxPrice; }

    public Integer getMinRating() { return minRating; }
    public void setMinRating(Integer minRating) { this.minRating = minRating; }

    public Boolean getIsVegetarian() { return isVegetarian; }
    public void setIsVegetarian(Boolean isVegetarian) { this.isVegetarian = isVegetarian; }

    public Boolean getIsVegan() { return isVegan; }
    public void setIsVegan(Boolean isVegan) { this.isVegan = isVegan; }

    public Boolean getIsGlutenFree() { return isGlutenFree; }
    public void setIsGlutenFree(Boolean isGlutenFree) { this.isGlutenFree = isGlutenFree; }

    public String getDefaultLocation() { return defaultLocation; }
    public void setDefaultLocation(String defaultLocation) { this.defaultLocation = defaultLocation; }

    public Double getDefaultRadiusKm() { return defaultRadiusKm; }
    public void setDefaultRadiusKm(Double defaultRadiusKm) { this.defaultRadiusKm = defaultRadiusKm; }

    public String getPreferredSortBy() { return preferredSortBy; }
    public void setPreferredSortBy(String preferredSortBy) { this.preferredSortBy = preferredSortBy; }

    public Map<String, Object> getCustomPreferences() { return customPreferences; }
    public void setCustomPreferences(Map<String, Object> customPreferences) { this.customPreferences = customPreferences; }
}