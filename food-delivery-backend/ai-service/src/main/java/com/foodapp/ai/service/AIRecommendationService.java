package com.foodapp.ai.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodapp.ai.entity.AIAnalytics;
import com.foodapp.ai.entity.FoodRecommendation;
import com.foodapp.ai.entity.UserPreference;
import com.foodapp.ai.gemini.client.GeminiClient;
import com.foodapp.ai.repository.AIAnalyticsRepository;
import com.foodapp.ai.repository.FoodRecommendationRepository;
import com.foodapp.ai.repository.UserPreferenceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AIRecommendationService {
    private static final Logger logger = LoggerFactory.getLogger(AIRecommendationService.class);

    private final GeminiClient geminiClient;
    private final UserPreferenceRepository userPreferenceRepository;
    private final FoodRecommendationRepository foodRecommendationRepository;
    private final AIAnalyticsRepository analyticsRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public AIRecommendationService(
            GeminiClient geminiClient,
            UserPreferenceRepository userPreferenceRepository,
            FoodRecommendationRepository foodRecommendationRepository,
            AIAnalyticsRepository analyticsRepository) {
        this.geminiClient = geminiClient;
        this.userPreferenceRepository = userPreferenceRepository;
        this.foodRecommendationRepository = foodRecommendationRepository;
        this.analyticsRepository = analyticsRepository;
        this.objectMapper = new ObjectMapper();
    }

    public Mono<FoodRecommendation> getPersonalizedRecommendations(Long userId, String mood, String occasion, String sessionId) {
        long startTime = System.currentTimeMillis();
        
        return Mono.fromCallable(() -> userPreferenceRepository.findByUserId(userId).orElse(new UserPreference(userId)))
                .flatMap(userPreference -> {
                    String preferencesText = buildUserPreferencesText(userPreference);
                    String dietaryRestrictions = buildDietaryRestrictionsText(userPreference);
                    
                    return geminiClient.getFoodRecommendations(preferencesText, dietaryRestrictions, mood + " " + occasion)
                            .map(response -> parseAndSaveRecommendation(userId, sessionId, mood, occasion, response, startTime))
                            .doOnError(error -> logAnalyticsEvent(userId, "recommendation_error", sessionId, 
                                    Map.of("error", error.getMessage()), startTime, false));
                });
    }

    public Mono<String> getNutritionAnalysis(String foodItem, Long userId, String sessionId) {
        long startTime = System.currentTimeMillis();
        
        return geminiClient.getNutritionInfo(foodItem)
                .doOnSuccess(response -> logAnalyticsEvent(userId, "nutrition_analysis", sessionId,
                        Map.of("foodItem", foodItem), startTime, true))
                .doOnError(error -> logAnalyticsEvent(userId, "nutrition_error", sessionId,
                        Map.of("foodItem", foodItem, "error", error.getMessage()), startTime, false));
    }

    public Mono<String> generateRecipe(String dishName, Integer servings, String difficulty, Long userId, String sessionId) {
        long startTime = System.currentTimeMillis();
        
        return geminiClient.generateRecipe(dishName, servings.toString(), difficulty)
                .doOnSuccess(response -> logAnalyticsEvent(userId, "recipe_generation", sessionId,
                        Map.of("dishName", dishName, "servings", servings, "difficulty", difficulty), 
                        startTime, true))
                .doOnError(error -> logAnalyticsEvent(userId, "recipe_error", sessionId,
                        Map.of("dishName", dishName, "error", error.getMessage()), startTime, false));
    }

    public Mono<String> analyzeFoodMood(String userInput, Long userId, String sessionId) {
        long startTime = System.currentTimeMillis();
        
        return geminiClient.analyzeMood(userInput)
                .doOnSuccess(response -> logAnalyticsEvent(userId, "mood_analysis", sessionId,
                        Map.of("userInput", userInput.substring(0, Math.min(userInput.length(), 100))), 
                        startTime, true))
                .doOnError(error -> logAnalyticsEvent(userId, "mood_analysis_error", sessionId,
                        Map.of("error", error.getMessage()), startTime, false));
    }

    public List<FoodRecommendation> getUserRecommendationHistory(Long userId, int limit) {
        return foodRecommendationRepository.findByUserId(userId)
                .stream()
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    public void recordUserFeedback(String recommendationId, Boolean liked, String notes) {
        foodRecommendationRepository.findById(recommendationId)
                .ifPresent(recommendation -> {
                    recommendation.setUserFeedback(liked);
                    recommendation.setFeedbackNotes(notes);
                    foodRecommendationRepository.save(recommendation);
                    
                    // Log feedback analytics
                    logAnalyticsEvent(recommendation.getUserId(), "user_feedback", 
                            recommendation.getSessionId(),
                            Map.of("recommendationId", recommendationId, "liked", liked, 
                                   "hasNotes", notes != null && !notes.isEmpty()),
                            0L, true);
                });
    }

    public UserPreference saveUserPreferences(Long userId, UserPreference preferences) {
        preferences.setUserId(userId);
        preferences.setUpdatedAt(LocalDateTime.now());
        
        UserPreference saved = userPreferenceRepository.save(preferences);
        
        logAnalyticsEvent(userId, "preferences_updated", null,
                Map.of("cuisineCount", preferences.getFavoriteCuisines() != null ? preferences.getFavoriteCuisines().size() : 0,
                       "hasRestrictions", preferences.getDietaryRestrictions() != null && !preferences.getDietaryRestrictions().isEmpty()),
                0L, true);
        
        return saved;
    }

    public Optional<UserPreference> getUserPreferences(Long userId) {
        return userPreferenceRepository.findByUserId(userId);
    }

    public List<Map<String, Object>> getRecommendationStats(Long userId, int days) {
        LocalDateTime since = LocalDateTime.now().minusDays(days);
        List<FoodRecommendation> recommendations = foodRecommendationRepository
                .findByUserIdAndCreatedAtAfter(userId, since);
        
        // Group by date and count
        Map<String, Long> dailyCount = recommendations.stream()
                .collect(Collectors.groupingBy(
                        r -> r.getCreatedAt().toLocalDate().toString(),
                        Collectors.counting()
                ));
        
        return dailyCount.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("date", entry.getKey());
                    map.put("count", entry.getValue());
                    return map;
                })
                .collect(Collectors.toList());
    }

    private String buildUserPreferencesText(UserPreference preferences) {
        StringBuilder sb = new StringBuilder();
        
        if (preferences.getFavoriteCuisines() != null && !preferences.getFavoriteCuisines().isEmpty()) {
            sb.append("Favorite cuisines: ").append(String.join(", ", preferences.getFavoriteCuisines())).append(". ");
        }
        
        if (preferences.getSpiceLevel() != null) {
            sb.append("Spice preference: ").append(preferences.getSpiceLevel()).append(". ");
        }
        
        if (preferences.getDislikedFoods() != null && !preferences.getDislikedFoods().isEmpty()) {
            sb.append("Dislikes: ").append(String.join(", ", preferences.getDislikedFoods())).append(". ");
        }
        
        if (preferences.getHealthConscious() != null && preferences.getHealthConscious()) {
            sb.append("Health-conscious eater. ");
        }
        
        if (preferences.getPrimaryGoal() != null) {
            sb.append("Primary goal: ").append(preferences.getPrimaryGoal()).append(". ");
        }
        
        return sb.toString().trim();
    }

    private String buildDietaryRestrictionsText(UserPreference preferences) {
        List<String> restrictions = new ArrayList<>();
        
        if (preferences.getDietaryRestrictions() != null) {
            restrictions.addAll(preferences.getDietaryRestrictions());
        }
        
        if (preferences.getAllergies() != null) {
            restrictions.addAll(preferences.getAllergies().stream()
                    .map(allergy -> "allergic to " + allergy)
                    .collect(Collectors.toList()));
        }
        
        return restrictions.isEmpty() ? "No dietary restrictions" : String.join(", ", restrictions);
    }

    private FoodRecommendation parseAndSaveRecommendation(Long userId, String sessionId, String mood, String occasion, 
                                                         String response, long startTime) {
        try {
            FoodRecommendation recommendation = new FoodRecommendation(userId, sessionId);
            recommendation.setMood(mood);
            recommendation.setOccasion(occasion);
            recommendation.setRequestPrompt(mood + " " + occasion);
            
            // Try to parse JSON response
            try {
                JsonNode jsonNode = objectMapper.readTree(response);
                if (jsonNode.has("recommendations")) {
                    List<FoodRecommendation.RecommendedItem> items = new ArrayList<>();
                    JsonNode recommendationsNode = jsonNode.get("recommendations");
                    
                    for (JsonNode itemNode : recommendationsNode) {
                        FoodRecommendation.RecommendedItem item = new FoodRecommendation.RecommendedItem();
                        item.setName(itemNode.path("name").asText());
                        item.setDescription(itemNode.path("description").asText());
                        item.setCategory(itemNode.path("category").asText());
                        item.setEstimatedPrice(itemNode.path("estimatedPrice").asText());
                        item.setPrepTime(itemNode.path("prepTime").asText());
                        
                        if (itemNode.has("healthScore")) {
                            item.setHealthScore(itemNode.get("healthScore").asInt());
                        }
                        
                        if (itemNode.has("tags")) {
                            List<String> tags = new ArrayList<>();
                            for (JsonNode tagNode : itemNode.get("tags")) {
                                tags.add(tagNode.asText());
                            }
                            item.setTags(tags);
                        }
                        
                        items.add(item);
                    }
                    
                    recommendation.setRecommendedItems(items);
                }
                
                if (jsonNode.has("explanation")) {
                    recommendation.setExplanation(jsonNode.get("explanation").asText());
                }
                
                recommendation.setConfidenceScore(0.85); // Default confidence
            } catch (Exception e) {
                logger.warn("Could not parse JSON response, saving as plain text", e);
                recommendation.setExplanation(response);
                recommendation.setConfidenceScore(0.5);
            }
            
            FoodRecommendation saved = foodRecommendationRepository.save(recommendation);
            
            logAnalyticsEvent(userId, "recommendation_success", sessionId,
                    Map.of("itemCount", saved.getRecommendedItems() != null ? saved.getRecommendedItems().size() : 0),
                    startTime, true);
            
            return saved;
            
        } catch (Exception e) {
            logger.error("Error parsing recommendation response", e);
            logAnalyticsEvent(userId, "recommendation_parse_error", sessionId,
                    Map.of("error", e.getMessage()), startTime, false);
            
            // Return basic recommendation with error info
            FoodRecommendation errorRecommendation = new FoodRecommendation(userId, sessionId);
            errorRecommendation.setMood(mood);
            errorRecommendation.setOccasion(occasion);
            errorRecommendation.setExplanation("Unable to process recommendation at this time. Please try again.");
            return errorRecommendation;
        }
    }

    private void logAnalyticsEvent(Long userId, String eventType, String sessionId, 
                                  Map<String, Object> eventData, long startTime, boolean successful) {
        try {
            AIAnalytics analytics = new AIAnalytics(userId, eventType);
            analytics.setSessionId(sessionId);
            analytics.setEventData(eventData);
            analytics.setSuccessful(successful);
            
            if (startTime > 0) {
                analytics.setResponseTimeMs(System.currentTimeMillis() - startTime);
            }
            
            analyticsRepository.save(analytics);
        } catch (Exception e) {
            logger.error("Error logging analytics event", e);
        }
    }
}