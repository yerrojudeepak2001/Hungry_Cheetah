package com.foodapp.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    /**
     * Send payment confirmation email
     */
    public void sendPaymentConfirmationEmail(String toEmail, String customerName, 
                                           String orderId, BigDecimal amount, String transactionId) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Payment Confirmation - Order #" + orderId);

            String emailContent = buildPaymentConfirmationEmail(customerName, orderId, amount, transactionId);
            helper.setText(emailContent, true);

            mailSender.send(mimeMessage);
            log.info("Payment confirmation email sent to: {} for order: {}", toEmail, orderId);

        } catch (MessagingException e) {
            log.error("Failed to send payment confirmation email to: {} for order: {}", toEmail, orderId, e);
        }
    }

    /**
     * Send payment failure notification email
     */
    public void sendPaymentFailureEmail(String toEmail, String customerName, 
                                       String orderId, String failureReason) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Payment Failed - Order #" + orderId);

            String emailContent = buildPaymentFailureEmail(customerName, orderId, failureReason);
            helper.setText(emailContent, true);

            mailSender.send(mimeMessage);
            log.info("Payment failure email sent to: {} for order: {}", toEmail, orderId);

        } catch (MessagingException e) {
            log.error("Failed to send payment failure email to: {} for order: {}", toEmail, orderId, e);
        }
    }

    /**
     * Send refund confirmation email
     */
    public void sendRefundConfirmationEmail(String toEmail, String customerName, 
                                          String orderId, BigDecimal refundAmount, String refundId) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Refund Processed - Order #" + orderId);

            String emailContent = buildRefundConfirmationEmail(customerName, orderId, refundAmount, refundId);
            helper.setText(emailContent, true);

            mailSender.send(mimeMessage);
            log.info("Refund confirmation email sent to: {} for order: {}", toEmail, orderId);

        } catch (MessagingException e) {
            log.error("Failed to send refund confirmation email to: {} for order: {}", toEmail, orderId, e);
        }
    }

    /**
     * Send simple notification email
     */
    public void sendSimpleEmail(String toEmail, String subject, String message) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(fromEmail);
            mailMessage.setTo(toEmail);
            mailMessage.setSubject(subject);
            mailMessage.setText(message);

            mailSender.send(mailMessage);
            log.info("Simple email sent to: {} with subject: {}", toEmail, subject);

        } catch (Exception e) {
            log.error("Failed to send simple email to: {} with subject: {}", toEmail, subject, e);
        }
    }

    private String buildPaymentConfirmationEmail(String customerName, String orderId, 
                                               BigDecimal amount, String transactionId) {
        return String.format("""
                <html>
                <body style="font-family: Arial, sans-serif; margin: 0; padding: 20px; background-color: #f4f4f4;">
                    <div style="max-width: 600px; margin: 0 auto; background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);">
                        <div style="text-align: center; margin-bottom: 30px;">
                            <h1 style="color: #28a745; margin: 0;">Payment Confirmed!</h1>
                        </div>
                        
                        <div style="margin-bottom: 20px;">
                            <p>Dear <strong>%s</strong>,</p>
                            <p>We're pleased to confirm that your payment has been successfully processed.</p>
                        </div>
                        
                        <div style="background-color: #f8f9fa; padding: 20px; border-radius: 5px; margin: 20px 0;">
                            <h3 style="color: #333; margin-top: 0;">Payment Details:</h3>
                            <p><strong>Order ID:</strong> %s</p>
                            <p><strong>Amount:</strong> $%.2f</p>
                            <p><strong>Transaction ID:</strong> %s</p>
                            <p><strong>Payment Date:</strong> %s</p>
                        </div>
                        
                        <div style="margin: 20px 0;">
                            <p>Your order is now being prepared and you'll receive updates about its status.</p>
                            <p>Thank you for choosing our food delivery service!</p>
                        </div>
                        
                        <div style="margin-top: 30px; padding-top: 20px; border-top: 1px solid #eee; text-align: center; color: #666;">
                            <p>If you have any questions, please contact our support team.</p>
                            <p>© 2025 Food Delivery App. All rights reserved.</p>
                        </div>
                    </div>
                </body>
                </html>
                """, customerName, orderId, amount, transactionId, new java.util.Date());
    }

    private String buildPaymentFailureEmail(String customerName, String orderId, String failureReason) {
        return String.format("""
                <html>
                <body style="font-family: Arial, sans-serif; margin: 0; padding: 20px; background-color: #f4f4f4;">
                    <div style="max-width: 600px; margin: 0 auto; background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);">
                        <div style="text-align: center; margin-bottom: 30px;">
                            <h1 style="color: #dc3545; margin: 0;">Payment Failed</h1>
                        </div>
                        
                        <div style="margin-bottom: 20px;">
                            <p>Dear <strong>%s</strong>,</p>
                            <p>We're sorry to inform you that your payment could not be processed.</p>
                        </div>
                        
                        <div style="background-color: #f8f9fa; padding: 20px; border-radius: 5px; margin: 20px 0;">
                            <h3 style="color: #333; margin-top: 0;">Order Details:</h3>
                            <p><strong>Order ID:</strong> %s</p>
                            <p><strong>Failure Reason:</strong> %s</p>
                        </div>
                        
                        <div style="margin: 20px 0;">
                            <p>Please try again with a different payment method or contact your bank if the issue persists.</p>
                            <p>Your order has been temporarily held and can be completed once payment is successful.</p>
                        </div>
                        
                        <div style="margin-top: 30px; padding-top: 20px; border-top: 1px solid #eee; text-align: center; color: #666;">
                            <p>If you need assistance, please contact our support team.</p>
                            <p>© 2025 Food Delivery App. All rights reserved.</p>
                        </div>
                    </div>
                </body>
                </html>
                """, customerName, orderId, failureReason);
    }

    private String buildRefundConfirmationEmail(String customerName, String orderId, 
                                              BigDecimal refundAmount, String refundId) {
        return String.format("""
                <html>
                <body style="font-family: Arial, sans-serif; margin: 0; padding: 20px; background-color: #f4f4f4;">
                    <div style="max-width: 600px; margin: 0 auto; background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);">
                        <div style="text-align: center; margin-bottom: 30px;">
                            <h1 style="color: #17a2b8; margin: 0;">Refund Processed</h1>
                        </div>
                        
                        <div style="margin-bottom: 20px;">
                            <p>Dear <strong>%s</strong>,</p>
                            <p>Your refund has been successfully processed.</p>
                        </div>
                        
                        <div style="background-color: #f8f9fa; padding: 20px; border-radius: 5px; margin: 20px 0;">
                            <h3 style="color: #333; margin-top: 0;">Refund Details:</h3>
                            <p><strong>Order ID:</strong> %s</p>
                            <p><strong>Refund Amount:</strong> $%.2f</p>
                            <p><strong>Refund ID:</strong> %s</p>
                            <p><strong>Processing Date:</strong> %s</p>
                        </div>
                        
                        <div style="margin: 20px 0;">
                            <p>The refund will appear in your original payment method within 3-5 business days.</p>
                            <p>Thank you for your understanding.</p>
                        </div>
                        
                        <div style="margin-top: 30px; padding-top: 20px; border-top: 1px solid #eee; text-align: center; color: #666;">
                            <p>If you have any questions, please contact our support team.</p>
                            <p>© 2025 Food Delivery App. All rights reserved.</p>
                        </div>
                    </div>
                </body>
                </html>
                """, customerName, orderId, refundAmount, refundId, new java.util.Date());
    }
}