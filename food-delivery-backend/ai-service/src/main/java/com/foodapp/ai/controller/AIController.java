package com.foodapp.ai.controller;

import com.foodapp.ai.dto.RecommendationRequest;
import com.foodapp.ai.dto.FeedbackRequest;
import com.foodapp.ai.entity.FoodRecommendation;
import com.foodapp.ai.entity.UserPreference;
import com.foodapp.ai.service.AIRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Optional;

@RestController
@RequestMapping("/api/ai")
//@CrossOrigin(origins = "*")
public class AIController {
    
    private final AIRecommendationService aiRecommendationService;

    @Autowired
    public AIController(AIRecommendationService aiRecommendationService) {
        this.aiRecommendationService = aiRecommendationService;
    }

    @PostMapping("/recommendations")
    public Mono<ResponseEntity<Map<String, Object>>> getPersonalizedRecommendations(
            @Valid @RequestBody RecommendationRequest request,
            HttpServletRequest httpRequest) {
        
        String sessionId = UUID.randomUUID().toString();
        
        return aiRecommendationService.getPersonalizedRecommendations(
                request.getUserId(), 
                request.getMood(), 
                request.getOccasion(), 
                sessionId)
                .map(recommendation -> {
                    Map<String, Object> response = new java.util.HashMap<>();
                    response.put("success", true);
                    response.put("message", "AI recommendations generated successfully");
                    response.put("data", recommendation);
                    response.put("sessionId", sessionId);
                    return ResponseEntity.ok(response);
                })
                .onErrorReturn(this.createErrorResponse("Unable to generate recommendations at the moment"));
    }

    @PostMapping("/nutrition-analysis")
    public Mono<ResponseEntity<Map<String, Object>>> getNutritionAnalysis(
            @RequestParam String foodItem,
            @RequestParam Long userId,
            HttpServletRequest httpRequest) {
        
        String sessionId = UUID.randomUUID().toString();
        
        return aiRecommendationService.getNutritionAnalysis(foodItem, userId, sessionId)
                .map(analysis -> {
                    Map<String, Object> response = new java.util.HashMap<>();
                    response.put("success", true);
                    response.put("message", "Nutrition analysis completed");
                    response.put("data", analysis);
                    response.put("sessionId", sessionId);
                    return ResponseEntity.ok(response);
                })
                .onErrorReturn(this.createErrorResponse("Unable to analyze nutrition information"));
    }

    @PostMapping("/recipe-generation")
    public Mono<ResponseEntity<Map<String, Object>>> generateRecipe(
            @RequestParam String dishName,
            @RequestParam(defaultValue = "4") Integer servings,
            @RequestParam(defaultValue = "medium") String difficulty,
            @RequestParam Long userId,
            HttpServletRequest httpRequest) {
        
        String sessionId = UUID.randomUUID().toString();
        
        return aiRecommendationService.generateRecipe(dishName, servings, difficulty, userId, sessionId)
                .map(recipe -> {
                    Map<String, Object> response = new java.util.HashMap<>();
                    response.put("success", true);
                    response.put("message", "Recipe generated successfully");
                    response.put("data", recipe);
                    response.put("sessionId", sessionId);
                    return ResponseEntity.ok(response);
                })
                .onErrorReturn(this.createErrorResponse("Unable to generate recipe"));
    }

    @PostMapping("/mood-analysis")
    public Mono<ResponseEntity<Map<String, Object>>> analyzeFoodMood(
            @RequestBody Map<String, String> request,
            HttpServletRequest httpRequest) {
        
        String userInput = request.get("userInput");
        Long userId = Long.parseLong(request.getOrDefault("userId", "0"));
        String sessionId = UUID.randomUUID().toString();
        
        return aiRecommendationService.analyzeFoodMood(userInput, userId, sessionId)
                .map(analysis -> {
                    Map<String, Object> response = new java.util.HashMap<>();
                    response.put("success", true);
                    response.put("message", "Mood analysis completed");
                    response.put("data", analysis);
                    response.put("sessionId", sessionId);
                    return ResponseEntity.ok(response);
                })
                .onErrorReturn(this.createErrorResponse("Unable to analyze mood"));
    }

    private ResponseEntity<Map<String, Object>> createErrorResponse(String message) {
        Map<String, Object> errorResponse = new java.util.HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("message", message);
        errorResponse.put("error", "AI service temporarily unavailable");
        return ResponseEntity.status(500).body(errorResponse);
    }

    @GetMapping("/recommendations/history/{userId}")
    public ResponseEntity<Map<String, Object>> getUserRecommendationHistory(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "10") int limit) {
        
        try {
            List<FoodRecommendation> history = aiRecommendationService.getUserRecommendationHistory(userId, limit);
            
            Map<String, Object> response = new java.util.HashMap<>();
            response.put("success", true);
            response.put("message", "Recommendation history retrieved successfully");
            response.put("data", history);
            response.put("count", history.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new java.util.HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Unable to retrieve recommendation history");
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @PostMapping("/recommendations/feedback")
    public ResponseEntity<Map<String, Object>> recordUserFeedback(
            @Valid @RequestBody FeedbackRequest request) {
        
        try {
            aiRecommendationService.recordUserFeedback(
                request.getRecommendationId(), 
                request.getLiked(), 
                request.getNotes()
            );
            
            Map<String, Object> response = new java.util.HashMap<>();
            response.put("success", true);
            response.put("message", "Feedback recorded successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new java.util.HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Unable to record feedback");
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @PostMapping("/preferences/{userId}")
    public ResponseEntity<Map<String, Object>> saveUserPreferences(
            @PathVariable Long userId,
            @Valid @RequestBody UserPreference preferences) {
        
        try {
            UserPreference saved = aiRecommendationService.saveUserPreferences(userId, preferences);
            
            Map<String, Object> response = new java.util.HashMap<>();
            response.put("success", true);
            response.put("message", "User preferences saved successfully");
            response.put("data", saved);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new java.util.HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Unable to save user preferences");
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/preferences/{userId}")
    public ResponseEntity<Map<String, Object>> getUserPreferences(@PathVariable Long userId) {
        try {
            Optional<UserPreference> preferencesOpt = aiRecommendationService.getUserPreferences(userId);
            if (preferencesOpt.isPresent()) {
                Map<String, Object> response = new java.util.HashMap<>();
                response.put("success", true);
                response.put("message", "User preferences retrieved successfully");
                response.put("data", preferencesOpt.get());
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new java.util.HashMap<>();
                response.put("success", true);
                response.put("message", "No preferences found");
                response.put("data", new UserPreference(userId));
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            Map<String, Object> errorResponse = new java.util.HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Unable to retrieve user preferences");
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/analytics/recommendations/{userId}")
    public ResponseEntity<Map<String, Object>> getRecommendationStats(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "30") int days) {
        
        try {
            List<Map<String, Object>> stats = aiRecommendationService.getRecommendationStats(userId, days);
            
            Map<String, Object> response = new java.util.HashMap<>();
            response.put("success", true);
            response.put("message", "Recommendation statistics retrieved successfully");
            response.put("data", stats);
            response.put("period", days + " days");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new java.util.HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Unable to retrieve recommendation statistics");
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new java.util.HashMap<>();
        response.put("success", true);
        response.put("message", "AI Service is running");
        response.put("timestamp", System.currentTimeMillis());
        response.put("service", "ai-service");
        return ResponseEntity.ok(response);
    }
}