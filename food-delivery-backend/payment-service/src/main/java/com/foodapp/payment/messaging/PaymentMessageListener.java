package com.foodapp.payment.messaging;

import com.foodapp.payment.model.PaymentTransaction;
import com.foodapp.payment.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentMessageListener {

    private final EmailService emailService;

    /**
     * Handle payment success events
     */
    @JmsListener(destination = "payment.success.queue")
    public void handlePaymentSuccess(PaymentTransaction transaction) {
        log.info("Received payment success event for transaction: {}", transaction.getId());
        
        try {
            // Send payment confirmation email
            // Note: In a real application, you would get customer email from user service
            String customerEmail = "customer@example.com"; // This should be fetched from user service
            String customerName = "Valued Customer"; // This should be fetched from user service
            
            emailService.sendPaymentConfirmationEmail(
                customerEmail,
                customerName,
                transaction.getOrderId().toString(),
                transaction.getAmount(),
                transaction.getTransactionId()
            );
            
            log.info("Payment confirmation email sent for transaction: {}", transaction.getId());
            
        } catch (Exception e) {
            log.error("Error processing payment success event for transaction: {}", transaction.getId(), e);
        }
    }

    /**
     * Handle payment failure events
     */
    @JmsListener(destination = "payment.failure.queue")
    public void handlePaymentFailure(PaymentTransaction transaction) {
        log.info("Received payment failure event for transaction: {}", transaction.getId());
        
        try {
            // Send payment failure email
            // Note: In a real application, you would get customer email from user service
            String customerEmail = "customer@example.com"; // This should be fetched from user service
            String customerName = "Valued Customer"; // This should be fetched from user service
            
            emailService.sendPaymentFailureEmail(
                customerEmail,
                customerName,
                transaction.getOrderId().toString(),
                transaction.getFailureReason()
            );
            
            log.info("Payment failure email sent for transaction: {}", transaction.getId());
            
        } catch (Exception e) {
            log.error("Error processing payment failure event for transaction: {}", transaction.getId(), e);
        }
    }

    /**
     * Handle general payment notifications
     */
    @JmsListener(destination = "payment.notification.queue")
    public void handlePaymentNotification(String message) {
        log.info("Received payment notification: {}", message);
        
        try {
            // Process general payment notifications
            // This could include alerts, admin notifications, etc.
            
            // Example: Send admin notification
            emailService.sendSimpleEmail(
                "admin@foodapp.com",
                "Payment System Notification",
                message
            );
            
        } catch (Exception e) {
            log.error("Error processing payment notification: {}", message, e);
        }
    }

    /**
     * Handle refund events
     */
    @JmsListener(destination = "payment.refund.queue")
    public void handleRefundSuccess(PaymentTransaction refundTransaction) {
        log.info("Received refund event for transaction: {}", refundTransaction.getId());
        
        try {
            // Send refund confirmation email
            String customerEmail = "customer@example.com"; // This should be fetched from user service
            String customerName = "Valued Customer"; // This should be fetched from user service
            
            emailService.sendRefundConfirmationEmail(
                customerEmail, 
                customerName, 
                refundTransaction.getOrderId().toString(),
                refundTransaction.getAmount(), 
                refundTransaction.getId().toString()
            );
            
            log.info("Refund confirmation email sent for transaction: {}", refundTransaction.getId());
        } catch (Exception e) {
            log.error("Failed to send refund confirmation email for transaction: {}", refundTransaction.getId(), e);
        }
    }
}