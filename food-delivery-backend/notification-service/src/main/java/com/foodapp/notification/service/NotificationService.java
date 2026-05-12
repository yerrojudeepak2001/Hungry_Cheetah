package com.foodapp.notification.service;

import com.foodapp.notification.model.*;
import com.foodapp.notification.repository.NotificationRepository;
import com.foodapp.notification.repository.NotificationPreferencesRepository;
import com.foodapp.notification.dto.NotificationAnalytics;
import com.foodapp.notification.dto.UserContactInfo;
import com.foodapp.notification.client.UserClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    
    private final NotificationRepository notificationRepository;
    private final NotificationPreferencesRepository preferencesRepository;
    private final EmailNotificationService emailService;
    private final SmsNotificationService smsService;
    private final PushNotificationService pushService;
    private final TemplateService templateService;
    private final UserClient userClient;
    
    @Transactional
    public Notification sendNotification(NotificationRequest request) {
        try {
            // Check user preferences
            if (!shouldSendNotification(request.getUserId(), request.getChannel(), request.getNotificationType())) {
                log.info("Notification blocked by user preferences: userId={}, channel={}, type={}", 
                    request.getUserId(), request.getChannel(), request.getNotificationType());
                return null;
            }
            
            // Create notification record
            Notification notification = createNotificationFromRequest(request);
            Notification savedNotification = notificationRepository.save(notification);
            
            // Send the notification asynchronously
            CompletableFuture.runAsync(() -> sendNotificationAsync(savedNotification));
            
            return savedNotification;
        } catch (Exception e) {
            log.error("Error sending notification: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to send notification", e);
        }
    }
    
    @Transactional
    public List<Notification> sendBulkNotifications(BulkNotificationRequest request) {
        List<Notification> notifications = new ArrayList<>();
        
        for (Long userId : request.getUserIds()) {
            try {
                NotificationRequest individualRequest = NotificationRequest.builder()
                    .userId(userId)
                    .title(request.getTitle())
                    .message(request.getMessage())
                    .channel(request.getChannel())
                    .priority(request.getPriority())
                    .notificationType(request.getNotificationType())
                    .templateId(request.getTemplateId())
                    .actionUrl(request.getActionUrl())
                    .imageUrl(request.getImageUrl())
                    .category(request.getCategory())
                    .referenceId(request.getReferenceId())
                    .metadata(request.getMetadata())
                    .templateData(request.getTemplateData())
                    .build();
                
                // Add user-specific data if available
                if (request.getUserSpecificData() != null && request.getUserSpecificData().containsKey(userId)) {
                    individualRequest.setTemplateData(request.getUserSpecificData().get(userId));
                }
                
                Notification notification = sendNotification(individualRequest);
                if (notification != null) {
                    notifications.add(notification);
                }
            } catch (Exception e) {
                log.error("Error sending bulk notification to user {}: {}", userId, e.getMessage(), e);
            }
        }
        
        return notifications;
    }
    
    public Page<Notification> getUserNotifications(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
    }
    
    public boolean addChannel(Long userId, NotificationChannel channel) {
        // This would typically update user preferences to include the new channel
        // For now, we'll just return true
        return true;
    }
    
    public List<NotificationChannel> getUserChannels(Long userId) {
        try {
            return preferencesRepository.findByUserId(userId)
                .map(prefs -> prefs.getPreferredChannels() != null ? 
                    new ArrayList<>(prefs.getPreferredChannels()) : 
                    Arrays.asList(NotificationChannel.values()))
                .orElse(Arrays.asList(NotificationChannel.values()));
        } catch (Exception e) {
            log.error("Error getting user channels for userId {}: {}", userId, e.getMessage());
            return Arrays.asList(NotificationChannel.values());
        }
    }
    
    @Transactional
    public boolean updateStatus(String notificationId, NotificationStatus status) {
        try {
            Long id = Long.parseLong(notificationId);
            int updated = notificationRepository.updateNotificationStatus(id, status);
            return updated > 0;
        } catch (Exception e) {
            log.error("Error updating notification status: {}", e.getMessage(), e);
            return false;
        }
    }
    
    @Transactional
    public void markAllAsRead(Long userId) {
        try {
            notificationRepository.markAllAsReadByUserId(userId, LocalDateTime.now());
            log.info("Marked all notifications as read for user: {}", userId);
        } catch (Exception e) {
            log.error("Error marking all notifications as read for user {}: {}", userId, e.getMessage(), e);
        }
    }
    
    public boolean configureSmartAlerts(Long userId, SmartAlertConfig config) {
        // This would typically save the smart alert configuration to database
        // For now, we'll just return true
        log.info("Configured smart alerts for user: {}", userId);
        return true;
    }
    
    public NotificationAnalytics getAnalytics(Long userId, String timeFrame) {
        try {
            LocalDateTime endDate = LocalDateTime.now();
            LocalDateTime startDate = calculateStartDate(endDate, timeFrame);
            
            Long totalSent = notificationRepository.countNotificationsByStatusAndDateRange(
                userId, NotificationStatus.SENT, startDate, endDate);
            Long totalRead = notificationRepository.countNotificationsByStatusAndDateRange(
                userId, NotificationStatus.READ, startDate, endDate);
            
            Double readRate = totalSent > 0 ? (double) totalRead / totalSent : 0.0;
            
            // Get channel and type breakdowns
            Map<String, Long> byChannel = getNotificationCountByChannel(userId, startDate, endDate);
            Map<String, Long> byType = getNotificationCountByType(userId, startDate, endDate);
            
            return NotificationAnalytics.builder()
                .userId(userId)
                .timeFrame(timeFrame)
                .totalNotificationsSent(totalSent)
                .totalNotificationsRead(totalRead)
                .readRate(readRate)
                .notificationsByChannel(byChannel)
                .notificationsByType(byType)
                .periodStart(startDate)
                .periodEnd(endDate)
                .build();
        } catch (Exception e) {
            log.error("Error getting analytics for user {}: {}", userId, e.getMessage(), e);
            return NotificationAnalytics.builder()
                .userId(userId)
                .timeFrame(timeFrame)
                .totalNotificationsSent(0L)
                .totalNotificationsRead(0L)
                .readRate(0.0)
                .build();
        }
    }
    
    // Private helper methods
    
    private boolean shouldSendNotification(Long userId, NotificationChannel channel, String notificationType) {
        try {
            Optional<NotificationPreferences> prefsOpt = preferencesRepository.findByUserId(userId);
            if (prefsOpt.isEmpty()) {
                return true; // Default to allow if no preferences set
            }
            
            NotificationPreferences prefs = prefsOpt.get();
            
            // Check channel preferences
            boolean channelEnabled = switch (channel) {
                case EMAIL -> prefs.getEmailEnabled();
                case SMS -> prefs.getSmsEnabled();
                case PUSH -> prefs.getPushEnabled();
                case IN_APP -> prefs.getInAppEnabled();
                default -> true;
            };
            
            if (!channelEnabled) {
                return false;
            }
            
            // Check notification type preferences
            if (notificationType != null) {
                boolean typeEnabled = switch (notificationType.toLowerCase()) {
                    case "order" -> prefs.getOrderNotifications();
                    case "promotion" -> prefs.getPromotionNotifications();
                    case "delivery" -> prefs.getDeliveryNotifications();
                    case "marketing" -> prefs.getMarketingNotifications();
                    case "system" -> prefs.getSystemNotifications();
                    default -> true;
                };
                
                if (!typeEnabled) {
                    return false;
                }
            }
            
            // Check frequency limits
            if (prefs.getFrequencyLimit() != null) {
                Long todayCount = notificationRepository.countNotificationsByUserAndDate(
                    userId, LocalDateTime.now().truncatedTo(ChronoUnit.DAYS));
                if (todayCount >= prefs.getFrequencyLimit()) {
                    return false;
                }
            }
            
            // Check quiet hours
            if (prefs.getQuietHoursEnabled() && prefs.getQuietHoursStart() != null && prefs.getQuietHoursEnd() != null) {
                int currentHour = LocalDateTime.now().getHour();
                int start = prefs.getQuietHoursStart();
                int end = prefs.getQuietHoursEnd();
                
                if (start <= end) {
                    if (currentHour >= start && currentHour < end) {
                        return false;
                    }
                } else {
                    if (currentHour >= start || currentHour < end) {
                        return false;
                    }
                }
            }
            
            return true;
        } catch (Exception e) {
            log.error("Error checking notification preferences for user {}: {}", userId, e.getMessage(), e);
            return true; // Default to allow on error
        }
    }
    
    private Notification createNotificationFromRequest(NotificationRequest request) {
        return Notification.builder()
            .userId(request.getUserId())
            .title(request.getTitle())
            .message(request.getMessage())
            .channel(request.getChannel())
            .priority(request.getPriority())
            .notificationType(request.getNotificationType())
            .templateId(request.getTemplateId())
            .actionUrl(request.getActionUrl())
            .imageUrl(request.getImageUrl())
            .category(request.getCategory())
            .referenceId(request.getReferenceId())
            .metadata(request.getMetadata())
            .scheduledAt(request.getScheduledAt())
            .expiresAt(request.getExpiresAt())
            .status(NotificationStatus.PENDING)
            .createdAt(LocalDateTime.now())
            .isRead(false)
            .retryCount(0)
            .build();
    }
    
    private void sendNotificationAsync(Notification notification) {
        try {
            // Get user contact info
            UserContactInfo userInfo = userClient.getUserContactInfo(notification.getUserId().toString());
            
            boolean success = false;
            String errorMessage = null;
            
            switch (notification.getChannel()) {
                case EMAIL -> {
                    if (userInfo.getEmail() != null) {
                        success = emailService.sendNotification(notification, userInfo);
                    } else {
                        errorMessage = "User email not available";
                    }
                }
                case SMS -> {
                    if (userInfo.getPhoneNumber() != null) {
                        success = smsService.sendNotification(notification, userInfo);
                    } else {
                        errorMessage = "User phone number not available";
                    }
                }
                case PUSH -> {
                    success = pushService.sendNotification(notification, userInfo);
                }
                case IN_APP -> {
                    // In-app notifications are handled differently (WebSocket, etc.)
                    success = true; // Mark as sent for now
                }
                case WHATSAPP -> {
                    // WhatsApp notifications not implemented yet
                    errorMessage = "WhatsApp notifications not yet supported";
                    success = false;
                }
            }
            
            // Update notification status
            notification.setStatus(success ? NotificationStatus.SENT : NotificationStatus.FAILED);
            notification.setSentAt(success ? LocalDateTime.now() : null);
            
            if (!success && errorMessage == null) {
                errorMessage = "Failed to send notification";
            }
            
            if (errorMessage != null) {
                log.error("Failed to send notification {}: {}", notification.getId(), errorMessage);
                // Could add error details to metadata
                if (notification.getMetadata() == null) {
                    notification.setMetadata(new HashMap<>());
                }
                notification.getMetadata().put("error", errorMessage);
            }
            
            notificationRepository.save(notification);
            
        } catch (Exception e) {
            log.error("Error in sendNotificationAsync for notification {}: {}", notification.getId(), e.getMessage(), e);
            notification.setStatus(NotificationStatus.FAILED);
            notification.setRetryCount(notification.getRetryCount() + 1);
            notificationRepository.save(notification);
        }
    }
    
    private LocalDateTime calculateStartDate(LocalDateTime endDate, String timeFrame) {
        return switch (timeFrame.toLowerCase()) {
            case "day" -> endDate.minusDays(1);
            case "week" -> endDate.minusWeeks(1);
            case "month" -> endDate.minusMonths(1);
            case "year" -> endDate.minusYears(1);
            default -> endDate.minusDays(7); // Default to week
        };
    }
    
    private Map<String, Long> getNotificationCountByChannel(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        try {
            List<Object[]> results = notificationRepository.getNotificationCountByChannel(userId, startDate, endDate);
            return results.stream()
                .collect(Collectors.toMap(
                    row -> ((NotificationChannel) row[0]).getValue(),
                    row -> (Long) row[1]
                ));
        } catch (Exception e) {
            log.error("Error getting notification count by channel: {}", e.getMessage(), e);
            return new HashMap<>();
        }
    }
    
    private Map<String, Long> getNotificationCountByType(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        try {
            List<Object[]> results = notificationRepository.getNotificationCountByType(userId, startDate, endDate);
            return results.stream()
                .collect(Collectors.toMap(
                    row -> (String) row[0],
                    row -> (Long) row[1]
                ));
        } catch (Exception e) {
            log.error("Error getting notification count by type: {}", e.getMessage(), e);
            return new HashMap<>();
        }
    }
}