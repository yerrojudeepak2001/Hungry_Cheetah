package com.foodapp.user.client;

import com.foodapp.user.dto.NotificationDto;
import com.foodapp.user.dto.NotificationPreferenceDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.List;

@Component
public class NotificationClientFallback implements NotificationClient {
    
    private static final Logger logger = LoggerFactory.getLogger(NotificationClientFallback.class);
    
    @Override
    public void sendEmail(NotificationDto notification) {
        logger.error("Notification service is unavailable. Cannot send email notification to: {}", 
                notification.getRecipient());
    }
    
    @Override
    public void sendSms(NotificationDto notification) {
        logger.error("Notification service is unavailable. Cannot send SMS notification to: {}", 
                notification.getRecipient());
    }
    
    @Override
    public void sendPushNotification(NotificationDto notification) {
        logger.error("Notification service is unavailable. Cannot send push notification to: {}", 
                notification.getRecipient());
    }
    
    @Override
    public List<NotificationDto> getUserNotifications(String userId) {
        logger.error("Notification service is unavailable. Returning empty notifications for user: {}", userId);
        return Collections.emptyList();
    }
    
    @Override
    public void markNotificationAsRead(String userId, String notificationId) {
        logger.error("Notification service is unavailable. Cannot mark notification {} as read for user: {}", 
                notificationId, userId);
    }
    
    @Override
    public NotificationPreferenceDto getNotificationPreferences(String userId) {
        logger.error("Notification service is unavailable. Returning default preferences for user: {}", userId);
        return new NotificationPreferenceDto(); // Return default preferences
    }
    
    @Override
    public NotificationPreferenceDto updateNotificationPreferences(String userId, NotificationPreferenceDto preferences) {
        logger.error("Notification service is unavailable. Cannot update notification preferences for user: {}", userId);
        return preferences; // Return the same preferences
    }
    
    @Override
    public void subscribeToPushNotifications(String userId, String deviceToken) {
        logger.error("Notification service is unavailable. Cannot subscribe user {} to push notifications", userId);
    }
    
    @Override
    public void unsubscribeFromPushNotifications(String userId) {
        logger.error("Notification service is unavailable. Cannot unsubscribe user {} from push notifications", userId);
    }
}