package com.foodapp.notification.service;

import com.foodapp.notification.model.Notification;
import com.foodapp.notification.dto.UserContactInfo;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class PushNotificationService {
    
    @Value("${notification.providers.push.firebase.config-path:}")
    private String firebaseConfigPath;
    
    @Value("${notification.providers.push.enabled:true}")
    private boolean pushEnabled;
    
    private FirebaseApp firebaseApp;
    private boolean firebaseInitialized = false;
    
    // In production, this would be stored in a database
    private final Map<Long, String> userDeviceTokens = new ConcurrentHashMap<>();
    
    @PostConstruct
    public void initializeFirebase() {
        if (!pushEnabled) {
            log.info("Push notifications disabled");
            return;
        }
        
        try {
            if (firebaseConfigPath != null && !firebaseConfigPath.trim().isEmpty()) {
                FileInputStream serviceAccount = new FileInputStream(firebaseConfigPath);
                
                FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
                
                firebaseApp = FirebaseApp.initializeApp(options);
                firebaseInitialized = true;
                log.info("Firebase push notification service initialized successfully");
            } else {
                log.warn("Firebase push notification service not initialized - missing config path");
            }
        } catch (IOException e) {
            log.error("Failed to initialize Firebase push notification service: {}", e.getMessage(), e);
            firebaseInitialized = false;
        }
    }
    
    public boolean sendNotification(Notification notification, UserContactInfo userInfo) {
        if (!pushEnabled || !firebaseInitialized) {
            log.warn("Push notification service not enabled or not initialized");
            return false;
        }
        
        String deviceToken = getUserDeviceToken(userInfo.getUserId());
        if (deviceToken == null || deviceToken.trim().isEmpty()) {
            log.warn("Cannot send push notification - device token not available for user: {}", userInfo.getUserId());
            return false;
        }
        
        try {
            Message message = buildFirebaseMessage(notification, userInfo, deviceToken);
            String response = FirebaseMessaging.getInstance(firebaseApp).send(message);
            
            log.info("Push notification sent successfully. Response: {}, To user: {}", response, userInfo.getUserId());
            return true;
            
        } catch (FirebaseMessagingException e) {
            log.error("Failed to send push notification to user {}: {}", userInfo.getUserId(), e.getMessage(), e);
            
            // Handle specific error codes
            if (MessagingErrorCode.UNREGISTERED.equals(e.getErrorCode())) {
                // Remove invalid token
                removeUserDeviceToken(userInfo.getUserId());
                log.info("Removed invalid device token for user: {}", userInfo.getUserId());
            }
            
            return false;
        } catch (Exception e) {
            log.error("Unexpected error sending push notification to user {}: {}", userInfo.getUserId(), e.getMessage(), e);
            return false;
        }
    }
    
    public boolean sendPushToDevice(String deviceToken, String title, String body, Map<String, String> data) {
        if (!pushEnabled || !firebaseInitialized) {
            log.warn("Push notification service not enabled or not initialized");
            return false;
        }
        
        try {
            Message.Builder messageBuilder = Message.builder()
                .setToken(deviceToken)
                .setNotification(com.google.firebase.messaging.Notification.builder()
                    .setTitle(title)
                    .setBody(body)
                    .build());
            
            if (data != null && !data.isEmpty()) {
                messageBuilder.putAllData(data);
            }
            
            Message message = messageBuilder.build();
            String response = FirebaseMessaging.getInstance(firebaseApp).send(message);
            
            log.info("Push notification sent successfully. Response: {}", response);
            return true;
            
        } catch (FirebaseMessagingException e) {
            log.error("Failed to send push notification to device {}: {}", deviceToken, e.getMessage(), e);
            return false;
        }
    }
    
    public boolean sendOrderConfirmationPush(Long userId, String orderId, String userName) {
        String title = "Order Confirmed! 🎉";
        String body = String.format("Hi %s! Your order #%s has been confirmed and is being prepared.", userName, orderId);
        
        Map<String, String> data = new HashMap<>();
        data.put("type", "order_confirmation");
        data.put("orderId", orderId);
        data.put("action", "view_order");
        
        return sendPushToUser(userId, title, body, data);
    }
    
    public boolean sendOrderStatusUpdatePush(Long userId, String orderId, String status, String userName) {
        String title = "Order Update";
        String body = String.format("Hi %s! Your order #%s is now %s.", userName, orderId, status);
        
        Map<String, String> data = new HashMap<>();
        data.put("type", "order_status");
        data.put("orderId", orderId);
        data.put("status", status);
        data.put("action", "view_order");
        
        return sendPushToUser(userId, title, body, data);
    }
    
    public boolean sendDeliveryUpdatePush(Long userId, String orderId, String deliveryStatus, String estimatedTime, String userName) {
        String title = "Delivery Update";
        String body = String.format("Hi %s! Your order #%s %s. ETA: %s", 
            userName, orderId, deliveryStatus, estimatedTime != null ? estimatedTime : "TBD");
        
        Map<String, String> data = new HashMap<>();
        data.put("type", "delivery_update");
        data.put("orderId", orderId);
        data.put("deliveryStatus", deliveryStatus);
        data.put("estimatedTime", estimatedTime != null ? estimatedTime : "");
        data.put("action", "track_order");
        
        return sendPushToUser(userId, title, body, data);
    }
    
    public boolean sendPromotionPush(Long userId, String promoCode, String description, String userName) {
        String title = "Special Offer! 🎁";
        String body = String.format("Hi %s! %s Use code: %s", userName, description, promoCode);
        
        Map<String, String> data = new HashMap<>();
        data.put("type", "promotion");
        data.put("promoCode", promoCode);
        data.put("action", "view_promotion");
        
        return sendPushToUser(userId, title, body, data);
    }
    
    public boolean sendDeliveryArrivalPush(Long userId, String orderId, String driverName, String userName) {
        String title = "Order Arriving! 🚗";
        String body = String.format("Hi %s! Your order #%s is arriving now! Driver: %s", userName, orderId, driverName);
        
        Map<String, String> data = new HashMap<>();
        data.put("type", "delivery_arrival");
        data.put("orderId", orderId);
        data.put("driverName", driverName);
        data.put("action", "view_order");
        
        return sendPushToUser(userId, title, body, data);
    }
    
    public boolean registerDeviceToken(Long userId, String deviceToken) {
        try {
            if (deviceToken != null && !deviceToken.trim().isEmpty()) {
                userDeviceTokens.put(userId, deviceToken);
                log.info("Registered device token for user: {}", userId);
                return true;
            }
            return false;
        } catch (Exception e) {
            log.error("Error registering device token for user {}: {}", userId, e.getMessage(), e);
            return false;
        }
    }
    
    public boolean unregisterDeviceToken(Long userId) {
        try {
            String removedToken = userDeviceTokens.remove(userId);
            if (removedToken != null) {
                log.info("Unregistered device token for user: {}", userId);
                return true;
            }
            return false;
        } catch (Exception e) {
            log.error("Error unregistering device token for user {}: {}", userId, e.getMessage(), e);
            return false;
        }
    }
    
    public boolean isServiceAvailable() {
        return pushEnabled && firebaseInitialized;
    }
    
    // Private helper methods
    
    private boolean sendPushToUser(Long userId, String title, String body, Map<String, String> data) {
        String deviceToken = getUserDeviceToken(userId);
        if (deviceToken == null) {
            log.warn("No device token available for user: {}", userId);
            return false;
        }
        
        return sendPushToDevice(deviceToken, title, body, data);
    }
    
    private String getUserDeviceToken(Long userId) {
        return userDeviceTokens.get(userId);
    }
    
    private void removeUserDeviceToken(Long userId) {
        userDeviceTokens.remove(userId);
    }
    
    private Message buildFirebaseMessage(Notification notification, UserContactInfo userInfo, String deviceToken) {
        Map<String, String> data = new HashMap<>();
        data.put("notificationId", notification.getId().toString());
        data.put("type", notification.getNotificationType() != null ? notification.getNotificationType() : "general");
        data.put("priority", notification.getPriority().getValue());
        
        if (notification.getActionUrl() != null) {
            data.put("actionUrl", notification.getActionUrl());
        }
        
        if (notification.getReferenceId() != null) {
            data.put("referenceId", notification.getReferenceId().toString());
        }
        
        if (notification.getCategory() != null) {
            data.put("category", notification.getCategory());
        }
        
        // Add custom metadata
        if (notification.getMetadata() != null) {
            data.putAll(notification.getMetadata());
        }
        
        // Build notification payload
        com.google.firebase.messaging.Notification.Builder notificationBuilder = 
            com.google.firebase.messaging.Notification.builder()
                .setTitle(notification.getTitle())
                .setBody(notification.getMessage());
        
        if (notification.getImageUrl() != null) {
            notificationBuilder.setImage(notification.getImageUrl());
        }
        
        // Configure for Android
        AndroidConfig androidConfig = AndroidConfig.builder()
            .setNotification(AndroidNotification.builder()
                .setTitle(notification.getTitle())
                .setBody(notification.getMessage())
                .setIcon("ic_notification")
                .setColor("#FF6B35") // Hungry Cheetah brand color
                .setPriority(mapToAndroidPriority(notification.getPriority()))
                .setChannelId("hungry_cheetah_notifications")
                .build())
            .build();
        
        // Configure for iOS
        ApnsConfig apnsConfig = ApnsConfig.builder()
            .setAps(Aps.builder()
                .setAlert(ApsAlert.builder()
                    .setTitle(notification.getTitle())
                    .setBody(notification.getMessage())
                    .build())
                .setBadge(1)
                .setSound("default")
                .build())
            .build();
        
        return Message.builder()
            .setToken(deviceToken)
            .setNotification(notificationBuilder.build())
            .setAndroidConfig(androidConfig)
            .setApnsConfig(apnsConfig)
            .putAllData(data)
            .build();
    }
    
    private AndroidNotification.Priority mapToAndroidPriority(com.foodapp.notification.model.NotificationPriority priority) {
        return switch (priority) {
            case LOW -> AndroidNotification.Priority.MIN;
            case MEDIUM -> AndroidNotification.Priority.DEFAULT;
            case HIGH -> AndroidNotification.Priority.HIGH;
            case URGENT -> AndroidNotification.Priority.MAX;
        };
    }
}