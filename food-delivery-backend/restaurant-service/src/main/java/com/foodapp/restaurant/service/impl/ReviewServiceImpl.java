package com.foodapp.restaurant.service.impl;

import com.foodapp.restaurant.document.ReviewDocument;
import com.foodapp.restaurant.repository.jpa.RestaurantRepository;
import com.foodapp.restaurant.repository.jpa.ReviewRepository;
import com.foodapp.restaurant.repository.mongo.ReviewMongoRepository;
import com.foodapp.restaurant.service.ReviewService;
import com.foodapp.restaurant.model.Restaurant;
import com.foodapp.restaurant.model.Review;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewMongoRepository reviewMongoRepository;

    @Override
    public Review addReview(Long restaurantId, Review review) {
        // Verify restaurant exists
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + restaurantId));
        
        // Create MongoDB review document with enhanced features
        ReviewDocument reviewDoc = ReviewDocument.builder()
                .restaurantId(restaurantId)
                .userId(review.getUserId())
                .userName(review.getUserName())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .verified(review.isVerified())
                .isEdited(false)
                .helpfulCount(0)
                .notHelpfulCount(0)
                .status("APPROVED")
                .build();
        
        ReviewDocument savedDoc = reviewMongoRepository.save(reviewDoc);
        
        // Convert back to Review entity for response
        review.setId(Long.parseLong(savedDoc.getId().hashCode() + ""));
        review.setRestaurant(restaurant);
        review.setCreatedAt(savedDoc.getCreatedAt());
        
        return review;
    }

    @Override
    public Review updateReview(Long restaurantId, Long reviewId, Review review) {
        // Verify restaurant exists
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new RuntimeException("Restaurant not found with id: " + restaurantId);
        }
        
        // Find review in MongoDB by converting reviewId to string
        List<ReviewDocument> reviews = reviewMongoRepository.findByRestaurantId(restaurantId);
        ReviewDocument existingReview = reviews.stream()
                .filter(r -> r.getId().hashCode() == reviewId.intValue())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + reviewId));
        
        // Update fields
        existingReview.setRating(review.getRating());
        existingReview.setComment(review.getComment());
        existingReview.setUpdatedAt(LocalDateTime.now());
        existingReview.setEdited(true);
        
        ReviewDocument updatedDoc = reviewMongoRepository.save(existingReview);
        
        // Convert back to Review entity
        review.setId(Long.parseLong(updatedDoc.getId().hashCode() + ""));
        review.setCreatedAt(updatedDoc.getCreatedAt());
        
        return review;
    }

    @Override
    public void deleteReview(Long restaurantId, Long reviewId) {
        // Verify restaurant exists
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new RuntimeException("Restaurant not found with id: " + restaurantId);
        }
        
        // Find and delete review from MongoDB
        List<ReviewDocument> reviews = reviewMongoRepository.findByRestaurantId(restaurantId);
        ReviewDocument reviewToDelete = reviews.stream()
                .filter(r -> r.getId().hashCode() == reviewId.intValue())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + reviewId));
        
        reviewMongoRepository.deleteById(reviewToDelete.getId());
    }

    @Override
    public List<Review> getRestaurantReviews(Long restaurantId) {
        // Verify restaurant exists
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + restaurantId));
        
        // Get reviews from MongoDB
        List<ReviewDocument> reviewDocs = reviewMongoRepository.findByRestaurantId(restaurantId);
        
        // Convert to Review entities
        return reviewDocs.stream()
                .map(doc -> convertToReview(doc, restaurant))
                .collect(Collectors.toList());
    }

    @Override
    public List<Review> getRestaurantReviews(Long restaurantId, int page, int size) {
        // Verify restaurant exists
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + restaurantId));
        
        // Get paginated reviews from MongoDB
        Pageable pageable = PageRequest.of(page, size);
        List<ReviewDocument> reviewDocs = reviewMongoRepository.findByRestaurantId(restaurantId, pageable).getContent();
        
        // Convert to Review entities
        return reviewDocs.stream()
                .map(doc -> convertToReview(doc, restaurant))
                .collect(Collectors.toList());
    }

    @Override
    public double getAverageRating(Long restaurantId) {
        // Verify restaurant exists
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new RuntimeException("Restaurant not found with id: " + restaurantId);
        }
        
        // Get all ratings from MongoDB and calculate average
        List<ReviewDocument> reviews = reviewMongoRepository.findByRestaurantId(restaurantId);
        
        if (reviews.isEmpty()) {
            return 0.0;
        }
        
        double sum = reviews.stream()
                .mapToInt(ReviewDocument::getRating)
                .sum();
        
        return sum / reviews.size();
    }

    @Override
    public List<Review> getLatestReviews(Long restaurantId, int limit) {
        // Verify restaurant exists
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + restaurantId));
        
        // Get latest reviews from MongoDB
        Pageable pageable = PageRequest.of(0, limit);
        List<ReviewDocument> reviewDocs = reviewMongoRepository.findByRestaurantIdOrderByCreatedAtDesc(restaurantId, pageable);
        
        // Convert to Review entities
        return reviewDocs.stream()
                .map(doc -> convertToReview(doc, restaurant))
                .collect(Collectors.toList());
    }
    
    // Helper method to convert ReviewDocument to Review
    private Review convertToReview(ReviewDocument doc, Restaurant restaurant) {
        Review review = Review.builder()
                .id(Long.parseLong(doc.getId().hashCode() + ""))
                .userId(doc.getUserId())
                .userName(doc.getUserName())
                .rating(doc.getRating())
                .comment(doc.getComment())
                .createdAt(doc.getCreatedAt())
                .verified(doc.isVerified())
                .restaurant(restaurant)
                .build();
        
        return review;
    }
}