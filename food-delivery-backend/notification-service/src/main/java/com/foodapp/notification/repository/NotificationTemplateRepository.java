package com.foodapp.notification.repository;

import com.foodapp.notification.model.NotificationTemplate;
import com.foodapp.notification.model.NotificationChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplate, String> {
    
    List<NotificationTemplate> findByChannelAndIsActive(NotificationChannel channel, Boolean isActive);
    
    List<NotificationTemplate> findByNotificationTypeAndIsActive(String notificationType, Boolean isActive);
    
    @Query("SELECT t FROM NotificationTemplate t WHERE t.channel = :channel AND t.notificationType = :type AND t.isActive = true")
    Optional<NotificationTemplate> findByChannelAndTypeAndActive(@Param("channel") NotificationChannel channel, @Param("type") String notificationType);
    
    List<NotificationTemplate> findByIsActiveOrderByCreatedAtDesc(Boolean isActive);
}