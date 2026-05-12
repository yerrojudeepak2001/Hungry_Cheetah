package com.foodapp.tracking.client;

import com.foodapp.tracking.dto.*;
import org.springframework.stereotype.Component;

@Component
public class NotificationClientFallback implements NotificationClient {

    @Override
    public void sendTrackingUpdate(NotificationRequest request) {
        // Fallback: do nothing
    }

    @Override
    public void sendDelayNotification(DelayNotificationRequest request) {
        // Fallback: do nothing
    }

    @Override
    public void sendArrivalNotification(ArrivalNotificationRequest request) {
        // Fallback: do nothing
    }
}