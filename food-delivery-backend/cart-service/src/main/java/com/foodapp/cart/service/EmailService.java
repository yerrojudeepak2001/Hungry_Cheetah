package com.foodapp.cart.service;

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
public class EmailService {

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

    public void sendCartAbandonmentEmail(String userEmail, String userName, String cartId) {
        String subject = "Don't forget your delicious order! 🍽️";
        String htmlContent = String.format("""
            <html>
            <body>
                <h2>Hi %s!</h2>
                <p>We noticed you left some delicious items in your cart. Don't let them get away!</p>
                <p>Your cart contains some amazing dishes that are waiting for you.</p>
                <p><strong>Cart ID:</strong> %s</p>
                <p>Complete your order now and enjoy your meal!</p>
                <p><a href="http://localhost:3000/cart" style="background-color: #4CAF50; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;">Complete Order</a></p>
                <br>
                <p>Best regards,<br>Hungry Cheetah Team</p>
            </body>
            </html>
            """, userName, cartId);
        
        sendHtmlEmail(userEmail, subject, htmlContent);
    }

    public void sendOrderConfirmationEmail(String userEmail, String userName, String orderId, double totalAmount) {
        String subject = "Order Confirmed! 🎉";
        String htmlContent = String.format("""
            <html>
            <body>
                <h2>Hi %s!</h2>
                <p>Thank you for your order! We're preparing your delicious meal.</p>
                <p><strong>Order ID:</strong> %s</p>
                <p><strong>Total Amount:</strong> $%.2f</p>
                <p>We'll notify you once your order is ready for delivery.</p>
                <p>Track your order: <a href="http://localhost:3000/orders/%s">View Order Status</a></p>
                <br>
                <p>Best regards,<br>Hungry Cheetah Team</p>
            </body>
            </html>
            """, userName, orderId, totalAmount, orderId);
        
        sendHtmlEmail(userEmail, subject, htmlContent);
    }

    public void sendCartReminderEmail(String userEmail, String userName, int itemCount) {
        String subject = "Your cart is waiting! 🛒";
        String htmlContent = String.format("""
            <html>
            <body>
                <h2>Hi %s!</h2>
                <p>You have %d delicious item(s) waiting in your cart!</p>
                <p>Don't miss out on your favorite dishes. Complete your order now!</p>
                <p><a href="http://localhost:3000/cart" style="background-color: #FF6B35; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;">View Cart</a></p>
                <br>
                <p>Best regards,<br>Hungry Cheetah Team</p>
            </body>
            </html>
            """, userName, itemCount);
        
        sendHtmlEmail(userEmail, subject, htmlContent);
    }
}