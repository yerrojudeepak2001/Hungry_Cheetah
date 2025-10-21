package com.foodapp.ai.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class FoodRecommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long userId;
    private LocalDateTime timestamp;
    
    @ElementCollection
    private Map<String, Double> cuisinePreferences;
    
    @ElementCollection
    private Map<String, Double> ingredientPreferences;
    
    @ElementCollection
    private Map<Long, Double> restaurantScores;
    
    @ElementCollection
    private Map<String, Double> tastePreferences;
    
    private String timeOfDay;
    private String dayOfWeek;
    private String weather;
    private String season;
    private Double userMood;
    private Boolean isSpecialOccasion;
    
    @ElementCollection
    private Map<String, Double> dietaryRestrictions;
    
    private Double pricePreference;
    private Integer preparationTimePreference;
    private Double healthScore;
    private Double noveltyScore;
}