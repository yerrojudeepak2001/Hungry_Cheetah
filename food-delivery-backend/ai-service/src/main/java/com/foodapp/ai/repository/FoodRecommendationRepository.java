package com.foodapp.ai.repository;

import com.foodapp.ai.entity.FoodRecommendation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FoodRecommendationRepository extends MongoRepository<FoodRecommendation, String> {
    List<FoodRecommendation> findByUserId(Long userId);
    Page<FoodRecommendation> findByUserId(Long userId, Pageable pageable);
    List<FoodRecommendation> findByUserIdAndCreatedAtAfter(Long userId, LocalDateTime after);
    List<FoodRecommendation> findBySessionId(String sessionId);
    List<FoodRecommendation> findByUserIdAndUserFeedbackIsNotNull(Long userId);
    long countByUserId(Long userId);
}