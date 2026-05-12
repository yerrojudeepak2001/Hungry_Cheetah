package com.foodapp.user.service;

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
public class UserEmailService {

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

    public void sendWelcomeEmail(String userEmail, String userName) {
        String subject = "Welcome to Hungry Cheetah! 🎉";
        String htmlContent = String.format("""
            <html>
            <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
                <div style="max-width: 600px; margin: 0 auto; background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);">
                    <div style="text-align: center; margin-bottom: 30px;">
                        <h1 style="color: #FF6B35; margin: 0;">🐆 Hungry Cheetah</h1>
                        <p style="color: #666; margin: 5px 0;">Fast Food, Faster Delivery</p>
                    </div>
                    
                    <h2 style="color: #333;">Welcome, %s! 🍽️</h2>
                    <p style="color: #555; line-height: 1.6;">
                        Thank you for joining Hungry Cheetah! We're excited to have you as part of our food-loving community.
                    </p>
                    
                    <div style="background-color: #f8f9fa; padding: 20px; border-radius: 8px; margin: 20px 0;">
                        <h3 style="color: #FF6B35; margin-top: 0;">What's Next?</h3>
                        <ul style="color: #555; line-height: 1.8;">
                            <li>🔍 Browse our amazing restaurants</li>
                            <li>🛒 Add your favorite dishes to cart</li>
                            <li>⚡ Enjoy lightning-fast delivery</li>
                            <li>⭐ Rate and review your experience</li>
                        </ul>
                    </div>
                    
                    <div style="text-align: center; margin: 30px 0;">
                        <a href="http://localhost:3000/restaurants" 
                           style="background-color: #FF6B35; color: white; padding: 12px 30px; text-decoration: none; border-radius: 25px; font-weight: bold; display: inline-block;">
                            Start Ordering Now
                        </a>
                    </div>
                    
                    <p style="color: #777; font-size: 14px; text-align: center; margin-top: 40px;">
                        If you have any questions, feel free to contact our support team.<br>
                        Happy eating! 🎉
                    </p>
                </div>
            </body>
            </html>
            """, userName);
        
        sendHtmlEmail(userEmail, subject, htmlContent);
    }

    public void sendEmailVerificationEmail(String userEmail, String userName, String verificationToken) {
        String subject = "Verify Your Email Address 📧";
        String verificationUrl = "http://localhost:3000/verify-email?token=" + verificationToken;
        
        String htmlContent = String.format("""
            <html>
            <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
                <div style="max-width: 600px; margin: 0 auto; background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);">
                    <div style="text-align: center; margin-bottom: 30px;">
                        <h1 style="color: #FF6B35; margin: 0;">🐆 Hungry Cheetah</h1>
                    </div>
                    
                    <h2 style="color: #333;">Hi %s!</h2>
                    <p style="color: #555; line-height: 1.6;">
                        Please verify your email address to complete your registration and start ordering delicious food!
                    </p>
                    
                    <div style="text-align: center; margin: 30px 0;">
                        <a href="%s" 
                           style="background-color: #4CAF50; color: white; padding: 12px 30px; text-decoration: none; border-radius: 25px; font-weight: bold; display: inline-block;">
                            Verify Email Address
                        </a>
                    </div>
                    
                    <p style="color: #777; font-size: 14px;">
                        If the button doesn't work, copy and paste this link into your browser:<br>
                        <a href="%s" style="color: #FF6B35;">%s</a>
                    </p>
                    
                    <p style="color: #777; font-size: 12px; margin-top: 30px;">
                        This verification link will expire in 24 hours. If you didn't create an account, please ignore this email.
                    </p>
                </div>
            </body>
            </html>
            """, userName, verificationUrl, verificationUrl, verificationUrl);
        
        sendHtmlEmail(userEmail, subject, htmlContent);
    }

