package com.foodapp.restaurant.repository.mongo;

import com.foodapp.restaurant.document.VirtualTourDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VirtualTourMongoRepository extends MongoRepository<VirtualTourDocument, String> {
    
    // Find virtual tour by restaurant ID
    Optional<VirtualTourDocument> findByRestaurantId(Long restaurantId);
    
    // Find all active virtual tours
    List<VirtualTourDocument> findByIsActiveTrue();
    
    // Find virtual tours by status
    List<VirtualTourDocument> findByStatus(String status);
    
    // Find virtual tours by type
    List<VirtualTourDocument> findByTourType(String tourType);
    
    // Find virtual tours created after a certain date
    List<VirtualTourDocument> findByCreatedAtAfter(LocalDateTime date);
    
    // Find virtual tours with high view count
    @Query("{ 'viewCount': { $gte: ?0 } }")
    List<VirtualTourDocument> findByViewCountGreaterThanEqual(int minViewCount);
    
    // Find virtual tours by creator
    List<VirtualTourDocument> findByCreatedBy(String userId);
    
    // Find virtual tours with audio guide
    @Query("{ 'audioGuide.enabled': true }")
    List<VirtualTourDocument> findWithAudioGuide();
    
    // Find virtual tours with high completion rate
    @Query("{ $expr: { $gte: [ '$completionCount', { $multiply: [ '$viewCount', ?0 ] } ] } }")
    List<VirtualTourDocument> findByCompletionRateGreaterThan(double rate);
    
    // Count active virtual tours
    long countByIsActiveTrue();
    
    // Check if restaurant has virtual tour
    boolean existsByRestaurantId(Long restaurantId);
    
    // Delete by restaurant ID
    void deleteByRestaurantId(Long restaurantId);
}
