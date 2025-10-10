package com.foodapp.user.service;

public interface EmailService {
    void sendVerificationEmail(String to, String verificationCode);
    void sendPasswordResetEmail(String to, String resetToken);
    void sendWelcomeEmail(String to, String name);
    void sendOrderConfirmationEmail(String to, String orderDetails);
}