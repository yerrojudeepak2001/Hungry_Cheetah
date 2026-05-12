package com.foodapp.notification.service;

import com.foodapp.notification.model.NotificationPreferences;
import com.foodapp.notification.repository.NotificationPreferencesRepository;
import com.foodapp.notification.dto.UserNotificationPreferences;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PreferenceService {
    
    private final NotificationPreferencesRepository preferencesRepository;
    
    @Transactional
    public NotificationPreferences updatePreferences(Long userId, NotificationPreferences preferences) {
        try {
            Optional<NotificationPreferences> existingOpt = preferencesRepository.findByUserId(userId);
            
            NotificationPreferences toSave;
            if (existingOpt.isPresent()) {
                toSave = existingOpt.get();
                // Update existing preferences
                updateExistingPreferences(toSave, preferences);
                toSave.setUpdatedAt(LocalDateTime.now());
            } else {
                // Create new preferences
                toSave = preferences;
                toSave.setUserId(userId);
                toSave.setCreatedAt(LocalDateTime.now());
                toSave.setUpdatedAt(LocalDateTime.now());
            }
            
            NotificationPreferences saved = preferencesRepository.save(toSave);
            log.info("Updated notification preferences for user: {}", userId);
            return saved;
            
        } catch (Exception e) {
            log.error("Error updating notification preferences for user {}: {}", userId, e.getMessage(), e);
            throw new RuntimeException("Failed to update notification preferences", e);
        }
    }
    
    public NotificationPreferences getPreferences(Long userId) {
        try {
            return preferencesRepository.findByUserId(userId)
                .orElseGet(() -> createDefaultPreferences(userId));
        } catch (Exception e) {
            log.error("Error getting notification preferences for user {}: {}", userId, e.getMessage(), e);
            return createDefaultPreferences(userId);
        }
    }
    
    public UserNotificationPreferences getUserPreferencesDto(Long userId) {
        try {
            NotificationPreferences prefs = getPreferences(userId);
            return mapToDto(prefs);
        } catch (Exception e) {
            log.error("Error getting user preferences DTO for user {}: {}", userId, e.getMessage(), e);
            return createDefaultDto(userId);
        }
    }
    
    @Transactional
    public void resetToDefaults(Long userId) {
        try {
            preferencesRepository.deleteByUserId(userId);
            log.info("Reset notification preferences to defaults for user: {}", userId);
        } catch (Exception e) {
            log.error("Error resetting notification preferences for user {}: {}", userId, e.getMessage(), e);
            throw new RuntimeException("Failed to reset notification preferences", e);
        }
    }
    
    @Transactional
    public NotificationPreferences enableChannel(Long userId, String channel, boolean enabled) {
        try {
            NotificationPreferences prefs = getPreferences(userId);
            
            switch (channel.toLowerCase()) {
                case "email" -> prefs.setEmailEnabled(enabled);
                case "sms" -> prefs.setSmsEnabled(enabled);
                case "push" -> prefs.setPushEnabled(enabled);
                case "in_app" -> prefs.setInAppEnabled(enabled);
                default -> throw new IllegalArgumentException("Unknown channel: " + channel);
            }
            
            prefs.setUpdatedAt(LocalDateTime.now());
            NotificationPreferences saved = preferencesRepository.save(prefs);
            
            log.info("Updated channel {} to {} for user: {}", channel, enabled, userId);
            return saved;
            
        } catch (Exception e) {
            log.error("Error updating channel {} for user {}: {}", channel, userId, e.getMessage(), e);
            throw new RuntimeException("Failed to update channel preference", e);
        }
    }
    
    @Transactional
    public NotificationPreferences enableNotificationType(Long userId, String notificationType, boolean enabled) {
        try {
            NotificationPreferences prefs = getPreferences(userId);
            
            switch (notificationType.toLowerCase()) {
                case "order" -> prefs.setOrderNotifications(enabled);
                case "promotion" -> prefs.setPromotionNotifications(enabled);
                case "delivery" -> prefs.setDeliveryNotifications(enabled);
                case "marketing" -> prefs.setMarketingNotifications(enabled);
                case "system" -> prefs.setSystemNotifications(enabled);
                default -> throw new IllegalArgumentException("Unknown notification type: " + notificationType);
            }
            
            prefs.setUpdatedAt(LocalDateTime.now());
            NotificationPreferences saved = preferencesRepository.save(prefs);
            
            log.info("Updated notification type {} to {} for user: {}", notificationType, enabled, userId);
            return saved;
            
        } catch (Exception e) {
            log.error("Error updating notification type {} for user {}: {}", notificationType, userId, e.getMessage(), e);
            throw new RuntimeException("Failed to update notification type preference", e);
        }
    }
    
    @Transactional
    public NotificationPreferences setQuietHours(Long userId, Integer startHour, Integer endHour, boolean enabled) {
        try {
            NotificationPreferences prefs = getPreferences(userId);
            
            prefs.setQuietHoursEnabled(enabled);
            prefs.setQuietHoursStart(startHour);
            prefs.setQuietHoursEnd(endHour);
            prefs.setUpdatedAt(LocalDateTime.now());
            
            NotificationPreferences saved = preferencesRepository.save(prefs);
            
            log.info("Updated quiet hours for user {}: enabled={}, start={}, end={}", userId, enabled, startHour, endHour);
            return saved;
            
        } catch (Exception e) {
            log.error("Error updating quiet hours for user {}: {}", userId, e.getMessage(), e);
            throw new RuntimeException("Failed to update quiet hours", e);
        }
    }
    
    @Transactional
    public NotificationPreferences setFrequencyLimit(Long userId, Integer limit) {
        try {
            NotificationPreferences prefs = getPreferences(userId);
            prefs.setFrequencyLimit(limit);
            prefs.setUpdatedAt(LocalDateTime.now());
            
            NotificationPreferences saved = preferencesRepository.save(prefs);
            
            log.info("Updated frequency limit to {} for user: {}", limit, userId);
            return saved;
            
        } catch (Exception e) {
            log.error("Error updating frequency limit for user {}: {}", userId, e.getMessage(), e);
            throw new RuntimeException("Failed to update frequency limit", e);
        }
    }
    
    // Private helper methods
    
    private NotificationPreferences createDefaultPreferences(Long userId) {
        return NotificationPreferences.builder()
            .userId(userId)
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
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
    }
    
    private void updateExistingPreferences(NotificationPreferences existing, NotificationPreferences updates) {
        if (updates.getEmailEnabled() != null) {
            existing.setEmailEnabled(updates.getEmailEnabled());
        }
        if (updates.getSmsEnabled() != null) {
            existing.setSmsEnabled(updates.getSmsEnabled());
        }
        if (updates.getPushEnabled() != null) {
            existing.setPushEnabled(updates.getPushEnabled());
        }
        if (updates.getInAppEnabled() != null) {
            existing.setInAppEnabled(updates.getInAppEnabled());
        }
        if (updates.getOrderNotifications() != null) {
            existing.setOrderNotifications(updates.getOrderNotifications());
        }
        if (updates.getPromotionNotifications() != null) {
            existing.setPromotionNotifications(updates.getPromotionNotifications());
        }
        if (updates.getDeliveryNotifications() != null) {
            existing.setDeliveryNotifications(updates.getDeliveryNotifications());
        }
        if (updates.getMarketingNotifications() != null) {
            existing.setMarketingNotifications(updates.getMarketingNotifications());
        }
        if (updates.getSystemNotifications() != null) {
            existing.setSystemNotifications(updates.getSystemNotifications());
        }
        if (updates.getQuietHoursEnabled() != null) {
            existing.setQuietHoursEnabled(updates.getQuietHoursEnabled());
        }
        if (updates.getQuietHoursStart() != null) {
            existing.setQuietHoursStart(updates.getQuietHoursStart());
        }
        if (updates.getQuietHoursEnd() != null) {
            existing.setQuietHoursEnd(updates.getQuietHoursEnd());
        }
        if (updates.getFrequencyLimit() != null) {
            existing.setFrequencyLimit(updates.getFrequencyLimit());
        }
        if (updates.getPreferredChannels() != null) {
            existing.setPreferredChannels(updates.getPreferredChannels());
        }
    }
    
    private UserNotificationPreferences mapToDto(NotificationPreferences prefs) {
        return UserNotificationPreferences.builder()
            .userId(prefs.getUserId())
            .emailEnabled(prefs.getEmailEnabled())
            .smsEnabled(prefs.getSmsEnabled())
            .pushEnabled(prefs.getPushEnabled())
            .inAppEnabled(prefs.getInAppEnabled())
            .orderNotifications(prefs.getOrderNotifications())
            .promotionNotifications(prefs.getPromotionNotifications())
            .deliveryNotifications(prefs.getDeliveryNotifications())
            .marketingNotifications(prefs.getMarketingNotifications())
            .systemNotifications(prefs.getSystemNotifications())
            .quietHoursEnabled(prefs.getQuietHoursEnabled())
            .quietHoursStart(prefs.getQuietHoursStart())
            .quietHoursEnd(prefs.getQuietHoursEnd())
            .frequencyLimit(prefs.getFrequencyLimit())
            .build();
    }
    
    private UserNotificationPreferences createDefaultDto(Long userId) {
        return UserNotificationPreferences.builder()
            .userId(userId)
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
}