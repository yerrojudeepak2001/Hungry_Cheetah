package com.foodapp.notification.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import com.foodapp.notification.model.NotificationChannel;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserNotificationPreferences {
    private Long userId;
    private Boolean emailEnabled;
    private Boolean smsEnabled;
    private Boolean pushEnabled;
    private Boolean inAppEnabled;
    private Boolean orderNotifications;
    private Boolean promotionNotifications;
    private Boolean deliveryNotifications;
    private Boolean marketingNotifications;
    private Boolean systemNotifications;
    private Boolean quietHoursEnabled;
    private Integer quietHoursStart;
    private Integer quietHoursEnd;
    private Integer frequencyLimit;
}