    public void sendPasswordResetEmail(String userEmail, String userName, String resetToken) {
        String subject = "Reset Your Password 🔒";
        String resetUrl = "http://localhost:3000/reset-password?token=" + resetToken;
        
        String htmlContent = String.format("""
            <html>
            <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
                <div style="max-width: 600px; margin: 0 auto; background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);">
                    <div style="text-align: center; margin-bottom: 30px;">
                        <h1 style="color: #FF6B35; margin: 0;">🐆 Hungry Cheetah</h1>
                    </div>
                    
                    <h2 style="color: #333;">Hi %s!</h2>
                    <p style="color: #555; line-height: 1.6;">
                        We received a request to reset your password. Click the button below to create a new password:
                    </p>
                    
                    <div style="text-align: center; margin: 30px 0;">
                        <a href="%s" 
                           style="background-color: #FF6B35; color: white; padding: 12px 30px; text-decoration: none; border-radius: 25px; font-weight: bold; display: inline-block;">
                            Reset Password
                        </a>
                    </div>
                    
                    <p style="color: #777; font-size: 14px;">
                        If the button doesn't work, copy and paste this link into your browser:<br>
                        <a href="%s" style="color: #FF6B35;">%s</a>
                    </p>
                    
                    <p style="color: #777; font-size: 12px; margin-top: 30px;">
                        This reset link will expire in 1 hour. If you didn't request a password reset, please ignore this email.
                    </p>
                </div>
            </body>
            </html>
            """, userName, resetUrl, resetUrl, resetUrl);
        
        sendHtmlEmail(userEmail, subject, htmlContent);
    }

    public void sendProfileUpdateNotification(String userEmail, String userName, String updateType) {
        String subject = "Profile Updated Successfully ✅";
        String htmlContent = String.format("""
            <html>
            <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
                <div style="max-width: 600px; margin: 0 auto; background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);">
                    <div style="text-align: center; margin-bottom: 30px;">
                        <h1 style="color: #FF6B35; margin: 0;">🐆 Hungry Cheetah</h1>
                    </div>
                    
                    <h2 style="color: #333;">Hi %s!</h2>
                    <p style="color: #555; line-height: 1.6;">
                        Your profile has been successfully updated. The following changes were made:
                    </p>
                    
                    <div style="background-color: #e8f5e8; padding: 15px; border-radius: 8px; margin: 20px 0; border-left: 4px solid #4CAF50;">
                        <p style="color: #2e7d32; margin: 0; font-weight: bold;">✅ %s</p>
                    </div>
                    
                    <p style="color: #555;">
                        If you didn't make these changes, please contact our support team immediately.
                    </p>
                    
                    <div style="text-align: center; margin: 30px 0;">
                        <a href="http://localhost:3000/profile" 
                           style="background-color: #FF6B35; color: white; padding: 12px 30px; text-decoration: none; border-radius: 25px; font-weight: bold; display: inline-block;">
                            View Profile
                        </a>
                    </div>
                </div>
            </body>
            </html>
            """, userName, updateType);
        
        sendHtmlEmail(userEmail, subject, htmlContent);
    }

    public void sendAccountStatusEmail(String userEmail, String userName, String status, String reason) {
        String subject = String.format("Account Status Update: %s", status);
        String statusColor = status.equalsIgnoreCase("ACTIVE") ? "#4CAF50" : 
                           status.equalsIgnoreCase("SUSPENDED") ? "#FF9800" : "#F44336";
        
        String htmlContent = String.format("""
            <html>
            <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
                <div style="max-width: 600px; margin: 0 auto; background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);">
                    <div style="text-align: center; margin-bottom: 30px;">
                        <h1 style="color: #FF6B35; margin: 0;">🐆 Hungry Cheetah</h1>
                    </div>
                    
                    <h2 style="color: #333;">Hi %s!</h2>
                    <p style="color: #555; line-height: 1.6;">
                        Your account status has been updated:
                    </p>
                    
                    <div style="background-color: %s; color: white; padding: 15px; border-radius: 8px; margin: 20px 0; text-align: center;">
                        <h3 style="margin: 0; font-size: 18px;">Status: %s</h3>
                    </div>
                    
                    <p style="color: #555; line-height: 1.6;">
                        <strong>Reason:</strong> %s
                    </p>
                    
                    <p style="color: #555;">
                        If you have any questions about this status change, please contact our support team.
                    </p>
                    
                    <div style="text-align: center; margin: 30px 0;">
                        <a href="http://localhost:3000/support" 
                           style="background-color: #FF6B35; color: white; padding: 12px 30px; text-decoration: none; border-radius: 25px; font-weight: bold; display: inline-block;">
                            Contact Support
                        </a>
                    </div>
                </div>
            </body>
            </html>
            """, userName, statusColor, status, reason);
        
        sendHtmlEmail(userEmail, subject, htmlContent);
    }
}