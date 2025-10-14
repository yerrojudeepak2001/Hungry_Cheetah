package com.foodapp.restaurant.service.impl;

import com.foodapp.restaurant.repository.RestaurantRepository;
import com.foodapp.restaurant.repository.ReviewRepository;
import com.foodapp.restaurant.service.ReviewService;
import com.foodapp.restaurant.model.Restaurant;
import com.foodapp.restaurant.model.Review;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public Review addReview(Long restaurantId, Review review) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + restaurantId));
                
        review.setRestaurant(restaurant);
        review.setCreatedAt(LocalDateTime.now());
        return reviewRepository.save(review);
    }

    @Override
    public Review updateReview(Long restaurantId, Long reviewId, Review review) {
        // Verify review exists and belongs to the restaurant
        Review existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + reviewId));
                
        if (!existingReview.getRestaurant().getId().equals(restaurantId)) {
            throw new RuntimeException("Review does not belong to restaurant with id: " + restaurantId);
        }
        
        // Update fields but preserve metadata
        existingReview.setRating(review.getRating());
        existingReview.setComment(review.getComment());
        
        return reviewRepository.save(existingReview);
    }

    @Override
    public void deleteReview(Long restaurantId, Long reviewId) {
        // Verify review exists and belongs to the restaurant
        Review existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + reviewId));
                
        if (!existingReview.getRestaurant().getId().equals(restaurantId)) {
            throw new RuntimeException("Review does not belong to restaurant with id: " + restaurantId);
        }
        
        reviewRepository.deleteById(reviewId);
    }

    @Override
    public List<Review> getRestaurantReviews(Long restaurantId) {
        // Verify restaurant exists
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new RuntimeException("Restaurant not found with id: " + restaurantId);
        }
        
        return reviewRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public List<Review> getRestaurantReviews(Long restaurantId, int page, int size) {
        // Verify restaurant exists
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new RuntimeException("Restaurant not found with id: " + restaurantId);
        }
        
        Pageable pageable = PageRequest.of(page, size);
        return reviewRepository.findByRestaurantId(restaurantId, pageable).getContent();
    }

    @Override
    public double getAverageRating(Long restaurantId) {
        // Verify restaurant exists
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new RuntimeException("Restaurant not found with id: " + restaurantId);
        }
        
        Double averageRating = reviewRepository.getAverageRatingByRestaurantId(restaurantId);
        return averageRating != null ? averageRating : 0.0;
    }

    @Override
    public List<Review> getLatestReviews(Long restaurantId, int limit) {
        // Verify restaurant exists
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new RuntimeException("Restaurant not found with id: " + restaurantId);
        }
        
        Pageable pageable = PageRequest.of(0, limit);
        return reviewRepository.findByRestaurantIdOrderByCreatedAtDesc(restaurantId, pageable);
    }
}