package com.foodapp.ai.repository;

import com.foodapp.ai.entity.AIAnalytics;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AIAnalyticsRepository extends MongoRepository<AIAnalytics, String> {
    List<AIAnalytics> findByUserId(Long userId);
    List<AIAnalytics> findByUserIdAndTimestampAfter(Long userId, LocalDateTime after);
    List<AIAnalytics> findByEventType(String eventType);
    List<AIAnalytics> findByEventTypeAndTimestampBetween(String eventType, LocalDateTime start, LocalDateTime end);
    
    @Query("{'userId': ?0, 'successful': true}")
    List<AIAnalytics> findSuccessfulEventsByUserId(Long userId);
    
    @Query("{'eventType': ?0, 'timestamp': {'$gte': ?1}}")
    List<AIAnalytics> findRecentEventsByType(String eventType, LocalDateTime since);
    
    long countByEventTypeAndTimestampAfter(String eventType, LocalDateTime after);
    long countBySuccessfulAndTimestampAfter(Boolean successful, LocalDateTime after);
}