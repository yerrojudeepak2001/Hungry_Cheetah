package com.foodapp.cart.repository;

import com.foodapp.cart.entity.CartAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CartAnalyticsRepository extends JpaRepository<CartAnalytics, Long> {
    
    List<CartAnalytics> findByUserId(String userId);
    
    List<CartAnalytics> findByCartId(Long cartId);
    
    List<CartAnalytics> findByActionType(String actionType);
    
    @Query("SELECT ca FROM CartAnalytics ca WHERE ca.userId = :userId AND ca.createdAt BETWEEN :startDate AND :endDate")
    List<CartAnalytics> findByUserIdAndDateRange(
        @Param("userId") String userId, 
        @Param("startDate") LocalDateTime startDate, 
        @Param("endDate") LocalDateTime endDate
    );
    
    @Query("SELECT ca FROM CartAnalytics ca WHERE ca.actionType = :actionType AND ca.createdAt BETWEEN :startDate AND :endDate")
    List<CartAnalytics> findByActionTypeAndDateRange(
        @Param("actionType") String actionType, 
        @Param("startDate") LocalDateTime startDate, 
        @Param("endDate") LocalDateTime endDate
    );
    
    @Query("SELECT COUNT(ca) FROM CartAnalytics ca WHERE ca.userId = :userId AND ca.actionType = 'CHECKOUT'")
    Long countCheckoutsByUserId(@Param("userId") String userId);
    
    @Query("SELECT COUNT(ca) FROM CartAnalytics ca WHERE ca.userId = :userId AND ca.actionType = 'ABANDON'")
    Long countAbandonmentsByUserId(@Param("userId") String userId);
    
    @Query("SELECT ca.menuItemId, COUNT(ca) as frequency FROM CartAnalytics ca WHERE ca.userId = :userId AND ca.actionType = 'ADD_ITEM' GROUP BY ca.menuItemId ORDER BY frequency DESC")
    List<Object[]> findMostAddedItemsByUserId(@Param("userId") String userId);
}