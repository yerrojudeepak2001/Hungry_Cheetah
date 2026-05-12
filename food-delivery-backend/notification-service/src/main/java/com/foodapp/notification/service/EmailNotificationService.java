package com.foodapp.notification.service;

import com.foodapp.notification.model.Notification;
import com.foodapp.notification.dto.UserContactInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailNotificationService {

    private final JavaMailSender mailSender;
    
    private static final String FROM_EMAIL = "hungrycheetah62@gmail.com";

    public void sendSimpleEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(FROM_EMAIL);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            
            mailSender.send(message);
            log.info("Simple email sent successfully to: {}", to);
        } catch (Exception e) {
            log.error("Error sending simple email to: {}", to, e);
            throw new RuntimeException("Failed to send email", e);
        }
    }

    public void sendHtmlEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setFrom(FROM_EMAIL);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            
            mailSender.send(message);
            log.info("HTML email sent successfully to: {}", to);
        } catch (MessagingException e) {
            log.error("Error sending HTML email to: {}", to, e);
            throw new RuntimeException("Failed to send HTML email", e);
        }
    }

    public void sendOrderConfirmationEmail(String userEmail, String userName, String orderId, double totalAmount) {
        String subject = "Order Confirmed! 🎉 - Hungry Cheetah";
        String htmlContent = String.format("""
            <html>
            <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
                <div style="max-width: 600px; margin: 0 auto; padding: 20px;">
                    <h1 style="color: #FF6B35;">Hi %s!</h1>
                    <p>Thank you for your order! We're preparing your delicious meal with care.</p>
                    <div style="background-color: #f9f9f9; padding: 15px; border-radius: 5px; margin: 20px 0;">
                        <h3 style="margin-top: 0;">Order Details:</h3>
                        <p><strong>Order ID:</strong> %s</p>
                        <p><strong>Total Amount:</strong> $%.2f</p>
                    </div>
                    <p>We'll notify you once your order is ready for delivery.</p>
                    <p style="text-align: center;">
                        <a href="http://localhost:3000/orders/%s" 
                           style="background-color: #4CAF50; color: white; padding: 12px 25px; text-decoration: none; border-radius: 5px; display: inline-block;">
                           Track Your Order
                        </a>
                    </p>
                    <br>
                    <p style="color: #666;">Best regards,<br><strong>Hungry Cheetah Team</strong></p>
                </div>
            </body>
            </html>
            """, userName, orderId, totalAmount, orderId);
        
        sendHtmlEmail(userEmail, subject, htmlContent);
    }

    public void sendOrderStatusUpdateEmail(String userEmail, String userName, String orderId, String status) {
        String subject = String.format("Order Update: %s - Hungry Cheetah", status);
        String htmlContent = String.format("""
            <html>
            <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
                <div style="max-width: 600px; margin: 0 auto; padding: 20px;">
                    <h1 style="color: #FF6B35;">Hi %s!</h1>
                    <p>Your order status has been updated:</p>
                    <div style="background-color: #e8f5e8; padding: 15px; border-radius: 5px; margin: 20px 0; border-left: 4px solid #4CAF50;">
                        <h3 style="margin-top: 0;">Order #%s</h3>
                        <p><strong>Current Status:</strong> <span style="color: #4CAF50; font-weight: bold;">%s</span></p>
                    </div>
                    <p style="text-align: center;">
                        <a href="http://localhost:3000/orders/%s" 
                           style="background-color: #FF6B35; color: white; padding: 12px 25px; text-decoration: none; border-radius: 5px; display: inline-block;">
                           View Order Details
                        </a>
                    </p>
                    <br>
                    <p style="color: #666;">Best regards,<br><strong>Hungry Cheetah Team</strong></p>
                </div>
            </body>
            </html>
            """, userName, orderId, status, orderId);
        
        sendHtmlEmail(userEmail, subject, htmlContent);
    }

    public void sendDeliveryUpdateEmail(String userEmail, String userName, String orderId, String deliveryStatus, String estimatedTime) {
        String subject = String.format("Delivery Update: %s - Hungry Cheetah", deliveryStatus);
        String htmlContent = String.format("""
            <html>
            <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
                <div style="max-width: 600px; margin: 0 auto; padding: 20px;">
                    <h1 style="color: #FF6B35;">Hi %s!</h1>
                    <p>Your delivery status has been updated:</p>
                    <div style="background-color: #fff3cd; padding: 15px; border-radius: 5px; margin: 20px 0; border-left: 4px solid #ffc107;">
                        <h3 style="margin-top: 0;">Order #%s</h3>
                        <p><strong>Delivery Status:</strong> <span style="color: #856404; font-weight: bold;">%s</span></p>
                        <p><strong>Estimated Time:</strong> %s</p>
                    </div>
                    <p style="text-align: center;">
                        <a href="http://localhost:3000/tracking/%s" 
                           style="background-color: #28a745; color: white; padding: 12px 25px; text-decoration: none; border-radius: 5px; display: inline-block;">
                           Track Live Location
                        </a>
                    </p>
                    <br>
                    <p style="color: #666;">Best regards,<br><strong>Hungry Cheetah Team</strong></p>
                </div>
            </body>
            </html>
            """, userName, orderId, deliveryStatus, estimatedTime, orderId);
        
        sendHtmlEmail(userEmail, subject, htmlContent);
    }
    
    public boolean sendNotification(Notification notification, UserContactInfo userInfo) {
        try {
            if (userInfo.getEmail() == null || userInfo.getEmail().trim().isEmpty()) {
                log.warn("Cannot send email notification - user email not available for user: {}", userInfo.getUserId());
                return false;
            }
            
            String userName = userInfo.getFirstName() != null ? userInfo.getFirstName() : "Valued Customer";
            
            if (notification.getTemplateId() != null) {
                // Use template-based email (would integrate with TemplateService)
                sendHtmlEmail(userInfo.getEmail(), notification.getTitle(), notification.getMessage());
            } else {
                // Send simple email
                sendSimpleEmail(userInfo.getEmail(), notification.getTitle(), notification.getMessage());
            }
            
            return true;
        } catch (Exception e) {
            log.error("Failed to send email notification to {}: {}", userInfo.getEmail(), e.getMessage(), e);
            return false;
        }
    }
}