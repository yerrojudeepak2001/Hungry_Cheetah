package com.foodapp.tracking.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.tracking.dto.NotificationRequest;

@FeignClient(name = "NOTIFICATION-SERVICE", fallback = NotificationClientFallback.class)
public interface NotificationClient {
    @PostMapping("/api/notifications/tracking")
    void sendTrackingUpdate(@RequestBody NotificationRequest request);
    
    @PostMapping("/api/notifications/delay")
    void sendDelayNotification(@RequestBody DelayNotificationRequest request);
    
    @PostMapping("/api/notifications/arrival")
    void sendArrivalNotification(@RequestBody ArrivalNotificationRequest request);
}