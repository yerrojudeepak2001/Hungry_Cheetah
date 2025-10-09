package com.foodapp.ai.service;

import com.foodapp.ai.model.FoodRecommendation;
import com.foodapp.ai.repository.FoodRecommendationRepository;
import org.springframework.stereotype.Service;
import org.springframework.cloud.openfeign.FeignClient;
import com.google.cloud.aiplatform.v1.PredictionServiceClient;
import com.google.cloud.aiplatform.v1.EndpointName;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class AIRecommendationService {
    private final FoodRecommendationRepository recommendationRepository;
    private final UserPreferenceService preferenceService;
    private final WeatherService weatherService;
    private final PredictionServiceClient predictionServiceClient;
    private final String projectId;
    private final String endpointId;

    public AIRecommendationService(FoodRecommendationRepository recommendationRepository,
                                 UserPreferenceService preferenceService,
                                 WeatherService weatherService,
                                 PredictionServiceClient predictionServiceClient,
                                 @Value("${google.cloud.project-id}") String projectId,
                                 @Value("${google.cloud.endpoint-id}") String endpointId) {
        this.recommendationRepository = recommendationRepository;
        this.preferenceService = preferenceService;
        this.weatherService = weatherService;
        this.predictionServiceClient = predictionServiceClient;
        this.projectId = projectId;
        this.endpointId = endpointId;
    }

    public List<FoodRecommendation> generateRecommendations(Long userId) {
        // Get user preferences and context
        Map<String, Object> userContext = getUserContext(userId);
        
        // Prepare features for ML model
        List<Feature> features = prepareFeatures(userContext);
        
        // Call ML model
        List<Prediction> predictions = callModel(features);
        
        // Process and save recommendations
        List<FoodRecommendation> recommendations = processRecommendations(predictions, userId);
        
        return recommendationRepository.saveAll(recommendations);
    }

    public List<FoodRecommendation> generateWeatherBasedRecommendations(Long userId, String location) {
        // Get weather data
        WeatherData weather = weatherService.getCurrentWeather(location);
        
        // Adjust recommendations based on weather
        Map<String, Double> weatherFactors = calculateWeatherFactors(weather);
        
        // Generate base recommendations
        List<FoodRecommendation> baseRecommendations = generateRecommendations(userId);
        
        // Apply weather factors
        List<FoodRecommendation> adjustedRecommendations = 
            adjustRecommendations(baseRecommendations, weatherFactors);
            
        return recommendationRepository.saveAll(adjustedRecommendations);
    }

    public List<FoodRecommendation> generateMoodBasedRecommendations(Long userId, Double mood) {
        // Get user's current preferences
        Map<String, Object> userContext = getUserContext(userId);
        userContext.put("mood", mood);
        
        // Adjust recommendations based on mood
        List<Feature> features = prepareFeatures(userContext);
        features.add(Feature.newBuilder().setName("mood").setValue(mood).build());
        
        // Get predictions
        List<Prediction> predictions = callModel(features);
        
        // Process recommendations with mood context
        List<FoodRecommendation> recommendations = 
            processRecommendations(predictions, userId);
            
        return recommendationRepository.saveAll(recommendations);
    }

    private Map<String, Object> getUserContext(Long userId) {
        Map<String, Object> context = new HashMap<>();
        
        // Get user preferences
        UserPreference preferences = preferenceService.getUserPreferences(userId);
        context.put("cuisinePreferences", preferences.getCuisinePreferences());
        context.put("dietaryRestrictions", preferences.getDietaryRestrictions());
        context.put("pricePreference", preferences.getPricePreference());
        
        // Add temporal context
        LocalDateTime now = LocalDateTime.now();
        context.put("timeOfDay", getTimeOfDay(now));
        context.put("dayOfWeek", now.getDayOfWeek().toString());
        context.put("season", getSeason(now));
        
        return context;
    }

    private List<Feature> prepareFeatures(Map<String, Object> context) {
        List<Feature> features = new ArrayList<>();
        
        context.forEach((key, value) -> {
            if (value instanceof Number) {
                features.add(Feature.newBuilder()
                    .setName(key)
                    .setValue(((Number) value).doubleValue())
                    .build());
            } else if (value instanceof String) {
                features.add(Feature.newBuilder()
                    .setName(key)
                    .setValue((String) value)
                    .build());
            }
        });
        
        return features;
    }

    private List<Prediction> callModel(List<Feature> features) {
        try {
            EndpointName endpointName = EndpointName.of(projectId, "us-central1", endpointId);
            
            // Prepare the prediction request
            PredictRequest request = PredictRequest.newBuilder()
                .setEndpoint(endpointName.toString())
                .setInstances(Instance.newBuilder()
                    .putAllFeatures(features.stream()
                        .collect(Collectors.toMap(Feature::getName, Feature::getValue)))
                    .build())
                .build();
            
            // Get prediction
            PredictResponse response = predictionServiceClient.predict(request);
            return processPredictionResponse(response);
            
        } catch (Exception e) {
            throw new AIServiceException("Error calling prediction model: " + e.getMessage());
        }
    }

    private Map<String, Double> calculateWeatherFactors(WeatherData weather) {
        Map<String, Double> factors = new HashMap<>();
        
        // Temperature based factors
        if (weather.getTemperature() > 30) {
            factors.put("cold_dishes", 1.5);
            factors.put("hot_dishes", 0.5);
        } else if (weather.getTemperature() < 10) {
            factors.put("hot_dishes", 1.5);
            factors.put("soups", 1.3);
        }
        
        // Weather condition based factors
        switch (weather.getCondition()) {
            case "RAINY":
                factors.put("comfort_food", 1.4);
                factors.put("delivery_friendly", 1.3);
                break;
            case "SUNNY":
                factors.put("refreshing", 1.3);
                factors.put("outdoor_friendly", 1.2);
                break;
        }
        
        return factors;
    }

    private List<FoodRecommendation> adjustRecommendations(
            List<FoodRecommendation> recommendations,
            Map<String, Double> factors) {
        return recommendations.stream()
            .map(rec -> applyFactors(rec, factors))
            .sorted(Comparator.comparingDouble(FoodRecommendation::getScore).reversed())
            .collect(Collectors.toList());
    }

    private FoodRecommendation applyFactors(
            FoodRecommendation recommendation,
            Map<String, Double> factors) {
        double score = recommendation.getScore();
        
        // Apply relevant factors
        for (Map.Entry<String, Double> factor : factors.entrySet()) {
            if (recommendation.getTags().contains(factor.getKey())) {
                score *= factor.getValue();
            }
        }
        
        recommendation.setScore(score);
        return recommendation;
    }
}