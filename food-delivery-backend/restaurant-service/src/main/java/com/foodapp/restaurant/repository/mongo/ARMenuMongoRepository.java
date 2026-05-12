package com.foodapp.restaurant.repository.mongo;

import com.foodapp.restaurant.document.ARMenuDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ARMenuMongoRepository extends MongoRepository<ARMenuDocument, String> {
    
    // Find AR menu by restaurant ID
    Optional<ARMenuDocument> findByRestaurantId(Long restaurantId);
    
    // Find all active AR menus
    List<ARMenuDocument> findByIsActiveTrue();
    
    // Find AR menus by status
    List<ARMenuDocument> findByStatus(String status);
    
    // Find AR menus uploaded after a certain date
    List<ARMenuDocument> findByUploadedAtAfter(LocalDateTime date);
    
    // Find AR menus by model format
    List<ARMenuDocument> findByModelFormat(String format);
    
    // Find AR menus with high view count
    @Query("{ 'viewCount': { $gte: ?0 } }")
    List<ARMenuDocument> findByViewCountGreaterThanEqual(int minViewCount);
    
    // Find AR menus by uploaded user
    List<ARMenuDocument> findByUploadedBy(String userId);
    
    // Count active AR menus
    long countByIsActiveTrue();
    
    // Check if restaurant has AR menu
    boolean existsByRestaurantId(Long restaurantId);
    
    // Delete by restaurant ID
    void deleteByRestaurantId(Long restaurantId);
}
