package com.foodapp.restaurant.repository.mongo;

import com.foodapp.restaurant.document.ReviewDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewMongoRepository extends MongoRepository<ReviewDocument, String> {
    
    // Find reviews by restaurant ID
    List<ReviewDocument> findByRestaurantId(Long restaurantId);
    
    // Find reviews by restaurant ID with pagination
    Page<ReviewDocument> findByRestaurantId(Long restaurantId, Pageable pageable);
    
    // Find reviews by user ID
    List<ReviewDocument> findByUserId(Long userId);
    
    // Find reviews by restaurant and user
    Optional<ReviewDocument> findByRestaurantIdAndUserId(Long restaurantId, Long userId);
    
    // Find reviews by rating
    List<ReviewDocument> findByRestaurantIdAndRating(Long restaurantId, int rating);
    
    // Find verified reviews
    List<ReviewDocument> findByRestaurantIdAndVerifiedTrue(Long restaurantId);
    
    // Find reviews ordered by creation date (latest first)
    List<ReviewDocument> findByRestaurantIdOrderByCreatedAtDesc(Long restaurantId, Pageable pageable);
    
    // Find reviews with photos
    @Query("{ 'restaurantId': ?0, 'photoUrls': { $exists: true, $ne: [] } }")
    List<ReviewDocument> findByRestaurantIdWithPhotos(Long restaurantId);
    
    // Find reviews by status
    List<ReviewDocument> findByRestaurantIdAndStatus(Long restaurantId, String status);
    
    // Find recent reviews (last N days)
    List<ReviewDocument> findByRestaurantIdAndCreatedAtAfter(Long restaurantId, LocalDateTime date);
    
    // Find reviews by rating range
    @Query("{ 'restaurantId': ?0, 'rating': { $gte: ?1, $lte: ?2 } }")
    List<ReviewDocument> findByRestaurantIdAndRatingBetween(Long restaurantId, int minRating, int maxRating);
    
    // Find most helpful reviews
    List<ReviewDocument> findByRestaurantIdOrderByHelpfulCountDesc(Long restaurantId, Pageable pageable);
    
    // Find reviews with restaurant response
    @Query("{ 'restaurantId': ?0, 'restaurantResponse': { $exists: true } }")
    List<ReviewDocument> findByRestaurantIdWithResponse(Long restaurantId);
    
    // Count reviews by restaurant
    long countByRestaurantId(Long restaurantId);
    
    // Count verified reviews
    long countByRestaurantIdAndVerifiedTrue(Long restaurantId);
    
    // Calculate average rating
    @Query(value = "{ 'restaurantId': ?0 }", fields = "{ 'rating': 1 }")
    List<ReviewDocument> findRatingsByRestaurantId(Long restaurantId);
    
    // Find reviews by tags
    List<ReviewDocument> findByRestaurantIdAndTagsContaining(Long restaurantId, String tag);
    
    // Delete reviews by restaurant ID
    void deleteByRestaurantId(Long restaurantId);
}
