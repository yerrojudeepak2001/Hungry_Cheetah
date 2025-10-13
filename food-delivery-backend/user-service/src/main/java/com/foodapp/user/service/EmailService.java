package com.foodapp.user.service;

public interface EmailService {
    void sendVerificationEmail(String to, String verificationCode);
    void sendPasswordResetEmail(String to, String resetToken);
    void sendWelcomeEmail(String to, String name);
    void sendOrderConfirmationEmail(String to, String orderDetails);
    void sendPasswordChangeNotification(String to);
}
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
    public void sendVerificationEmail(String email, String verificationCode) {
        // TODO: Implement verification email sending logic
        System.out.println("Sending verification email to: " + email + " with code: " + verificationCode);
    }
    
    public void sendPasswordResetEmail(String email, String resetToken) {
        // TODO: Implement password reset email
        System.out.println("Sending password reset email to: " + email + " with token: " + resetToken);
    }
    
    public void sendWelcomeEmail(String email, String name) {
        // TODO: Implement welcome email sending logic
        System.out.println("Sending welcome email to: " + email + " for user: " + name);
    }
    
    public void sendOrderConfirmationEmail(String email, String orderDetails) {
        // TODO: Implement order confirmation email
        System.out.println("Sending order confirmation email to: " + email + " with details: " + orderDetails);
    }
    
    public void sendPasswordChangeNotification(String email) {
        // TODO: Implement password change notification
        System.out.println("Sending password change notification to: " + email);
    }
}
