package com.foodapp.restaurant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantEmailService {

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

    public void sendRestaurantWelcomeEmail(String restaurantEmail, String restaurantName, String ownerName) {
        String subject = "Welcome to Hungry Cheetah Partner Network! 🎉";
        String htmlContent = String.format("""
            <html>
            <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
                <div style="max-width: 600px; margin: 0 auto; background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);">
                    <div style="text-align: center; margin-bottom: 30px;">
                        <h1 style="color: #FF6B35; margin: 0;">🐆 Hungry Cheetah</h1>
                        <p style="color: #666; margin: 5px 0;">Partner Network</p>
                    </div>
                    
                    <h2 style="color: #333;">Welcome, %s! 🍽️</h2>
                    <p style="color: #555; line-height: 1.6;">
                        Congratulations on joining the Hungry Cheetah partner network! We're excited to have <strong>%s</strong> as part of our food delivery ecosystem.
                    </p>
                    
                    <div style="background-color: #f8f9fa; padding: 20px; border-radius: 8px; margin: 20px 0;">
                        <h3 style="color: #FF6B35; margin-top: 0;">What's Next?</h3>
                        <ul style="color: #555; line-height: 1.8;">
                            <li>📋 Complete your restaurant profile</li>
                            <li>🍕 Add your menu items</li>
                            <li>📊 Set up your operating hours</li>
                            <li>💰 Configure payment settings</li>
                            <li>🚀 Go live and start receiving orders!</li>
                        </ul>
                    </div>
                    
                    <div style="text-align: center; margin: 30px 0;">
                        <a href="http://localhost:3000/restaurant/dashboard" 
                           style="background-color: #FF6B35; color: white; padding: 12px 30px; text-decoration: none; border-radius: 25px; font-weight: bold; display: inline-block;">
                            Access Restaurant Dashboard
                        </a>
                    </div>
                    
                    <p style="color: #777; font-size: 14px; text-align: center; margin-top: 40px;">
                        Need help? Contact our restaurant support team at support@hungrycheetah.com<br>
                        Let's grow together! 🚀
                    </p>
                </div>
            </body>
            </html>
            """, ownerName, restaurantName);
        
        sendHtmlEmail(restaurantEmail, subject, htmlContent);
    }

    public void sendNewOrderNotification(String restaurantEmail, String restaurantName, String orderId, 
                                       String customerName, BigDecimal orderTotal, List<String> items) {
        String subject = "New Order Received! 🛎️";
        String itemsList = String.join("<br>• ", items);
        
        String htmlContent = String.format("""
            <html>
            <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
                <div style="max-width: 600px; margin: 0 auto; background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);">
                    <div style="text-align: center; margin-bottom: 30px;">
                        <h1 style="color: #FF6B35; margin: 0;">🐆 Hungry Cheetah</h1>
                    </div>
                    
                    <div style="background-color: #e8f5e8; padding: 15px; border-radius: 8px; margin: 20px 0; border-left: 4px solid #4CAF50;">
                        <h2 style="color: #2e7d32; margin: 0;">🛎️ New Order for %s!</h2>
                    </div>
                    
                    <div style="background-color: #f8f9fa; padding: 20px; border-radius: 8px; margin: 20px 0;">
                        <h3 style="margin-top: 0; color: #333;">Order Details</h3>
                        <p><strong>Order ID:</strong> %s</p>
                        <p><strong>Customer:</strong> %s</p>
                        <p><strong>Total:</strong> $%.2f</p>
                        
                        <h4 style="color: #333;">Items:</h4>
                        <div style="margin-left: 20px;">
                            • %s
                        </div>
                    </div>
                    
                    <div style="text-align: center; margin: 30px 0;">
                        <a href="http://localhost:3000/restaurant/orders/%s" 
                           style="background-color: #4CAF50; color: white; padding: 12px 30px; text-decoration: none; border-radius: 25px; font-weight: bold; display: inline-block; margin-right: 10px;">
                            View Order
                        </a>
                        <a href="http://localhost:3000/restaurant/orders" 
                           style="background-color: #FF6B35; color: white; padding: 12px 30px; text-decoration: none; border-radius: 25px; font-weight: bold; display: inline-block;">
                            All Orders
                        </a>
                    </div>
                    
                    <p style="color: #777; font-size: 12px; text-align: center;">
                        Please confirm this order as soon as possible to ensure timely delivery.
                    </p>
                </div>
            </body>
            </html>
            """, restaurantName, orderId, customerName, orderTotal, itemsList, orderId);
        
        sendHtmlEmail(restaurantEmail, subject, htmlContent);
    }

    public void sendLowInventoryAlert(String restaurantEmail, String restaurantName, List<String> lowStockItems) {
        String subject = "Low Inventory Alert ⚠️";
        String itemsList = String.join("<br>• ", lowStockItems);
        
        String htmlContent = String.format("""
            <html>
            <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
                <div style="max-width: 600px; margin: 0 auto; background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);">
                    <div style="text-align: center; margin-bottom: 30px;">
                        <h1 style="color: #FF6B35; margin: 0;">🐆 Hungry Cheetah</h1>
                    </div>
                    
                    <div style="background-color: #fff3cd; padding: 15px; border-radius: 8px; margin: 20px 0; border-left: 4px solid #ffc107;">
                        <h2 style="color: #856404; margin: 0;">⚠️ Low Inventory Alert</h2>
                    </div>
                    
                    <p style="color: #555; line-height: 1.6;">
                        Hi %s team,<br><br>
                        The following items are running low in stock and may need to be restocked soon:
                    </p>
                    
                    <div style="background-color: #f8f9fa; padding: 20px; border-radius: 8px; margin: 20px 0;">
                        <h3 style="margin-top: 0; color: #333;">Low Stock Items:</h3>
                        <div style="margin-left: 20px; color: #d32f2f;">
                            • %s
                        </div>
                    </div>
                    
                    <div style="text-align: center; margin: 30px 0;">
                        <a href="http://localhost:3000/restaurant/inventory" 
                           style="background-color: #ffc107; color: #212529; padding: 12px 30px; text-decoration: none; border-radius: 25px; font-weight: bold; display: inline-block;">
                            Manage Inventory
                        </a>
                    </div>
                    
                    <p style="color: #777; font-size: 12px; text-align: center;">
                        Consider updating your inventory or temporarily disabling these items to avoid customer disappointment.
                    </p>
                </div>
            </body>
            </html>
            """, restaurantName, itemsList);
        
        sendHtmlEmail(restaurantEmail, subject, htmlContent);
    }

    public void sendRestaurantReviewNotification(String restaurantEmail, String restaurantName, 
                                               String customerName, int rating, String reviewText) {
        String subject = String.format("New %d-Star Review Received! ⭐", rating);
        String ratingStars = "⭐".repeat(rating);
        
        String htmlContent = String.format("""
            <html>
            <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
                <div style="max-width: 600px; margin: 0 auto; background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);">
                    <div style="text-align: center; margin-bottom: 30px;">
                        <h1 style="color: #FF6B35; margin: 0;">🐆 Hungry Cheetah</h1>
                    </div>
                    
                    <h2 style="color: #333;">New Review for %s!</h2>
                    
                    <div style="background-color: #f8f9fa; padding: 20px; border-radius: 8px; margin: 20px 0; border-left: 4px solid #4CAF50;">
                        <div style="text-align: center; margin-bottom: 15px;">
                            <span style="font-size: 24px;">%s</span>
                            <p style="margin: 5px 0; color: #666;">%d out of 5 stars</p>
                        </div>
                        
                        <p style="margin: 0;"><strong>From:</strong> %s</p>
                        <div style="margin-top: 15px; padding: 15px; background-color: white; border-radius: 5px; font-style: italic;">
                            "%s"
                        </div>
                    </div>
                    
                    <div style="text-align: center; margin: 30px 0;">
                        <a href="http://localhost:3000/restaurant/reviews" 
                           style="background-color: #FF6B35; color: white; padding: 12px 30px; text-decoration: none; border-radius: 25px; font-weight: bold; display: inline-block;">
                            View All Reviews
                        </a>
                    </div>
                    
                    <p style="color: #777; font-size: 12px; text-align: center;">
                        Customer feedback helps improve your service. Consider responding to show you care!
                    </p>
                </div>
            </body>
            </html>
            """, restaurantName, ratingStars, rating, customerName, reviewText);
        
        sendHtmlEmail(restaurantEmail, subject, htmlContent);
    }

    public void sendStatusChangeNotification(String restaurantEmail, String restaurantName, 
                                           String oldStatus, String newStatus, String reason) {
        String subject = String.format("Restaurant Status Updated: %s", newStatus);
        String statusColor = newStatus.equalsIgnoreCase("OPEN") ? "#4CAF50" : 
                           newStatus.equalsIgnoreCase("CLOSED") ? "#F44336" : "#FF9800";
        
        String htmlContent = String.format("""
            <html>
            <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
                <div style="max-width: 600px; margin: 0 auto; background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);">
                    <div style="text-align: center; margin-bottom: 30px;">
                        <h1 style="color: #FF6B35; margin: 0;">🐆 Hungry Cheetah</h1>
                    </div>
                    
                    <h2 style="color: #333;">Status Update for %s</h2>
                    
                    <div style="background-color: %s; color: white; padding: 15px; border-radius: 8px; margin: 20px 0; text-align: center;">
                        <h3 style="margin: 0; font-size: 18px;">Status: %s</h3>
                        <p style="margin: 5px 0; opacity: 0.9;">Previous: %s</p>
                    </div>
                    
                    <div style="background-color: #f8f9fa; padding: 20px; border-radius: 8px; margin: 20px 0;">
                        <p style="margin: 0;"><strong>Reason:</strong> %s</p>
                    </div>
                    
                    <div style="text-align: center; margin: 30px 0;">
                        <a href="http://localhost:3000/restaurant/settings" 
                           style="background-color: #FF6B35; color: white; padding: 12px 30px; text-decoration: none; border-radius: 25px; font-weight: bold; display: inline-block;">
                            Restaurant Settings
                        </a>
                    </div>
                    
                    <p style="color: #777; font-size: 12px; text-align: center;">
                        If you have questions about this status change, please contact support.
                    </p>
                </div>
            </body>
            </html>
            """, restaurantName, statusColor, newStatus, oldStatus, reason);
        
        sendHtmlEmail(restaurantEmail, subject, htmlContent);
    }

    public void sendPromotionEmail(String restaurantEmail, String restaurantName, String promotionTitle, 
                                 String promotionDetails, String promoCode, String validUntil) {
        String subject = "New Promotion Opportunity! 🎉";
        
        String htmlContent = String.format("""
            <html>
            <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
                <div style="max-width: 600px; margin: 0 auto; background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);">
                    <div style="text-align: center; margin-bottom: 30px;">
                        <h1 style="color: #FF6B35; margin: 0;">🐆 Hungry Cheetah</h1>
                    </div>
                    
                    <h2 style="color: #333;">🎉 New Promotion for %s!</h2>
                    
                    <div style="background-color: #e3f2fd; padding: 20px; border-radius: 8px; margin: 20px 0; border-left: 4px solid #2196F3;">
                        <h3 style="margin-top: 0; color: #1976D2;">%s</h3>
                        <p style="color: #555; line-height: 1.6;">%s</p>
                        
                        <div style="background-color: white; padding: 15px; border-radius: 5px; margin: 15px 0; text-align: center;">
                            <strong style="color: #FF6B35; font-size: 18px;">Promo Code: %s</strong>
                        </div>
                        
                        <p style="margin: 0; color: #666;"><strong>Valid Until:</strong> %s</p>
                    </div>
                    
                    <div style="text-align: center; margin: 30px 0;">
                        <a href="http://localhost:3000/restaurant/promotions" 
                           style="background-color: #2196F3; color: white; padding: 12px 30px; text-decoration: none; border-radius: 25px; font-weight: bold; display: inline-block;">
                            Manage Promotions
                        </a>
                    </div>
                    
                    <p style="color: #777; font-size: 12px; text-align: center;">
                        Promotions help increase your visibility and attract more customers!
                    </p>
                </div>
            </body>
            </html>
            """, restaurantName, promotionTitle, promotionDetails, promoCode, validUntil);
        
        sendHtmlEmail(restaurantEmail, subject, htmlContent);
    }
}