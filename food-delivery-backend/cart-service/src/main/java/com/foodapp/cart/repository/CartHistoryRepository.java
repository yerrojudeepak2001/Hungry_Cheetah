package com.foodapp.cart.repository;

import com.foodapp.cart.entity.CartHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CartHistoryRepository extends JpaRepository<CartHistory, Long> {
    
    List<CartHistory> findByCartIdOrderByTimestampDesc(Long cartId);
    
    List<CartHistory> findByUserIdOrderByTimestampDesc(String userId);
    
    List<CartHistory> findByUserIdAndTimestampBetweenOrderByTimestampDesc(
        String userId, LocalDateTime startTime, LocalDateTime endTime);
    
    @Query("SELECT ch FROM CartHistory ch WHERE ch.userId = :userId AND ch.actionType = :actionType " +
           "ORDER BY ch.timestamp DESC")
    List<CartHistory> findByUserIdAndActionType(@Param("userId") String userId, 
                                              @Param("actionType") String actionType);
    
    @Query("SELECT COUNT(ch) FROM CartHistory ch WHERE ch.userId = :userId AND " +
           "ch.actionType = :actionType AND ch.timestamp >= :since")
    Long countByUserIdAndActionTypeSince(@Param("userId") String userId, 
                                       @Param("actionType") String actionType, 
                                       @Param("since") LocalDateTime since);
    
    @Query("SELECT ch.actionType, COUNT(ch) FROM CartHistory ch WHERE ch.userId = :userId " +
           "GROUP BY ch.actionType ORDER BY COUNT(ch) DESC")
    List<Object[]> getUserActionStatistics(@Param("userId") String userId);
    
    void deleteByTimestampBefore(LocalDateTime before);
}