package com.foodapp.restaurant.client.fallback;

import com.foodapp.restaurant.client.RecommendationClient;
import com.foodapp.restaurant.dto.RecommendationRequest;
import com.foodapp.restaurant.dto.RecommendationResponse;
import com.foodapp.restaurant.dto.RecommendationFeedback;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Collections;

@Component
public class RecommendationClientFallback implements RecommendationClient {

    @Override
    public RecommendationResponse getPersonalizedRecommendations(RecommendationRequest request) {
        return RecommendationResponse.builder()
                .recommendations(Collections.emptyList())
                .algorithm("FALLBACK")
                .confidence(0.0)
                .build();
    }

    @Override
    public List<RecommendationResponse> getTrendingItems(String locationId) {
        return Collections.emptyList();
    }

    @Override
    public List<RecommendationResponse> getSimilarItems(String itemId) {
        return Collections.emptyList();
    }

    @Override
    public void recordRecommendationFeedback(RecommendationFeedback feedback) {
        // Fallback: Do nothing
    }
}