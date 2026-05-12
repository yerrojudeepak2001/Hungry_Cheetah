package com.foodapp.payment.messaging;

import com.foodapp.payment.model.PaymentTransaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentMessagePublisher {

    private final JmsTemplate jmsTemplate;

    /**
     * Publish payment success event
     */
    public void publishPaymentSuccess(PaymentTransaction transaction) {
        try {
            jmsTemplate.convertAndSend("payment.success.queue", transaction);
            log.info("Published payment success event for transaction: {}", transaction.getId());
        } catch (Exception e) {
            log.error("Failed to publish payment success event for transaction: {}", transaction.getId(), e);
        }
    }

    /**
     * Publish payment failure event
     */
    public void publishPaymentFailure(PaymentTransaction transaction) {
        try {
            jmsTemplate.convertAndSend("payment.failure.queue", transaction);
            log.info("Published payment failure event for transaction: {}", transaction.getId());
        } catch (Exception e) {
            log.error("Failed to publish payment failure event for transaction: {}", transaction.getId(), e);
        }
    }

    /**
     * Publish general payment notification
     */
    public void publishPaymentNotification(String message) {
        try {
            jmsTemplate.convertAndSend("payment.notification.queue", message);
            log.info("Published payment notification: {}", message);
        } catch (Exception e) {
            log.error("Failed to publish payment notification: {}", message, e);
        }
    }

    /**
     * Publish refund notification
     */
    public void publishRefundNotification(PaymentTransaction refundTransaction) {
        try {
            jmsTemplate.convertAndSend("payment.refund.queue", refundTransaction);
            log.info("Published refund notification for transaction: {}", refundTransaction.getId());
        } catch (Exception e) {
            log.error("Failed to publish refund notification for transaction: {}", refundTransaction.getId(), e);
        }
    }

    /**
     * Publish to order service that payment was successful
     */
    public void notifyOrderService(PaymentTransaction transaction) {
        try {
            jmsTemplate.convertAndSend("order.payment.success.queue", transaction);
            log.info("Notified order service of payment success for transaction: {}", transaction.getId());
        } catch (Exception e) {
            log.error("Failed to notify order service for transaction: {}", transaction.getId(), e);
        }
    }

    /**
     * Publish to delivery service that payment was successful
     */
    public void notifyDeliveryService(PaymentTransaction transaction) {
        try {
            jmsTemplate.convertAndSend("delivery.payment.ready.queue", transaction);
            log.info("Notified delivery service of payment success for transaction: {}", transaction.getId());
        } catch (Exception e) {
            log.error("Failed to notify delivery service for transaction: {}", transaction.getId(), e);
        }
    }
}