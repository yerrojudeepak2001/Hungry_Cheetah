package com.foodapp.notification.repository;

import com.foodapp.notification.model.Notification;
import com.foodapp.notification.model.NotificationStatus;
import com.foodapp.notification.model.NotificationChannel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    
    Page<Notification> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
    
    Page<Notification> findByUserIdAndIsReadOrderByCreatedAtDesc(Long userId, Boolean isRead, Pageable pageable);
    
    List<Notification> findByUserIdAndChannelAndCreatedAtAfter(Long userId, NotificationChannel channel, LocalDateTime after);
    
    List<Notification> findByStatusAndScheduledAtBefore(NotificationStatus status, LocalDateTime scheduledTime);
    
    List<Notification> findByStatusAndExpiresAtBefore(NotificationStatus status, LocalDateTime expiresAt);
    
    @Query("SELECT COUNT(n) FROM Notification n WHERE n.userId = :userId AND n.createdAt >= :startDate")
    Long countNotificationsByUserAndDate(@Param("userId") Long userId, @Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT COUNT(n) FROM Notification n WHERE n.userId = :userId AND n.channel = :channel AND n.createdAt >= :startDate")
    Long countNotificationsByUserChannelAndDate(@Param("userId") Long userId, @Param("channel") NotificationChannel channel, @Param("startDate") LocalDateTime startDate);
    
    @Modifying
    @Query("UPDATE Notification n SET n.isRead = true, n.readAt = :readAt WHERE n.userId = :userId AND n.isRead = false")
    int markAllAsReadByUserId(@Param("userId") Long userId, @Param("readAt") LocalDateTime readAt);
    
    @Modifying
    @Query("UPDATE Notification n SET n.status = :status WHERE n.id = :notificationId")
    int updateNotificationStatus(@Param("notificationId") Long notificationId, @Param("status") NotificationStatus status);
    
    @Query("SELECT n FROM Notification n WHERE n.userId = :userId AND n.notificationType = :type AND n.createdAt >= :startDate ORDER BY n.createdAt DESC")
    List<Notification> findRecentNotificationsByType(@Param("userId") Long userId, @Param("type") String type, @Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT COUNT(n) FROM Notification n WHERE n.userId = :userId AND n.status = :status AND n.createdAt BETWEEN :startDate AND :endDate")
    Long countNotificationsByStatusAndDateRange(@Param("userId") Long userId, @Param("status") NotificationStatus status, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT n.channel, COUNT(n) FROM Notification n WHERE n.userId = :userId AND n.createdAt BETWEEN :startDate AND :endDate GROUP BY n.channel")
    List<Object[]> getNotificationCountByChannel(@Param("userId") Long userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT n.notificationType, COUNT(n) FROM Notification n WHERE n.userId = :userId AND n.createdAt BETWEEN :startDate AND :endDate GROUP BY n.notificationType")
    List<Object[]> getNotificationCountByType(@Param("userId") Long userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}