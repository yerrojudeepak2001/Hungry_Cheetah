package com.foodapp.notification.service;

import com.foodapp.notification.model.Notification;
import com.foodapp.notification.dto.UserContactInfo;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
@Slf4j
public class SmsNotificationService {
    
    @Value("${notification.providers.sms.twilio.account-sid:}")
    private String accountSid;
    
    @Value("${notification.providers.sms.twilio.auth-token:}")
    private String authToken;
    
    @Value("${notification.providers.sms.twilio.phone-number:}")
    private String fromPhoneNumber;
    
    @Value("${notification.providers.sms.enabled:true}")
    private boolean smsEnabled;
    
    private boolean twilioInitialized = false;
    
    @PostConstruct
    public void initializeTwilio() {
        try {
            if (smsEnabled && accountSid != null && !accountSid.isEmpty() && 
                authToken != null && !authToken.isEmpty()) {
                Twilio.init(accountSid, authToken);
                twilioInitialized = true;
                log.info("Twilio SMS service initialized successfully");
            } else {
                log.warn("Twilio SMS service not initialized - missing configuration");
            }
        } catch (Exception e) {
            log.error("Failed to initialize Twilio SMS service: {}", e.getMessage(), e);
            twilioInitialized = false;
        }
    }
    
    public boolean sendNotification(Notification notification, UserContactInfo userInfo) {
        if (!smsEnabled || !twilioInitialized) {
            log.warn("SMS service not enabled or not initialized");
            return false;
        }
        
        if (userInfo.getPhoneNumber() == null || userInfo.getPhoneNumber().trim().isEmpty()) {
            log.warn("Cannot send SMS notification - user phone number not available for user: {}", userInfo.getUserId());
            return false;
        }
        
        try {
            String formattedPhoneNumber = formatPhoneNumber(userInfo.getPhoneNumber());
            String messageText = buildSmsMessage(notification, userInfo);
            
            Message message = Message.creator(
                new PhoneNumber(formattedPhoneNumber),
                new PhoneNumber(fromPhoneNumber),
                messageText
            ).create();
            
            log.info("SMS notification sent successfully. MessageSid: {}, To: {}", 
                message.getSid(), formattedPhoneNumber);
            return true;
            
        } catch (Exception e) {
            log.error("Failed to send SMS notification to {}: {}", userInfo.getPhoneNumber(), e.getMessage(), e);
            return false;
        }
    }
    
    public boolean sendSms(String phoneNumber, String message) {
        if (!smsEnabled || !twilioInitialized) {
            log.warn("SMS service not enabled or not initialized");
            return false;
        }
        
        try {
            String formattedPhoneNumber = formatPhoneNumber(phoneNumber);
            
            Message twilioMessage = Message.creator(
                new PhoneNumber(formattedPhoneNumber),
                new PhoneNumber(fromPhoneNumber),
                message
            ).create();
            
            log.info("SMS sent successfully. MessageSid: {}, To: {}", 
                twilioMessage.getSid(), formattedPhoneNumber);
            return true;
            
        } catch (Exception e) {
            log.error("Failed to send SMS to {}: {}", phoneNumber, e.getMessage(), e);
            return false;
        }
    }
    
    public boolean sendOrderConfirmationSms(String phoneNumber, String orderId, String userName) {
        String message = String.format(
            "Hi %s! Your order #%s has been confirmed. We're preparing your delicious meal and will notify you when it's ready for delivery. - Hungry Cheetah",
            userName, orderId
        );
        return sendSms(phoneNumber, message);
    }
    
    public boolean sendOrderStatusUpdateSms(String phoneNumber, String orderId, String status, String userName) {
        String message = String.format(
            "Hi %s! Your order #%s status has been updated to: %s. Track your order for real-time updates. - Hungry Cheetah",
            userName, orderId, status
        );
        return sendSms(phoneNumber, message);
    }
    
    public boolean sendDeliveryUpdateSms(String phoneNumber, String orderId, String deliveryStatus, String estimatedTime, String userName) {
        String message = String.format(
            "Hi %s! Delivery update for order #%s: %s. Estimated time: %s. - Hungry Cheetah",
            userName, orderId, deliveryStatus, estimatedTime != null ? estimatedTime : "TBD"
        );
        return sendSms(phoneNumber, message);
    }
    
    public boolean sendPromotionSms(String phoneNumber, String promoCode, String description, String userName) {
        String message = String.format(
            "Hi %s! 🎉 Special offer just for you: %s. Use code: %s. Order now on Hungry Cheetah!",
            userName, description, promoCode
        );
        return sendSms(phoneNumber, message);
    }
    
    public boolean sendDeliveryArrivalSms(String phoneNumber, String orderId, String driverName, String userName) {
        String message = String.format(
            "Hi %s! Your order #%s is arriving now! Your driver %s is at your location. - Hungry Cheetah",
            userName, orderId, driverName
        );
        return sendSms(phoneNumber, message);
    }
    
    public boolean sendOtpSms(String phoneNumber, String otp, String userName) {
        String message = String.format(
            "Hi %s! Your Hungry Cheetah verification code is: %s. This code will expire in 10 minutes. Please do not share this code with anyone.",
            userName, otp
        );
        return sendSms(phoneNumber, message);
    }
    
    public boolean isServiceAvailable() {
        return smsEnabled && twilioInitialized;
    }
    
    // Private helper methods
    
    private String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            throw new IllegalArgumentException("Phone number cannot be null");
        }
        
        // Remove any non-digit characters
        String cleaned = phoneNumber.replaceAll("[^\\d]", "");
        
        // Add country code if not present (assuming US for now)
        if (cleaned.length() == 10) {
            cleaned = "1" + cleaned;
        }
        
        // Add + prefix
        if (!cleaned.startsWith("+")) {
            cleaned = "+" + cleaned;
        }
        
        return cleaned;
    }
    
    private String buildSmsMessage(Notification notification, UserContactInfo userInfo) {
        StringBuilder messageBuilder = new StringBuilder();
        
        // Add greeting if user name is available
        if (userInfo.getFirstName() != null && !userInfo.getFirstName().trim().isEmpty()) {
            messageBuilder.append("Hi ").append(userInfo.getFirstName()).append("! ");
        }
        
        // Add main message
        messageBuilder.append(notification.getMessage());
        
        // Add action URL if available (shortened for SMS)
        if (notification.getActionUrl() != null && !notification.getActionUrl().trim().isEmpty()) {
            messageBuilder.append(" Track: ").append(shortenUrl(notification.getActionUrl()));
        }
        
        // Add signature
        messageBuilder.append(" - Hungry Cheetah");
        
        // Ensure message is within SMS character limit (160 characters for single SMS)
        String fullMessage = messageBuilder.toString();
        if (fullMessage.length() > 150) { // Leave some buffer
            // Truncate and add ellipsis
            fullMessage = fullMessage.substring(0, 147) + "...";
        }
        
        return fullMessage;
    }
    
    private String shortenUrl(String url) {
        // Simple URL shortening - in production, you might use a proper URL shortening service
        if (url == null || url.length() <= 30) {
            return url;
        }
        
        // For now, just return a placeholder
        return "bit.ly/hc-track";
    }
}