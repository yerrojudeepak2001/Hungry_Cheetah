package com.foodapp.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.user.dto.NotificationDto;
import com.foodapp.user.dto.NotificationPreferenceDto;
import java.util.List;

@FeignClient(name = "NOTIFICATION-SERVICE", fallback = NotificationClientFallback.class)
public interface NotificationClient {
    
    @PostMapping("/api/notifications/send-email")
    void sendEmail(@RequestBody NotificationDto notification);
    
    @PostMapping("/api/notifications/send-sms")
    void sendSms(@RequestBody NotificationDto notification);
    
    @PostMapping("/api/notifications/send-push")
    void sendPushNotification(@RequestBody NotificationDto notification);
    
    @GetMapping("/api/notifications/user/{userId}")
    List<NotificationDto> getUserNotifications(@PathVariable("userId") String userId);
    
    @PostMapping("/api/notifications/user/{userId}/mark-read/{notificationId}")
    void markNotificationAsRead(
            @PathVariable("userId") String userId,
            @PathVariable("notificationId") String notificationId);
    
    @GetMapping("/api/notifications/user/{userId}/preferences")
    NotificationPreferenceDto getNotificationPreferences(@PathVariable("userId") String userId);
    
    @PutMapping("/api/notifications/user/{userId}/preferences")
    NotificationPreferenceDto updateNotificationPreferences(
            @PathVariable("userId") String userId,
            @RequestBody NotificationPreferenceDto preferences);
    
    @PostMapping("/api/notifications/user/{userId}/subscribe-push")
    void subscribeToPushNotifications(
            @PathVariable("userId") String userId,
            @RequestParam String deviceToken);
    
    @DeleteMapping("/api/notifications/user/{userId}/unsubscribe-push")
    void unsubscribeFromPushNotifications(@PathVariable("userId") String userId);
}