package com.foodapp.notification.service;

import com.foodapp.notification.model.Notification;
import com.foodapp.notification.model.NotificationStatus;
import com.foodapp.notification.model.NotificationChannel;
import com.foodapp.notification.repository.NotificationRepository;
import com.foodapp.notification.dto.NotificationAnalytics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationAnalyticsService {
    
    private final NotificationRepository notificationRepository;
    
    // Cache for real-time analytics
    private final Map<String, Object> analyticsCache = new ConcurrentHashMap<>();
    
    public NotificationAnalytics getDetailedAnalytics(Long userId, String timeFrame) {
        try {
            LocalDateTime endDate = LocalDateTime.now();
            LocalDateTime startDate = calculateStartDate(endDate, timeFrame);
            
            // Get basic counts
            Long totalSent = countNotificationsByStatusAndDateRange(userId, NotificationStatus.SENT, startDate, endDate);
            Long totalDelivered = countNotificationsByStatusAndDateRange(userId, NotificationStatus.DELIVERED, startDate, endDate);
            Long totalRead = countNotificationsByStatusAndDateRange(userId, NotificationStatus.READ, startDate, endDate);
            Long totalFailed = countNotificationsByStatusAndDateRange(userId, NotificationStatus.FAILED, startDate, endDate);
            
            // Calculate rates
            Double deliveryRate = totalSent > 0 ? (double) totalDelivered / totalSent * 100 : 0.0;
            Double readRate = totalDelivered > 0 ? (double) totalRead / totalDelivered * 100 : 0.0;
            Double failureRate = totalSent > 0 ? (double) totalFailed / totalSent * 100 : 0.0;
            
            // Get breakdowns by channel and type
            Map<String, Long> notificationsByChannel = getNotificationCountByChannel(userId, startDate, endDate);
            Map<String, Long> notificationsByType = getNotificationCountByType(userId, startDate, endDate);
            
            // Calculate engagement rates by channel
            Map<String, Double> engagementByChannel = calculateEngagementByChannel(userId, startDate, endDate);
            Map<String, Double> engagementByType = calculateEngagementByType(userId, startDate, endDate);
            
            return NotificationAnalytics.builder()
                .userId(userId)
                .timeFrame(timeFrame)
                .totalNotificationsSent(totalSent)
                .totalNotificationsRead(totalRead)
                .totalNotificationsClicked(0L) // Would need to track clicks separately
                .readRate(readRate)
                .clickRate(0.0) // Would need to track clicks separately
                .notificationsByChannel(notificationsByChannel)
                .notificationsByType(notificationsByType)
                .engagementByChannel(engagementByChannel)
                .engagementByType(engagementByType)
                .periodStart(startDate)
                .periodEnd(endDate)
                .build();
                
        } catch (Exception e) {
            log.error("Error getting detailed analytics for user {}: {}", userId, e.getMessage(), e);
            return createEmptyAnalytics(userId, timeFrame);
        }
    }
    
    public Map<String, Object> getSystemAnalytics(String timeFrame) {
        try {
            LocalDateTime endDate = LocalDateTime.now();
            LocalDateTime startDate = calculateStartDate(endDate, timeFrame);
            
            Map<String, Object> analytics = new HashMap<>();
            
            // Total system metrics
            Long totalNotifications = notificationRepository.count();
            Long notificationsInPeriod = countAllNotificationsByDateRange(startDate, endDate);
            
            analytics.put("totalNotifications", totalNotifications);
            analytics.put("notificationsInPeriod", notificationsInPeriod);
            
            // Status breakdown
            Map<String, Long> statusBreakdown = getNotificationStatusBreakdown(startDate, endDate);
            analytics.put("statusBreakdown", statusBreakdown);
            
            // Channel performance
            Map<String, Object> channelPerformance = getChannelPerformanceMetrics(startDate, endDate);
            analytics.put("channelPerformance", channelPerformance);
            
            // Peak hours analysis
            Map<String, Long> hourlyDistribution = getHourlyNotificationDistribution(startDate, endDate);
            analytics.put("hourlyDistribution", hourlyDistribution);
            
            analytics.put("periodStart", startDate);
            analytics.put("periodEnd", endDate);
            
            return analytics;
            
        } catch (Exception e) {
            log.error("Error getting system analytics: {}", e.getMessage(), e);
            return new HashMap<>();
        }
    }
    
    public void trackNotificationEvent(Long notificationId, String eventType, Map<String, Object> eventData) {
        try {
            // In a real system, this would update the notification record with event data
            // For now, we'll just log it
            log.info("Notification event tracked: notificationId={}, eventType={}, eventData={}", 
                notificationId, eventType, eventData);
            
            // Could store in a separate analytics table or update the notification metadata
            
        } catch (Exception e) {
            log.error("Error tracking notification event: {}", e.getMessage(), e);
        }
    }
    
    @Scheduled(fixedRate = 300000) // Every 5 minutes
    public void updateAnalyticsCache() {
        try {
            log.debug("Updating analytics cache");
            
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime hourAgo = now.minusHours(1);
            LocalDateTime dayAgo = now.minusDays(1);
            
            // Cache recent metrics
            analyticsCache.put("notifications_last_hour", countAllNotificationsByDateRange(hourAgo, now));
            analyticsCache.put("notifications_last_day", countAllNotificationsByDateRange(dayAgo, now));
            analyticsCache.put("failed_notifications_last_hour", countFailedNotificationsByDateRange(hourAgo, now));
            
            // Cache system health metrics
            analyticsCache.put("system_health_score", calculateSystemHealthScore());
            analyticsCache.put("last_updated", now);
            
        } catch (Exception e) {
            log.error("Error updating analytics cache: {}", e.getMessage(), e);
        }
    }
    
    public Map<String, Object> getCachedAnalytics() {
        return new HashMap<>(analyticsCache);
    }
    
    // Private helper methods
    
    private Long countNotificationsByStatusAndDateRange(Long userId, NotificationStatus status, LocalDateTime startDate, LocalDateTime endDate) {
        if (userId != null) {
            return notificationRepository.countNotificationsByStatusAndDateRange(userId, status, startDate, endDate);
        } else {
            // System-wide count - would need a different repository method
            return 0L;
        }
    }
    
    private Long countAllNotificationsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        // Would need a repository method for this
        return notificationRepository.count(); // Placeholder
    }
    
    private Long countFailedNotificationsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        // Would need a repository method for this
        return 0L; // Placeholder
    }
    
    private Map<String, Long> getNotificationCountByChannel(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        try {
            List<Object[]> results = notificationRepository.getNotificationCountByChannel(userId, startDate, endDate);
            Map<String, Long> channelCounts = new HashMap<>();
            
            for (Object[] result : results) {
                NotificationChannel channel = (NotificationChannel) result[0];
                Long count = (Long) result[1];
                channelCounts.put(channel.getValue(), count);
            }
            
            return channelCounts;
        } catch (Exception e) {
            log.error("Error getting notification count by channel: {}", e.getMessage(), e);
            return new HashMap<>();
        }
    }
    
    private Map<String, Long> getNotificationCountByType(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        try {
            List<Object[]> results = notificationRepository.getNotificationCountByType(userId, startDate, endDate);
            Map<String, Long> typeCounts = new HashMap<>();
            
            for (Object[] result : results) {
                String type = (String) result[0];
                Long count = (Long) result[1];
                typeCounts.put(type != null ? type : "unknown", count);
            }
            
            return typeCounts;
        } catch (Exception e) {
            log.error("Error getting notification count by type: {}", e.getMessage(), e);
            return new HashMap<>();
        }
    }
    
    private Map<String, Double> calculateEngagementByChannel(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        Map<String, Double> engagement = new HashMap<>();
        
        for (NotificationChannel channel : NotificationChannel.values()) {
            try {
                Long sent = notificationRepository.countNotificationsByUserChannelAndDate(userId, channel, startDate);
                Long read = countNotificationsByStatusAndDateRange(userId, NotificationStatus.READ, startDate, endDate);
                
                Double rate = sent > 0 ? (double) read / sent * 100 : 0.0;
                engagement.put(channel.getValue(), rate);
            } catch (Exception e) {
                log.error("Error calculating engagement for channel {}: {}", channel, e.getMessage());
                engagement.put(channel.getValue(), 0.0);
            }
        }
        
        return engagement;
    }
    
    private Map<String, Double> calculateEngagementByType(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        // Implementation would be similar to calculateEngagementByChannel
        return new HashMap<>();
    }
    
    private Map<String, Long> getNotificationStatusBreakdown(LocalDateTime startDate, LocalDateTime endDate) {
        // Would need repository methods for system-wide status breakdown
        Map<String, Long> breakdown = new HashMap<>();
        for (NotificationStatus status : NotificationStatus.values()) {
            breakdown.put(status.getValue(), 0L); // Placeholder
        }
        return breakdown;
    }
    
    private Map<String, Object> getChannelPerformanceMetrics(LocalDateTime startDate, LocalDateTime endDate) {
        Map<String, Object> performance = new HashMap<>();
        
        for (NotificationChannel channel : NotificationChannel.values()) {
            Map<String, Object> channelMetrics = new HashMap<>();
            channelMetrics.put("sent", 0L);
            channelMetrics.put("delivered", 0L);
            channelMetrics.put("failed", 0L);
            channelMetrics.put("deliveryRate", 0.0);
            performance.put(channel.getValue(), channelMetrics);
        }
        
        return performance;
    }
    
    private Map<String, Long> getHourlyNotificationDistribution(LocalDateTime startDate, LocalDateTime endDate) {
        Map<String, Long> distribution = new HashMap<>();
        
        for (int hour = 0; hour < 24; hour++) {
            distribution.put(String.format("%02d:00", hour), 0L);
        }
        
        return distribution;
    }
    
    private Double calculateSystemHealthScore() {
        try {
            LocalDateTime lastHour = LocalDateTime.now().minusHours(1);
            
            // Simple health score based on failure rate in the last hour
            Long totalNotifications = (Long) analyticsCache.getOrDefault("notifications_last_hour", 0L);
            Long failedNotifications = (Long) analyticsCache.getOrDefault("failed_notifications_last_hour", 0L);
            
            if (totalNotifications == 0) {
                return 100.0; // No notifications means perfect health
            }
            
            Double failureRate = (double) failedNotifications / totalNotifications;
            return Math.max(0.0, (1.0 - failureRate) * 100.0);
            
        } catch (Exception e) {
            log.error("Error calculating system health score: {}", e.getMessage(), e);
            return 0.0;
        }
    }
    
    private LocalDateTime calculateStartDate(LocalDateTime endDate, String timeFrame) {
        return switch (timeFrame.toLowerCase()) {
            case "hour" -> endDate.minusHours(1);
            case "day" -> endDate.minusDays(1);
            case "week" -> endDate.minusWeeks(1);
            case "month" -> endDate.minusMonths(1);
            case "year" -> endDate.minusYears(1);
            default -> endDate.minusDays(7); // Default to week
        };
    }
    
    private NotificationAnalytics createEmptyAnalytics(Long userId, String timeFrame) {
        return NotificationAnalytics.builder()
            .userId(userId)
            .timeFrame(timeFrame)
            .totalNotificationsSent(0L)
            .totalNotificationsRead(0L)
            .totalNotificationsClicked(0L)
            .readRate(0.0)
            .clickRate(0.0)
            .notificationsByChannel(new HashMap<>())
            .notificationsByType(new HashMap<>())
            .engagementByChannel(new HashMap<>())
            .engagementByType(new HashMap<>())
            .periodStart(LocalDateTime.now().minusDays(7))
            .periodEnd(LocalDateTime.now())
            .build();
    }
}