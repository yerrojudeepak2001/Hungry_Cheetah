package com.foodapp.restaurant.client.fallback;

import com.foodapp.restaurant.client.FeedbackClient;
import com.foodapp.restaurant.dto.FeedbackData;
import com.foodapp.restaurant.dto.RatingInfo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Collections;

@Component
public class FeedbackClientFallback implements FeedbackClient {

    @Override
    public List<FeedbackData> getRestaurantFeedback(String restaurantId) {
        return Collections.emptyList();
    }

    @Override
    public List<FeedbackData> getDeliveryFeedback(String deliveryId) {
        return Collections.emptyList();
    }

    @Override
    public void submitRestaurantFeedback(FeedbackData feedback) {
        // Fallback: Do nothing
    }

    @Override
    public RatingInfo getAggregateRatings(String restaurantId) {
        return RatingInfo.builder()
                .averageRating(0.0)
                .totalReviews(0)
                .build();
    }
}