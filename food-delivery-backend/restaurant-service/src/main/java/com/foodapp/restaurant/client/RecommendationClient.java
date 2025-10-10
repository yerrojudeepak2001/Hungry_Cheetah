package com.foodapp.restaurant.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.foodapp.restaurant.dto.RecommendationRequest;
import com.foodapp.restaurant.dto.RecommendationResponse;
import com.foodapp.restaurant.dto.RecommendationFeedback;

@FeignClient(name = "RECOMMENDATION-SERVICE", fallback = com.foodapp.restaurant.client.fallback.RecommendationClientFallback.class)
public interface RecommendationClient {
    @PostMapping("/api/recommendations/personalized")
    RecommendationResponse getPersonalizedRecommendations(
        @RequestBody RecommendationRequest request);
    
    @GetMapping("/api/recommendations/trending/{locationId}")
    List<RecommendationResponse> getTrendingItems(
        @PathVariable("locationId") String locationId);
    
    @GetMapping("/api/recommendations/similar/{itemId}")
    List<RecommendationResponse> getSimilarItems(
        @PathVariable("itemId") String itemId);
    
    @PostMapping("/api/recommendations/feedback")
    void recordRecommendationFeedback(@RequestBody RecommendationFeedback feedback);
}