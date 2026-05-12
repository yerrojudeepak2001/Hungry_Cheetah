package com.foodapp.user.repository;

import com.foodapp.user.model.UserAuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserAuditLogRepository extends JpaRepository<UserAuditLog, Long> {
    
    List<UserAuditLog> findByUserIdOrderByTimestampDesc(Long userId);
    
    List<UserAuditLog> findByUserIdAndActionOrderByTimestampDesc(Long userId, String action);
    
    @Query("SELECT u FROM UserAuditLog u WHERE u.userId = :userId AND u.timestamp BETWEEN :startDate AND :endDate ORDER BY u.timestamp DESC")
    List<UserAuditLog> findByUserIdAndTimestampBetween(
            @Param("userId") Long userId, 
            @Param("startDate") LocalDateTime startDate, 
            @Param("endDate") LocalDateTime endDate);
    
    Page<UserAuditLog> findByUserIdOrderByTimestampDesc(Long userId, Pageable pageable);
}