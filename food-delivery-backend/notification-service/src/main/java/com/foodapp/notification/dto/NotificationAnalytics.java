package com.foodapp.notification.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationAnalytics {
    private Long userId;
    private String timeFrame;
    private Long totalNotificationsSent;
    private Long totalNotificationsRead;
    private Long totalNotificationsClicked;
    private Double readRate;
    private Double clickRate;
    private Map<String, Long> notificationsByChannel;
    private Map<String, Long> notificationsByType;
    private Map<String, Double> engagementByChannel;
    private Map<String, Double> engagementByType;
    private LocalDateTime periodStart;
    private LocalDateTime periodEnd;
}