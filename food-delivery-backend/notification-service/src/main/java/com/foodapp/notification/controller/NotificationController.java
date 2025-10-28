package com.foodapp.notification.controller;

import com.foodapp.notification.dto.ApiResponse;
import com.foodapp.notification.model.*;
import com.foodapp.notification.service.NotificationService;
import com.foodapp.notification.service.PreferenceService;
import com.foodapp.notification.service.TemplateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;
    private final PreferenceService preferenceService;
    private final TemplateService templateService;

    public NotificationController(NotificationService notificationService,
            PreferenceService preferenceService,
            TemplateService templateService) {
        this.notificationService = notificationService;
        this.preferenceService = preferenceService;
        this.templateService = templateService;
    }

    // Notification Management
    @PostMapping("/send")
    public ResponseEntity<ApiResponse<?>> sendNotification(@RequestBody NotificationRequest request) {
        var sent = notificationService.sendNotification(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Notification sent successfully", sent));
    }

    @PostMapping("/bulk")
    public ResponseEntity<ApiResponse<?>> sendBulkNotifications(@RequestBody BulkNotificationRequest request) {
        var sent = notificationService.sendBulkNotifications(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Bulk notifications sent successfully", sent));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<?>> getUserNotifications(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        var notifications = notificationService.getUserNotifications(userId, page, size);
        return ResponseEntity.ok(new ApiResponse<>(true, "Notifications fetched successfully", notifications));
    }

    // Preferences
    @PostMapping("/preferences/{userId}")
    public ResponseEntity<ApiResponse<?>> updateNotificationPreferences(
            @PathVariable Long userId,
            @RequestBody NotificationPreferences preferences) {
        var updated = preferenceService.updatePreferences(userId, preferences);
        return ResponseEntity.ok(new ApiResponse<>(true, "Notification preferences updated successfully", updated));
    }

    @GetMapping("/preferences/{userId}")
    public ResponseEntity<ApiResponse<?>> getNotificationPreferences(@PathVariable Long userId) {
        var preferences = preferenceService.getPreferences(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Notification preferences fetched successfully", preferences));
    }

    // Templates
    @PostMapping("/templates")
    public ResponseEntity<ApiResponse<?>> createTemplate(@RequestBody NotificationTemplate template) {
        var created = templateService.createTemplate(template);
        return ResponseEntity.ok(new ApiResponse<>(true, "Template created successfully", created));
    }

    @GetMapping("/templates/{templateId}")
    public ResponseEntity<ApiResponse<?>> getTemplate(@PathVariable String templateId) {
        var template = templateService.getTemplate(templateId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Template fetched successfully", template));
    }

    // Channel Management
    @PostMapping("/channels")
    public ResponseEntity<ApiResponse<?>> addNotificationChannel(
            @RequestParam Long userId,
            @RequestBody NotificationChannel channel) {
        var added = notificationService.addChannel(userId, channel);
        return ResponseEntity.ok(new ApiResponse<>(true, "Notification channel added successfully", added));
    }

    @GetMapping("/channels/{userId}")
    public ResponseEntity<ApiResponse<?>> getUserChannels(@PathVariable Long userId) {
        var channels = notificationService.getUserChannels(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "User channels fetched successfully", channels));
    }

    // Notification Status
    @PutMapping("/{notificationId}/status")
    public ResponseEntity<ApiResponse<?>> updateNotificationStatus(
            @PathVariable String notificationId,
            @RequestParam NotificationStatus status) {
        var updated = notificationService.updateStatus(notificationId, status);
        return ResponseEntity.ok(new ApiResponse<>(true, "Notification status updated successfully", updated));
    }

    @PostMapping("/read-all/{userId}")
    public ResponseEntity<ApiResponse<?>> markAllAsRead(@PathVariable Long userId) {
        notificationService.markAllAsRead(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "All notifications marked as read", null));
    }

    // Smart Notifications
    @PostMapping("/smart-alerts")
    public ResponseEntity<ApiResponse<?>> configureSmartAlerts(
            @RequestParam Long userId,
            @RequestBody SmartAlertConfig config) {
        var configured = notificationService.configureSmartAlerts(userId, config);
        return ResponseEntity.ok(new ApiResponse<>(true, "Smart alerts configured successfully", configured));
    }

    @GetMapping("/analytics/{userId}")
    public ResponseEntity<ApiResponse<?>> getNotificationAnalytics(
            @PathVariable Long userId,
            @RequestParam String timeFrame) {
        var analytics = notificationService.getAnalytics(userId, timeFrame);
        return ResponseEntity.ok(new ApiResponse<>(true, "Notification analytics fetched successfully", analytics));
    }
}