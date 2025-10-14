package com.foodapp.restaurant.repository;

import com.foodapp.restaurant.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    List<Review> findByRestaurantId(Long restaurantId);
    
    Page<Review> findByRestaurantId(Long restaurantId, Pageable pageable);
    
    List<Review> findByRestaurantIdOrderByCreatedAtDesc(Long restaurantId, Pageable pageable);
    
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.restaurant.id = :restaurantId")
    Double getAverageRatingByRestaurantId(Long restaurantId);
}