package com.foodapp.ai.recommendation.service;

import com.foodapp.ai.recommendation.model.TasteProfile;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PersonalizedRecommendationService {
    private final TasteProfileRepository tasteProfileRepository;
    private final WeatherIntegrationService weatherService;
    private final EmotionAnalysisService emotionService;
    private final RestaurantClient restaurantClient;

    public PersonalizedRecommendationService(
            TasteProfileRepository tasteProfileRepository,
            WeatherIntegrationService weatherService,
            EmotionAnalysisService emotionService,
            RestaurantClient restaurantClient) {
        this.tasteProfileRepository = tasteProfileRepository;
        this.weatherService = weatherService;
        this.emotionService = emotionService;
        this.restaurantClient = restaurantClient;
    }

    public List<RecommendationDTO> getPersonalizedRecommendations(Long userId, String latitude, String longitude) {
        // Get user's taste profile
        TasteProfile profile = tasteProfileRepository.findByUserId(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Taste profile not found"));

        // Get weather-based recommendations
        Map<String, Object> weatherRecommendations = weatherService.getWeatherBasedRecommendations(latitude, longitude);

        // Calculate recommendation scores
        List<MenuItem> allMenuItems = restaurantClient.getAllMenuItems();
        return allMenuItems.stream()
            .map(item -> calculateRecommendationScore(item, profile, weatherRecommendations))
            .sorted(Comparator.comparing(RecommendationDTO::getScore).reversed())
            .limit(10)
            .collect(Collectors.toList());
    }

    private RecommendationDTO calculateRecommendationScore(MenuItem item, TasteProfile profile, Map<String, Object> weatherContext) {
        double score = 0.0;

        // Base score from taste preferences
        score += calculateTasteMatchScore(item, profile) * 0.4;

        // Weather context score
        score += calculateWeatherContextScore(item, weatherContext) * 0.2;

        // Time-based score
        score += calculateTimeBasedScore(item) * 0.1;

        // Popular items boost
        score += calculatePopularityScore(item) * 0.2;

        // Special dietary considerations
        score += calculateDietaryScore(item, profile) * 0.1;

        return new RecommendationDTO(item, score);
    }

    private double calculateTasteMatchScore(MenuItem item, TasteProfile profile) {
        double score = 0.0;
        
        // Match cuisine preferences
        if (profile.getPreferredCuisines().contains(item.getCuisineType())) {
            score += 0.5;
        }

        // Match spice preference
        if (Math.abs(profile.getSpicePreference() - item.getSpiceLevel()) <= 1) {
            score += 0.3;
        }

        // Match favorite ingredients
        score += profile.getFavoriteIngredients().stream()
            .filter(ingredient -> item.getIngredients().contains(ingredient))
            .count() * 0.2;

        return score;
    }

    private double calculateWeatherContextScore(MenuItem item, Map<String, Object> weatherContext) {
        double score = 0.0;
        String weatherType = (String) weatherContext.get("type");
        
        // Hot weather recommendations
        if ("hot".equals(weatherType) && 
            (item.getCategory().equals("COLD_DRINKS") || item.getCategory().equals("SALADS"))) {
            score += 1.0;
        }
        
        // Cold weather recommendations
        if ("cold".equals(weatherType) && 
            (item.getCategory().equals("SOUPS") || item.getCategory().equals("HOT_BEVERAGES"))) {
            score += 1.0;
        }

        return score;
    }

    private double calculateTimeBasedScore(MenuItem item) {
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        
        // Breakfast items (6-11)
        if (currentHour >= 6 && currentHour <= 11 && 
            item.getCategory().equals("BREAKFAST")) {
            return 1.0;
        }
        
        // Lunch items (11-15)
        if (currentHour >= 11 && currentHour <= 15 && 
            item.getCategory().equals("LUNCH")) {
            return 1.0;
        }
        
        // Dinner items (18-23)
        if (currentHour >= 18 && currentHour <= 23 && 
            item.getCategory().equals("DINNER")) {
            return 1.0;
        }

        return 0.5;
    }

    private double calculatePopularityScore(MenuItem item) {
        // TODO: Implement popularity scoring based on order history
        return 0.0;
    }

    private double calculateDietaryScore(MenuItem item, TasteProfile profile) {
        double score = 1.0;

        // Check dietary restrictions
        if (!Collections.disjoint(profile.getDietaryRestrictions(), item.getAllergens())) {
            score = 0.0;
        }

        // Check allergens
        if (!Collections.disjoint(profile.getAllergies(), item.getAllergens())) {
            score = 0.0;
        }

        return score;
    }
}