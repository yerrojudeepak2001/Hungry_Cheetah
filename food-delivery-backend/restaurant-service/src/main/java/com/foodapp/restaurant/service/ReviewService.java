package com.foodapp.restaurant.service;

import com.foodapp.restaurant.model.Review;
import java.util.List;

public interface ReviewService {
    Review addReview(Long restaurantId, Review review);
    Review updateReview(Long restaurantId, Long reviewId, Review review);
    void deleteReview(Long restaurantId, Long reviewId);
    List<Review> getRestaurantReviews(Long restaurantId);
    List<Review> getRestaurantReviews(Long restaurantId, int page, int size);
    double getAverageRating(Long restaurantId);
    List<Review> getLatestReviews(Long restaurantId, int limit);
}