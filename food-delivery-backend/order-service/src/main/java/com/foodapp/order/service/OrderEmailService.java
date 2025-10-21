package com.foodapp.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderEmailService {

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

    public void sendOrderConfirmationEmail(String customerEmail, String customerName, String orderId, 
                                         String restaurantName, BigDecimal orderTotal, List<String> items, 
                                         String deliveryAddress, String estimatedDeliveryTime) {
        String subject = "Order Confirmed! 🎉";
        String itemsList = String.join("<br>• ", items);
        
        String htmlContent = String.format("""
            <html>
            <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
                <div style="max-width: 600px; margin: 0 auto; background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);">
                    <div style="text-align: center; margin-bottom: 30px;">
                        <h1 style="color: #FF6B35; margin: 0;">🐆 Hungry Cheetah</h1>
                        <p style="color: #666; margin: 5px 0;">Fast Food, Faster Delivery</p>
                    </div>
                    
                    <div style="background-color: #e8f5e8; padding: 15px; border-radius: 8px; margin: 20px 0; border-left: 4px solid #4CAF50;">
                        <h2 style="color: #2e7d32; margin: 0;">🎉 Order Confirmed!</h2>
                    </div>
                    
                    <p style="color: #555; line-height: 1.6;">
                        Hi %s,<br><br>
                        Thank you for your order! We're preparing your delicious meal and it will be delivered soon.
                    </p>
                    
                    <div style="background-color: #f8f9fa; padding: 20px; border-radius: 8px; margin: 20px 0;">
                        <h3 style="margin-top: 0; color: #333;">Order Details</h3>
                        <p><strong>Order ID:</strong> %s</p>
                        <p><strong>Restaurant:</strong> %s</p>
                        <p><strong>Total:</strong> $%.2f</p>
                        <p><strong>Estimated Delivery:</strong> %s</p>
                        <p><strong>Delivery Address:</strong> %s</p>
                        
                        <h4 style="color: #333; margin-top: 20px;">Items Ordered:</h4>
                        <div style="margin-left: 20px; color: #555;">
                            • %s
                        </div>
                    </div>
                    
                    <div style="text-align: center; margin: 30px 0;">
                        <a href="http://localhost:3000/orders/%s" 
                           style="background-color: #FF6B35; color: white; padding: 12px 30px; text-decoration: none; border-radius: 25px; font-weight: bold; display: inline-block; margin-right: 10px;">
                            Track Order
                        </a>
                        <a href="http://localhost:3000/support" 
                           style="background-color: #6c757d; color: white; padding: 12px 30px; text-decoration: none; border-radius: 25px; font-weight: bold; display: inline-block;">
                            Need Help?
                        </a>
                    </div>
                    
                    <p style="color: #777; font-size: 12px; text-align: center;">
                        You'll receive updates about your order status. Enjoy your meal! 🍽️
                    </p>
                </div>
            </body>
            </html>
            """, customerName, orderId, restaurantName, orderTotal, estimatedDeliveryTime, deliveryAddress, itemsList, orderId);
        
        sendHtmlEmail(customerEmail, subject, htmlContent);
    }

    public void sendOrderStatusUpdateEmail(String customerEmail, String customerName, String orderId, 
                                         String oldStatus, String newStatus, String statusMessage) {
        String subject = String.format("Order Update: %s", newStatus);
        String statusColor = getStatusColor(newStatus);
        String statusIcon = getStatusIcon(newStatus);
        
        String htmlContent = String.format("""
            <html>
            <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
                <div style="max-width: 600px; margin: 0 auto; background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);">
                    <div style="text-align: center; margin-bottom: 30px;">
                        <h1 style="color: #FF6B35; margin: 0;">🐆 Hungry Cheetah</h1>
                    </div>
                    
                    <h2 style="color: #333;">Hi %s!</h2>
                    <p style="color: #555; line-height: 1.6;">
                        Your order status has been updated:
                    </p>
                    
                    <div style="background-color: %s; color: white; padding: 15px; border-radius: 8px; margin: 20px 0; text-align: center;">
                        <h3 style="margin: 0; font-size: 20px;">%s %s</h3>
                        <p style="margin: 5px 0; opacity: 0.9;">Order ID: %s</p>
                    </div>
                    
                    <div style="background-color: #f8f9fa; padding: 20px; border-radius: 8px; margin: 20px 0;">
                        <p style="margin: 0; color: #555;">%s</p>
                    </div>
                    
                    <div style="text-align: center; margin: 30px 0;">
                        <a href="http://localhost:3000/orders/%s" 
                           style="background-color: #FF6B35; color: white; padding: 12px 30px; text-decoration: none; border-radius: 25px; font-weight: bold; display: inline-block;">
                            Track Order
                        </a>
                    </div>
                </div>
            </body>
            </html>
            """, customerName, statusColor, statusIcon, newStatus, orderId, statusMessage, orderId);
        
        sendHtmlEmail(customerEmail, subject, htmlContent);
    }

    public void sendOrderCancellationEmail(String customerEmail, String customerName, String orderId, 
                                         String restaurantName, BigDecimal refundAmount, String cancellationReason) {
        String subject = "Order Cancelled";
        
        String htmlContent = String.format("""
            <html>
            <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
                <div style="max-width: 600px; margin: 0 auto; background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);">
                    <div style="text-align: center; margin-bottom: 30px;">
                        <h1 style="color: #FF6B35; margin: 0;">🐆 Hungry Cheetah</h1>
                    </div>
                    
                    <h2 style="color: #333;">Hi %s,</h2>
                    <p style="color: #555; line-height: 1.6;">
                        We're sorry to inform you that your order has been cancelled.
                    </p>
                    
                    <div style="background-color: #fff3cd; padding: 15px; border-radius: 8px; margin: 20px 0; border-left: 4px solid #ffc107;">
                        <h3 style="color: #856404; margin-top: 0;">Order Cancelled</h3>
                        <p><strong>Order ID:</strong> %s</p>
                        <p><strong>Restaurant:</strong> %s</p>
                        <p><strong>Refund Amount:</strong> $%.2f</p>
                    </div>
                    
                    <div style="background-color: #f8f9fa; padding: 20px; border-radius: 8px; margin: 20px 0;">
                        <h4 style="margin-top: 0; color: #333;">Reason for Cancellation:</h4>
                        <p style="margin: 0; color: #555;">%s</p>
                    </div>
                    
                    <p style="color: #555; line-height: 1.6;">
                        Your refund will be processed within 3-5 business days. We apologize for any inconvenience caused.
                    </p>
                    
                    <div style="text-align: center; margin: 30px 0;">
                        <a href="http://localhost:3000/restaurants" 
                           style="background-color: #FF6B35; color: white; padding: 12px 30px; text-decoration: none; border-radius: 25px; font-weight: bold; display: inline-block; margin-right: 10px;">
                            Order Again
                        </a>
                        <a href="http://localhost:3000/support" 
                           style="background-color: #6c757d; color: white; padding: 12px 30px; text-decoration: none; border-radius: 25px; font-weight: bold; display: inline-block;">
                            Contact Support
                        </a>
                    </div>
                </div>
            </body>
            </html>
            """, customerName, orderId, restaurantName, refundAmount, cancellationReason);
        
        sendHtmlEmail(customerEmail, subject, htmlContent);
    }

    public void sendDeliveryUpdateEmail(String customerEmail, String customerName, String orderId, 
                                      String driverName, String driverPhone, String estimatedArrival) {
        String subject = "Your Order is On the Way! 🚗";
        
        String htmlContent = String.format("""
            <html>
            <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
                <div style="max-width: 600px; margin: 0 auto; background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);">
                    <div style="text-align: center; margin-bottom: 30px;">
                        <h1 style="color: #FF6B35; margin: 0;">🐆 Hungry Cheetah</h1>
                    </div>
                    
                    <div style="background-color: #e3f2fd; padding: 15px; border-radius: 8px; margin: 20px 0; border-left: 4px solid #2196F3;">
                        <h2 style="color: #1976D2; margin: 0;">🚗 Your Order is On the Way!</h2>
                    </div>
                    
                    <p style="color: #555; line-height: 1.6;">
                        Hi %s,<br><br>
                        Great news! Your order is now being delivered to you.
                    </p>
                    
                    <div style="background-color: #f8f9fa; padding: 20px; border-radius: 8px; margin: 20px 0;">
                        <h3 style="margin-top: 0; color: #333;">Delivery Details</h3>
                        <p><strong>Order ID:</strong> %s</p>
                        <p><strong>Driver:</strong> %s</p>
                        <p><strong>Driver Phone:</strong> %s</p>
                        <p><strong>Estimated Arrival:</strong> %s</p>
                    </div>
                    
                    <div style="text-align: center; margin: 30px 0;">
                        <a href="http://localhost:3000/orders/%s/track" 
                           style="background-color: #2196F3; color: white; padding: 12px 30px; text-decoration: none; border-radius: 25px; font-weight: bold; display: inline-block;">
                            Track Live Location
                        </a>
                    </div>
                    
                    <p style="color: #777; font-size: 12px; text-align: center;">
                        Please be available to receive your order. Enjoy your meal! 🍽️
                    </p>
                </div>
            </body>
            </html>
            """, customerName, orderId, driverName, driverPhone, estimatedArrival, orderId);
        
        sendHtmlEmail(customerEmail, subject, htmlContent);
    }

    public void sendOrderCompletedEmail(String customerEmail, String customerName, String orderId, 
                                      String restaurantName, BigDecimal orderTotal) {
        String subject = "Order Delivered Successfully! ⭐";
        
        String htmlContent = String.format("""
            <html>
            <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
                <div style="max-width: 600px; margin: 0 auto; background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);">
                    <div style="text-align: center; margin-bottom: 30px;">
                        <h1 style="color: #FF6B35; margin: 0;">🐆 Hungry Cheetah</h1>
                    </div>
                    
                    <div style="background-color: #e8f5e8; padding: 15px; border-radius: 8px; margin: 20px 0; border-left: 4px solid #4CAF50;">
                        <h2 style="color: #2e7d32; margin: 0;">✅ Order Delivered Successfully!</h2>
                    </div>
                    
                    <p style="color: #555; line-height: 1.6;">
                        Hi %s,<br><br>
                        Your order has been delivered successfully! We hope you enjoyed your meal.
                    </p>
                    
                    <div style="background-color: #f8f9fa; padding: 20px; border-radius: 8px; margin: 20px 0;">
                        <h3 style="margin-top: 0; color: #333;">Order Summary</h3>
                        <p><strong>Order ID:</strong> %s</p>
                        <p><strong>Restaurant:</strong> %s</p>
                        <p><strong>Total:</strong> $%.2f</p>
                        <p><strong>Delivered:</strong> %s</p>
                    </div>
                    
                    <div style="text-align: center; margin: 30px 0;">
                        <a href="http://localhost:3000/orders/%s/review" 
                           style="background-color: #FF6B35; color: white; padding: 12px 30px; text-decoration: none; border-radius: 25px; font-weight: bold; display: inline-block; margin-right: 10px;">
                            Rate & Review
                        </a>
                        <a href="http://localhost:3000/restaurants/%s" 
                           style="background-color: #4CAF50; color: white; padding: 12px 30px; text-decoration: none; border-radius: 25px; font-weight: bold; display: inline-block;">
                            Order Again
                        </a>
                    </div>
                    
                    <p style="color: #777; font-size: 12px; text-align: center;">
                        Thank you for choosing Hungry Cheetah! Your feedback helps us improve. 🙏
                    </p>
                </div>
            </body>
            </html>
            """, customerName, orderId, restaurantName, orderTotal, 
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' hh:mm a")), 
                orderId, restaurantName);
        
        sendHtmlEmail(customerEmail, subject, htmlContent);
    }

    private String getStatusColor(String status) {
        return switch (status.toUpperCase()) {
            case "CONFIRMED", "PREPARING" -> "#FF9800";
            case "READY", "OUT_FOR_DELIVERY" -> "#2196F3";
            case "DELIVERED", "COMPLETED" -> "#4CAF50";
            case "CANCELLED" -> "#F44336";
            default -> "#6c757d";
        };
    }

    private String getStatusIcon(String status) {
        return switch (status.toUpperCase()) {
            case "CONFIRMED" -> "✅";
            case "PREPARING" -> "👨‍🍳";
            case "READY" -> "🍽️";
            case "OUT_FOR_DELIVERY" -> "🚗";
            case "DELIVERED", "COMPLETED" -> "✅";
            case "CANCELLED" -> "❌";
            default -> "📋";
        };
    }
}