package com.foodapp.ai.controller;

import com.foodapp.ai.nutrition.SmartNutritionPlan;
import com.foodapp.ai.service.PersonalizedRecommendationService;
import com.foodapp.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/ai")
public class AIController {
    private final PersonalizedRecommendationService recommendationService;
    private final WeatherIntegrationService weatherService;
    private final EmotionAnalysisService emotionService;
    private final VisionRecognitionService visionService;
    private final VoiceOrderService voiceService;
    private final SmartNutritionService nutritionService;

    public AIController(PersonalizedRecommendationService recommendationService,
                       WeatherIntegrationService weatherService,
                       EmotionAnalysisService emotionService,
                       VisionRecognitionService visionService,
                       VoiceOrderService voiceService,
                       SmartNutritionService nutritionService) {
        this.recommendationService = recommendationService;
        this.weatherService = weatherService;
        this.emotionService = emotionService;
        this.visionService = visionService;
        this.voiceService = voiceService;
        this.nutritionService = nutritionService;
    }

    @GetMapping("/recommendations")
    public ResponseEntity<ApiResponse<?>> getPersonalizedRecommendations(
            @RequestParam Long userId,
            @RequestParam String latitude,
            @RequestParam String longitude) {
        var recommendations = recommendationService.getPersonalizedRecommendations(userId, latitude, longitude);
        return ResponseEntity.ok(new ApiResponse<>(true, "Recommendations fetched successfully", recommendations));
    }

    @PostMapping("/voice-order")
    public ResponseEntity<ApiResponse<?>> processVoiceOrder(
            @RequestParam("audio") MultipartFile audioFile,
            @RequestParam Long userId) {
        var order = voiceService.processVoiceOrder(audioFile, userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Voice order processed successfully", order));
    }

    @PostMapping("/analyze-image")
    public ResponseEntity<ApiResponse<?>> analyzeFoodImage(
            @RequestParam("image") MultipartFile image) {
        var analysis = visionService.analyzeFoodImage(image);
        return ResponseEntity.ok(new ApiResponse<>(true, "Image analyzed successfully", analysis));
    }

    @PostMapping("/emotion-analysis")
    public ResponseEntity<ApiResponse<?>> analyzeEmotion(
            @RequestBody String userInput) {
        var suggestions = emotionService.analyzeMoodForFoodRecommendation(userInput);
        return ResponseEntity.ok(new ApiResponse<>(true, "Emotion analyzed successfully", suggestions));
    }

    @GetMapping("/weather-recommendations")
    public ResponseEntity<ApiResponse<?>> getWeatherBasedRecommendations(
            @RequestParam String latitude,
            @RequestParam String longitude) {
        var recommendations = weatherService.getWeatherBasedRecommendations(latitude, longitude);
        return ResponseEntity.ok(new ApiResponse<>(true, "Weather-based recommendations fetched", recommendations));
    }

    @PostMapping("/nutrition/plan")
    public ResponseEntity<ApiResponse<?>> createNutritionPlan(
            @RequestBody SmartNutritionPlan plan) {
        var createdPlan = nutritionService.createNutritionPlan(plan);
        return ResponseEntity.ok(new ApiResponse<>(true, "Nutrition plan created successfully", createdPlan));
    }

    @GetMapping("/nutrition/plan/{userId}")
    public ResponseEntity<ApiResponse<?>> getNutritionPlan(
            @PathVariable Long userId) {
        var plan = nutritionService.getNutritionPlan(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Nutrition plan fetched successfully", plan));
    }

    @PutMapping("/nutrition/plan/{userId}")
    public ResponseEntity<ApiResponse<?>> updateNutritionPlan(
            @PathVariable Long userId,
            @RequestBody SmartNutritionPlan plan) {
        var updatedPlan = nutritionService.updateNutritionPlan(userId, plan);
        return ResponseEntity.ok(new ApiResponse<>(true, "Nutrition plan updated successfully", updatedPlan));
    }
}