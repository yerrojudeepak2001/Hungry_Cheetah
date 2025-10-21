package com.foodapp.notification.messaging;

import com.foodapp.notification.service.EmailNotificationService;
import com.foodapp.notification.service.SmsNotificationService;
import com.foodapp.notification.service.PushNotificationService;
import com.foodapp.notification.service.NotificationService;
import com.foodapp.notification.model.*;
import com.foodapp.notification.dto.UserContactInfo;
import com.foodapp.notification.client.UserClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Map;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationMessageConsumer {

    private final EmailNotificationService emailService;
    private final SmsNotificationService smsService;
    private final PushNotificationService pushService;
    private final NotificationService notificationService;
    private final UserClient userClient;
    private final ObjectMapper objectMapper;
    
    private static final TypeReference<Map<String, Object>> MAP_TYPE_REF = new TypeReference<Map<String, Object>>() {};

    @JmsListener(destination = "notification.send")
    public void handleNotificationMessage(String notificationMessage) {
        try {
            log.info("Received notification message: {}", notificationMessage);
            
            // Parse the notification request
            NotificationRequest request = objectMapper.readValue(notificationMessage, NotificationRequest.class);
            
            // Send notification through the service
            notificationService.sendNotification(request);
            
        } catch (Exception e) {
            log.error("Error processing notification message: {}", notificationMessage, e);
        }
    }

    @KafkaListener(topics = "cart.abandoned", groupId = "notification-service-group")
    public void handleCartAbandonment(String cartAbandonmentMessage) {
        try {
            log.info("Received cart abandonment message: {}", cartAbandonmentMessage);
            
            // Parse cart abandonment data
            Map<String, Object> cartData = objectMapper.readValue(cartAbandonmentMessage, MAP_TYPE_REF);
            Long userId = Long.valueOf(cartData.get("userId").toString());
            String cartId = cartData.get("cartId").toString();
            
            // Get user contact info
            UserContactInfo userInfo = userClient.getUserContactInfo(userId.toString());
            
            // Send cart abandonment notifications
            sendCartAbandonmentNotifications(userId, cartId, userInfo);
            
        } catch (Exception e) {
            log.error("Error processing cart abandonment message: {}", cartAbandonmentMessage, e);
        }
    }

    @KafkaListener(topics = "order.created", groupId = "notification-service-group")
    public void handleOrderCreated(String orderMessage) {
        try {
            log.info("Received order created message: {}", orderMessage);
            
            // Parse order data
            Map<String, Object> orderData = objectMapper.readValue(orderMessage, MAP_TYPE_REF);
            Long userId = Long.valueOf(orderData.get("userId").toString());
            String orderId = orderData.get("orderId").toString();
            Double totalAmount = Double.valueOf(orderData.get("totalAmount").toString());
            
            // Get user contact info
            UserContactInfo userInfo = userClient.getUserContactInfo(userId.toString());
            
            // Send order confirmation notifications
            sendOrderConfirmationNotifications(userId, orderId, totalAmount, userInfo);
            
        } catch (Exception e) {
            log.error("Error processing order created message: {}", orderMessage, e);
        }
    }

    @KafkaListener(topics = "order.status.update", groupId = "notification-service-group")
    public void handleOrderStatusUpdate(String orderStatusMessage) {
        try {
            log.info("Received order status update message: {}", orderStatusMessage);
            
            // Parse order status data
            Map<String, Object> statusData = objectMapper.readValue(orderStatusMessage, MAP_TYPE_REF);
            Long userId = Long.valueOf(statusData.get("userId").toString());
            String orderId = statusData.get("orderId").toString();
            String status = statusData.get("status").toString();
            String previousStatus = statusData.get("previousStatus").toString();
            
            // Get user contact info
            UserContactInfo userInfo = userClient.getUserContactInfo(userId.toString());
            
            // Send order status update notifications
            sendOrderStatusUpdateNotifications(userId, orderId, status, previousStatus, userInfo);
            
        } catch (Exception e) {
            log.error("Error processing order status update message: {}", orderStatusMessage, e);
        }
    }

    @KafkaListener(topics = "delivery.update", groupId = "notification-service-group")
    public void handleDeliveryUpdate(String deliveryMessage) {
        try {
            log.info("Received delivery update message: {}", deliveryMessage);
            
            // Parse delivery data
            Map<String, Object> deliveryData = objectMapper.readValue(deliveryMessage, MAP_TYPE_REF);
            Long userId = Long.valueOf(deliveryData.get("userId").toString());
            String orderId = deliveryData.get("orderId").toString();
            String deliveryStatus = deliveryData.get("deliveryStatus").toString();
            String estimatedTime = (String) deliveryData.get("estimatedTime");
            String driverName = (String) deliveryData.get("driverName");
            
            // Get user contact info
            UserContactInfo userInfo = userClient.getUserContactInfo(userId.toString());
            
            // Send delivery update notifications
            sendDeliveryUpdateNotifications(userId, orderId, deliveryStatus, estimatedTime, driverName, userInfo);
            
        } catch (Exception e) {
            log.error("Error processing delivery update message: {}", deliveryMessage, e);
        }
    }

    @KafkaListener(topics = "promotion.new", groupId = "notification-service-group")
    public void handleNewPromotion(String promotionMessage) {
        try {
            log.info("Received new promotion message: {}", promotionMessage);
            
            // Parse promotion data
            Map<String, Object> promoData = objectMapper.readValue(promotionMessage, MAP_TYPE_REF);
            Long userId = Long.valueOf(promoData.get("userId").toString());
            String promoCode = promoData.get("promoCode").toString();
            String description = promoData.get("description").toString();
            String validUntil = (String) promoData.get("validUntil");
            
            // Get user contact info
            UserContactInfo userInfo = userClient.getUserContactInfo(userId.toString());
            
            // Send promotion notifications
            sendPromotionNotifications(userId, promoCode, description, validUntil, userInfo);
            
        } catch (Exception e) {
            log.error("Error processing promotion message: {}", promotionMessage, e);
        }
    }

    @KafkaListener(topics = "payment.failed", groupId = "notification-service-group")
    public void handlePaymentFailure(String paymentMessage) {
        try {
            log.info("Received payment failure message: {}", paymentMessage);
            
            // Parse payment data
            Map<String, Object> paymentData = objectMapper.readValue(paymentMessage, MAP_TYPE_REF);
            Long userId = Long.valueOf(paymentData.get("userId").toString());
            String orderId = paymentData.get("orderId").toString();
            String reason = (String) paymentData.get("reason");
            
            // Get user contact info
            UserContactInfo userInfo = userClient.getUserContactInfo(userId.toString());
            
            // Send payment failure notifications
            sendPaymentFailureNotifications(userId, orderId, reason, userInfo);
            
        } catch (Exception e) {
            log.error("Error processing payment failure message: {}", paymentMessage, e);
        }
    }
    
    // Private helper methods for sending specific notification types
    
    private void sendCartAbandonmentNotifications(Long userId, String cartId, UserContactInfo userInfo) {
        String userName = getDisplayName(userInfo);
        
        // Email notification
        if (userInfo.getEmail() != null) {
            String subject = "Don't let your meal get away! 🍽️";
            String htmlContent = String.format("""
                <html>
                <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
                    <div style="max-width: 600px; margin: 0 auto; padding: 20px;">
                        <h1 style="color: #FF6B35;">Hi %s!</h1>
                        <p>We noticed you left some delicious items in your cart. Don't let them get away!</p>
                        <div style="background-color: #f9f9f9; padding: 15px; border-radius: 5px; margin: 20px 0;">
                            <p>Complete your order now and satisfy your cravings.</p>
                        </div>
                        <p style="text-align: center;">
                            <a href="http://localhost:3000/cart" 
                               style="background-color: #4CAF50; color: white; padding: 12px 25px; text-decoration: none; border-radius: 5px; display: inline-block;">
                               Complete Your Order
                            </a>
                        </p>
                        <br>
                        <p style="color: #666;">Best regards,<br><strong>Hungry Cheetah Team</strong></p>
                    </div>
                </body>
                </html>
                """, userName);
            emailService.sendHtmlEmail(userInfo.getEmail(), subject, htmlContent);
        }
        
        // Push notification
        if (pushService.isServiceAvailable()) {
            pushService.sendPushToDevice(
                null, // Would need to get device token
                "Don't forget your order! 🛒",
                String.format("Hi %s! You have items waiting in your cart. Complete your order now!", userName),
                Map.of("type", "cart_abandonment", "cartId", cartId, "action", "view_cart")
            );
        }
    }
    
    private void sendOrderConfirmationNotifications(Long userId, String orderId, Double totalAmount, UserContactInfo userInfo) {
        String userName = getDisplayName(userInfo);
        
        // Email notification
        if (userInfo.getEmail() != null) {
            emailService.sendOrderConfirmationEmail(userInfo.getEmail(), userName, orderId, totalAmount);
        }
        
        // SMS notification
        if (userInfo.getPhoneNumber() != null && smsService.isServiceAvailable()) {
            smsService.sendOrderConfirmationSms(userInfo.getPhoneNumber(), orderId, userName);
        }
        
        // Push notification
        if (pushService.isServiceAvailable()) {
            pushService.sendOrderConfirmationPush(userId, orderId, userName);
        }
    }
    
    private void sendOrderStatusUpdateNotifications(Long userId, String orderId, String status, String previousStatus, UserContactInfo userInfo) {
        String userName = getDisplayName(userInfo);
        
        // Email notification
        if (userInfo.getEmail() != null) {
            emailService.sendOrderStatusUpdateEmail(userInfo.getEmail(), userName, orderId, status);
        }
        
        // SMS notification for important status changes
        if (userInfo.getPhoneNumber() != null && smsService.isServiceAvailable() && isImportantStatusChange(status)) {
            smsService.sendOrderStatusUpdateSms(userInfo.getPhoneNumber(), orderId, status, userName);
        }
        
        // Push notification
        if (pushService.isServiceAvailable()) {
            pushService.sendOrderStatusUpdatePush(userId, orderId, status, userName);
        }
    }
    
    private void sendDeliveryUpdateNotifications(Long userId, String orderId, String deliveryStatus, String estimatedTime, String driverName, UserContactInfo userInfo) {
        String userName = getDisplayName(userInfo);
        
        // Email notification
        if (userInfo.getEmail() != null) {
            emailService.sendDeliveryUpdateEmail(userInfo.getEmail(), userName, orderId, deliveryStatus, estimatedTime);
        }
        
        // SMS notification for delivery arrival
        if (userInfo.getPhoneNumber() != null && smsService.isServiceAvailable() && 
            "arriving".equalsIgnoreCase(deliveryStatus) && driverName != null) {
            smsService.sendDeliveryArrivalSms(userInfo.getPhoneNumber(), orderId, driverName, userName);
        }
        
        // Push notification
        if (pushService.isServiceAvailable()) {
            if ("arriving".equalsIgnoreCase(deliveryStatus) && driverName != null) {
                pushService.sendDeliveryArrivalPush(userId, orderId, driverName, userName);
            } else {
                pushService.sendDeliveryUpdatePush(userId, orderId, deliveryStatus, estimatedTime, userName);
            }
        }
    }
    
    private void sendPromotionNotifications(Long userId, String promoCode, String description, String validUntil, UserContactInfo userInfo) {
        String userName = getDisplayName(userInfo);
        
        // Email notification
        if (userInfo.getEmail() != null) {
            String subject = "Special Offer Just For You! 🎁";
            String htmlContent = String.format("""
                <html>
                <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
                    <div style="max-width: 600px; margin: 0 auto; padding: 20px;">
                        <h1 style="color: #FF6B35;">Hi %s!</h1>
                        <p>We have a special offer just for you!</p>
                        <div style="background-color: #e8f5e8; padding: 15px; border-radius: 5px; margin: 20px 0; border-left: 4px solid #4CAF50;">
                            <h3 style="margin-top: 0;">%s</h3>
                            <p><strong>Promo Code:</strong> <span style="background-color: #ffeb3b; padding: 5px 10px; border-radius: 3px; font-weight: bold;">%s</span></p>
                            %s
                        </div>
                        <p style="text-align: center;">
                            <a href="http://localhost:3000/menu" 
                               style="background-color: #FF6B35; color: white; padding: 12px 25px; text-decoration: none; border-radius: 5px; display: inline-block;">
                               Order Now
                            </a>
                        </p>
                        <br>
                        <p style="color: #666;">Best regards,<br><strong>Hungry Cheetah Team</strong></p>
                    </div>
                </body>
                </html>
                """, userName, description, promoCode, 
                validUntil != null ? "<p><strong>Valid until:</strong> " + validUntil + "</p>" : "");
            emailService.sendHtmlEmail(userInfo.getEmail(), subject, htmlContent);
        }
        
        // SMS notification
        if (userInfo.getPhoneNumber() != null && smsService.isServiceAvailable()) {
            smsService.sendPromotionSms(userInfo.getPhoneNumber(), promoCode, description, userName);
        }
        
        // Push notification
        if (pushService.isServiceAvailable()) {
            pushService.sendPromotionPush(userId, promoCode, description, userName);
        }
    }
    
    private void sendPaymentFailureNotifications(Long userId, String orderId, String reason, UserContactInfo userInfo) {
        String userName = getDisplayName(userInfo);
        
        // Email notification
        if (userInfo.getEmail() != null) {
            String subject = "Payment Issue - Action Required";
            String htmlContent = String.format("""
                <html>
                <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
                    <div style="max-width: 600px; margin: 0 auto; padding: 20px;">
                        <h1 style="color: #FF6B35;">Hi %s!</h1>
                        <p>We encountered an issue processing your payment for order #%s.</p>
                        <div style="background-color: #ffeaa7; padding: 15px; border-radius: 5px; margin: 20px 0; border-left: 4px solid #fdcb6e;">
                            <h3 style="margin-top: 0;">Payment Issue</h3>
                            <p><strong>Reason:</strong> %s</p>
                            <p>Please update your payment method to complete your order.</p>
                        </div>
                        <p style="text-align: center;">
                            <a href="http://localhost:3000/orders/%s/payment" 
                               style="background-color: #e17055; color: white; padding: 12px 25px; text-decoration: none; border-radius: 5px; display: inline-block;">
                               Update Payment
                            </a>
                        </p>
                        <br>
                        <p style="color: #666;">Best regards,<br><strong>Hungry Cheetah Team</strong></p>
                    </div>
                </body>
                </html>
                """, userName, orderId, reason != null ? reason : "Payment was declined", orderId);
            emailService.sendHtmlEmail(userInfo.getEmail(), subject, htmlContent);
        }
        
        // Push notification
        if (pushService.isServiceAvailable()) {
            Map<String, String> data = new HashMap<>();
            data.put("type", "payment_failure");
            data.put("orderId", orderId);
            data.put("action", "update_payment");
            
            pushService.sendPushToDevice(
                null, // Would need to get device token
                "Payment Issue",
                String.format("Hi %s! There was an issue with your payment for order #%s. Please update your payment method.", userName, orderId),
                data
            );
        }
    }
    
    private String getDisplayName(UserContactInfo userInfo) {
        if (userInfo.getFirstName() != null && !userInfo.getFirstName().trim().isEmpty()) {
            return userInfo.getFirstName();
        }
        if (userInfo.getFullName() != null && !userInfo.getFullName().trim().isEmpty()) {
            return userInfo.getFullName();
        }
        return "Valued Customer";
    }
    
    private boolean isImportantStatusChange(String status) {
        return status != null && (
            status.equalsIgnoreCase("preparing") ||
            status.equalsIgnoreCase("ready") ||
            status.equalsIgnoreCase("picked_up") ||
            status.equalsIgnoreCase("out_for_delivery") ||
            status.equalsIgnoreCase("delivered")
        );
    }
}