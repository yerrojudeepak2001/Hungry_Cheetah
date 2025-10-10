package com.foodapp.user.service;

<<<<<<< HEAD
public interface EmailService {
    void sendVerificationEmail(String to, String verificationCode);
    void sendPasswordResetEmail(String to, String resetToken);
    void sendWelcomeEmail(String to, String name);
    void sendOrderConfirmationEmail(String to, String orderDetails);
=======
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
    public void sendWelcomeEmail(String email, String name) {
        // TODO: Implement email sending logic
        System.out.println("Sending welcome email to: " + email);
    }
    
    public void sendPasswordResetEmail(String email, String resetToken) {
        // TODO: Implement password reset email
        System.out.println("Sending password reset email to: " + email);
    }
    
    public void sendPasswordChangeNotification(String email) {
        // TODO: Implement password change notification
        System.out.println("Sending password change notification to: " + email);
    }
    
    public void sendVerificationEmail(String email, String token) {
        // TODO: Implement verification email
        System.out.println("Sending verification email to: " + email + " with token: " + token);
    }
>>>>>>> version1.4
}