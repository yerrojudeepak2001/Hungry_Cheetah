package com.foodapp.ai.nutrition;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "smart_nutrition_plans")
public class SmartNutritionPlan {
    @Id
    private String id;
    private Long userId;
    private String planType;
    
    // Health Profile
    private HealthProfile healthProfile;
    private List<String> healthGoals;
    private List<String> dietaryRestrictions;
    private Map<String, Integer> nutritionTargets;
    
    // AI-Generated Plans
    private List<MealPlan> weeklyPlans;
    private Map<String, Integer> calorieDistribution;
    private List<String> recommendedRestaurants;
    private List<String> recommendedDishes;
    
    // Smart Features
    private Boolean autoAdjust; // Adjusts based on activity and progress
    private List<String> mealReminders;
    private Map<String, Object> healthMetrics;
    
    @Data
    public static class HealthProfile {
        private Double height;
        private Double weight;
        private Integer age;
        private String gender;
        private String activityLevel;
        private List<String> healthConditions;
        private List<String> allergies;
        private Map<String, String> bloodWork; // Integration with health apps
    }
    
    @Data
    public static class MealPlan {
        private String dayOfWeek;
        private List<Meal> meals;
        private Integer totalCalories;
        private Map<String, Double> macronutrients;
        private List<String> supplements;
        private Double hydrationTarget;
    }
    
    @Data
    public static class Meal {
        private String mealType;
        private LocalDateTime scheduledTime;
        private List<FoodItem> recommendedItems;
        private Map<String, Double> nutritionalBreakdown;
        private String restaurantId;
        private Double portionSize;
        private List<String> alternatives;
    }
    
    @Data
    public static class FoodItem {
        private String itemId;
        private String name;
        private Integer calories;
        private Map<String, Double> nutrients;
        private String cuisineType;
        private Double satietyIndex;
        private List<String> healthBenefits;
    }
    
    // Progress Tracking
    private List<ProgressUpdate> progressHistory;
    private Map<String, Double> goalProgress;
    private List<String> achievements;
    
    @Data
    public static class ProgressUpdate {
        private LocalDateTime date;
        private Double weight;
        private Map<String, Double> measurements;
        private Integer energyLevel;
        private String mood;
        private List<String> symptoms;
        private Map<String, Double> vitals;
    }
}