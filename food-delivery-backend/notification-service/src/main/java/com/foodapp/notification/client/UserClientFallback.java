package com.foodapp.notification.client;

import com.foodapp.notification.dto.UserNotificationPreferences;
import com.foodapp.notification.dto.UserContactInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserClientFallback implements UserClient {
    
    @Override
    public UserNotificationPreferences getNotificationPreferences(String userId) {
        log.warn("UserClient fallback: getNotificationPreferences called for user {}", userId);
        return UserNotificationPreferences.builder()
            .userId(Long.valueOf(userId))
            .emailEnabled(true)
            .smsEnabled(true)
            .pushEnabled(true)
            .inAppEnabled(true)
            .orderNotifications(true)
            .promotionNotifications(true)
            .deliveryNotifications(true)
            .marketingNotifications(false)
            .systemNotifications(true)
            .quietHoursEnabled(false)
            .frequencyLimit(10)
            .build();
    }
    
    @Override
    public UserContactInfo getUserContactInfo(String userId) {
        log.warn("UserClient fallback: getUserContactInfo called for user {}", userId);
        return UserContactInfo.builder()
            .userId(Long.valueOf(userId))
            .email("user" + userId + "@example.com")
            .phoneNumber("+1234567890")
            .firstName("User")
            .lastName(userId)
            .fullName("User " + userId)
            .preferredLanguage("en")
            .timezone("UTC")
            .isActive(true)
            .build();
    }
    
    @Override
    public void updateLastNotificationSent(String userId, String type) {
        log.warn("UserClient fallback: updateLastNotificationSent called for user {} with type {}", userId, type);
        // Do nothing in fallback
    }
}