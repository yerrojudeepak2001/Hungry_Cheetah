package com.foodapp.restaurant.service.impl;

import com.foodapp.restaurant.service.ReviewService;
import com.foodapp.restaurant.model.Review;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    @Override
    public Review addReview(Long restaurantId, Review review) {
        // Implementation placeholder
        return review;
    }

    @Override
    public Review updateReview(Long restaurantId, Long reviewId, Review review) {
        // Implementation placeholder
        return review;
    }

    @Override
    public void deleteReview(Long restaurantId, Long reviewId) {
        // Implementation placeholder
    }

    @Override
    public List<Review> getRestaurantReviews(Long restaurantId) {
        // Implementation placeholder
        return Collections.emptyList();
    }

    @Override
    public List<Review> getRestaurantReviews(Long restaurantId, int page, int size) {
        // Implementation placeholder
        return Collections.emptyList();
    }

    @Override
    public double getAverageRating(Long restaurantId) {
        // Implementation placeholder
        return 0.0;
    }

    @Override
    public List<Review> getLatestReviews(Long restaurantId, int limit) {
        // Implementation placeholder
        return Collections.emptyList();
    }
}