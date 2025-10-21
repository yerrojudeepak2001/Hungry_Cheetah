package com.foodapp.ai.vision;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "augmented_dining")
public class AugmentedDining {
    @Id
    private String id;
    private Long restaurantId;
    
    // AR Menu Features
    private List<ARMenuItem> arMenu;
    private List<String> supportedDevices;
    private String arPlatform;
    private Map<String, String> arAssets;
    
    // Food Recognition
    private FoodRecognition foodRecognition;
    private List<VisualSearch> visualSearches;
    private List<NutritionScan> nutritionScans;
    
    @Data
    public static class ARMenuItem {
        private String itemId;
        private String name;
        private String modelUrl; // 3D model URL
        private String textureUrl;
        private Map<String, String> animations;
        private List<String> ingredients;
        private Map<String, Double> dimensions;
        private List<String> viewAngles;
        private Map<String, Object> customizations;
        private List<String> garnishes;
        private Boolean hasAnimation;
    }
    
    @Data
    public static class FoodRecognition {
        private String recognitionId;
        private String imageUrl;
        private LocalDateTime scanTime;
        private List<RecognizedItem> recognizedItems;
        private Map<String, Double> confidenceScores;
        private List<String> suggestedItems;
        private Map<String, Object> nutritionalEstimates;
    }
    
    @Data
    public static class RecognizedItem {
        private String itemName;
        private Double confidence;
        private Map<String, Double> ingredients;
        private Map<String, Double> nutritionalValues;
        private List<String> allergens;
        private List<String> similarDishes;
        private Map<String, String> preparation;
    }
    
    @Data
    public static class VisualSearch {
        private String searchId;
        private String imageUrl;
        private LocalDateTime searchTime;
        private List<SearchResult> results;
        private Map<String, Double> relevanceScores;
    }
    
    @Data
    public static class SearchResult {
        private String itemId;
        private String name;
        private Double matchScore;
        private List<String> matchingAttributes;
        private Map<String, Object> visualFeatures;
    }
    
    @Data
    public static class NutritionScan {
        private String scanId;
        private String imageUrl;
        private LocalDateTime scanTime;
        private Map<String, Double> nutritionValues;
        private List<String> ingredients;
        private List<String> allergens;
        private Map<String, Object> healthMetrics;
        private List<String> dietaryFlags;
        private Map<String, Double> portionEstimates;
    }
    
    // Interactive Features
    private List<ARInteraction> interactions;
    private Map<String, Object> userPreferences;
    private List<String> savedViews;
    
    @Data
    public static class ARInteraction {
        private String interactionId;
        private Long userId;
        private String type;
        private LocalDateTime timestamp;
        private Map<String, Object> interactionData;
        private String deviceInfo;
        private Map<String, Object> performanceMetrics;
    }
    
    // Personalization
    private Map<String, Object> userCalibration;
    private List<String> favoriteViews;
    private Map<String, Object> displayPreferences;
    
    // Analytics
    private Map<String, Integer> viewCounts;
    private List<String> popularAngles;
    private Map<String, Object> interactionMetrics;
    private List<String> technicalIssues;
}