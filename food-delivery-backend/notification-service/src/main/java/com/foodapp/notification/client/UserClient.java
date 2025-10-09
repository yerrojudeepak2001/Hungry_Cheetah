package com.foodapp.notification.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.notification.dto.UserNotificationPreferences;
import com.foodapp.notification.dto.UserContactInfo;

@FeignClient(name = "USER-SERVICE", fallback = UserClientFallback.class)
public interface UserClient {
    @GetMapping("/api/users/{userId}/notification-preferences")
    UserNotificationPreferences getNotificationPreferences(@PathVariable("userId") String userId);
    
    @GetMapping("/api/users/{userId}/contact-info")
    UserContactInfo getUserContactInfo(@PathVariable("userId") String userId);
    
    @PutMapping("/api/users/{userId}/notification-sent")
    void updateLastNotificationSent(@PathVariable("userId") String userId, @RequestParam String type);
}