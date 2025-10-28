package com.foodapp.payment.service;

// import com.foodapp.common.constants.AppConstants; // Removed common dependency
import com.foodapp.payment.model.Payment;
import com.foodapp.payment.model.PaymentTransaction;
import com.foodapp.payment.repository.PaymentRepository;
import com.foodapp.payment.repository.PaymentTransactionRepository;
import com.foodapp.payment.dto.*;
import com.foodapp.payment.messaging.PaymentMessagePublisher;
import com.foodapp.payment.service.EmailService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    private final JmsTemplate jmsTemplate;
    private final PaymentRepository paymentRepository;
    private final PaymentTransactionRepository paymentTransactionRepository;
    private final StripePaymentService stripePaymentService;
    private final PaymentMessagePublisher messagePublisher;
    private final EmailService emailService;

    @Transactional
    public PaymentTransaction processPayment(PaymentRequest request) {
        // Validate payment details
        validatePaymentRequest(request);

        // Process payment with payment gateway
        PaymentGatewayResponse gatewayResponse = processWithPaymentGateway(request);

        // Create transaction record
        PaymentTransaction transaction = new PaymentTransaction();
        transaction.setOrderId(request.getOrderId());
        transaction.setAmount(request.getAmount());
        transaction.setPaymentMethod(request.getPaymentMethod());
        transaction.setStatus(gatewayResponse.getStatus());
        transaction.setTransactionId(gatewayResponse.getTransactionId());

        paymentTransactionRepository.save(transaction);

        // If payment successful, notify other services
        if (gatewayResponse.isSuccessful()) {
            // Send to original queue for backward compatibility
            jmsTemplate.convertAndSend("payment-success-queue", transaction);

            // Use new messaging system
            messagePublisher.publishPaymentSuccess(transaction);
            messagePublisher.notifyOrderService(transaction);
            messagePublisher.notifyDeliveryService(transaction);
        } else {
            messagePublisher.publishPaymentFailure(transaction);
        }

        return transaction;
    }

    @Transactional
    public PaymentTransaction processRefund(RefundRequest request) {
        // Validate refund request
        PaymentTransaction originalTransaction = paymentTransactionRepository.findById(request.getTransactionId())
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        // Process refund with payment gateway
        RefundResponse refundResponse = processRefundWithGateway(originalTransaction, request);

        // Create refund transaction
        PaymentTransaction refundTransaction = new PaymentTransaction();
        refundTransaction.setOrderId(originalTransaction.getOrderId());
        refundTransaction.setAmount(request.getAmount());
        refundTransaction.setType("REFUND");
        refundTransaction.setReferenceTransactionId(originalTransaction.getId());
        refundTransaction.setStatus(refundResponse.getStatus());

        paymentTransactionRepository.save(refundTransaction);

        return refundTransaction;
    }

    private void validatePaymentRequest(PaymentRequest request) {
        // Implement payment validation logic
    }

    private PaymentGatewayResponse processWithPaymentGateway(PaymentRequest request) {
        // Implement payment gateway integration
        return null;
    }

    private RefundResponse processRefundWithGateway(PaymentTransaction originalTransaction, RefundRequest request) {
        // Implement refund processing logic
        return null;
    }

    public String getPaymentStatus(String paymentId) {
        // Get payment status logic here
        return "COMPLETED";
    }

    public java.util.List<com.foodapp.payment.dto.PaymentResponse> processSplitPayment(java.util.List<Payment> payments,
            String groupOrderId) {
        // Process split payment logic here
        return payments.stream()
                .map(payment -> {
                    PaymentResponse response = new PaymentResponse();
                    response.setPaymentId("PAY_" + System.currentTimeMillis());
                    response.setStatus("SUCCESS");
                    response.setMessage("Split payment processed successfully");
                    return response;
                })
                .toList();
    }

    public com.foodapp.payment.model.PaymentMethod addPaymentMethod(Long userId,
            com.foodapp.payment.model.PaymentMethod paymentMethod) {
        // Add payment method logic here
        paymentMethod.setUserId(userId);
        return paymentMethod;
    }

    public java.util.List<com.foodapp.payment.model.PaymentMethod> getUserPaymentMethods(Long userId) {
        // Get user payment methods logic here
        return java.util.List.of();
    }

    public void removePaymentMethod(Long userId, String methodId) {
        // Remove payment method logic here
    }

    /**
     * Process payment using Stripe
     */
    @Transactional
    public PaymentTransaction processStripePayment(PaymentRequest request, String customerId) {
        try {
            // Create PaymentIntent with Stripe
            PaymentIntent paymentIntent = stripePaymentService.createPaymentIntent(
                    request.getAmount(),
                    request.getCurrency() != null ? request.getCurrency() : "usd",
                    customerId,
                    "Order payment for order ID: " + request.getOrderId());

            // Create transaction record
            PaymentTransaction transaction = new PaymentTransaction();
            transaction.setOrderId(request.getOrderId());
            transaction.setAmount(request.getAmount());
            transaction.setPaymentMethod("STRIPE_" + request.getPaymentMethod());
            transaction.setStatus("PENDING");
            transaction.setTransactionId(paymentIntent.getId());
            transaction.setGatewayTransactionId(paymentIntent.getId());

            paymentTransactionRepository.save(transaction);

            log.info("Created Stripe payment transaction: {} for order: {}",
                    transaction.getId(), request.getOrderId());

            return transaction;

        } catch (StripeException e) {
            log.error("Error processing Stripe payment for order: {}", request.getOrderId(), e);

            // Create failed transaction record
            PaymentTransaction failedTransaction = new PaymentTransaction();
            failedTransaction.setOrderId(request.getOrderId());
            failedTransaction.setAmount(request.getAmount());
            failedTransaction.setPaymentMethod("STRIPE_" + request.getPaymentMethod());
            failedTransaction.setStatus("FAILED");
            failedTransaction.setFailureReason(e.getMessage());

            paymentTransactionRepository.save(failedTransaction);

            throw new RuntimeException("Payment processing failed: " + e.getMessage(), e);
        }
    }

    /**
     * Handle successful payment confirmation from Stripe webhook
     */
    @Transactional
    public void handleStripePaymentSuccess(String paymentIntentId) {
        PaymentTransaction transaction = paymentTransactionRepository
                .findByGatewayTransactionId(paymentIntentId)
                .orElse(null);

        if (transaction != null) {
            transaction.setStatus("COMPLETED");
            paymentTransactionRepository.save(transaction);

            // Notify other services using new messaging system
            jmsTemplate.convertAndSend("payment-success-queue", transaction);
            messagePublisher.publishPaymentSuccess(transaction);
            messagePublisher.notifyOrderService(transaction);
            messagePublisher.notifyDeliveryService(transaction);

            log.info("Payment completed for transaction: {} with Stripe PaymentIntent: {}",
                    transaction.getId(), paymentIntentId);
        } else {
            log.warn("No transaction found for Stripe PaymentIntent: {}", paymentIntentId);
        }
    }

    /**
     * Handle failed payment from Stripe webhook
     */
    @Transactional
    public void handleStripePaymentFailure(String paymentIntentId, String failureReason) {
        PaymentTransaction transaction = paymentTransactionRepository
                .findByGatewayTransactionId(paymentIntentId)
                .orElse(null);

        if (transaction != null) {
            transaction.setStatus("FAILED");
            transaction.setFailureReason(failureReason);
            paymentTransactionRepository.save(transaction);

            // Notify other services about payment failure using new messaging system
            jmsTemplate.convertAndSend("payment.failure.queue", transaction);
            messagePublisher.notifyOrderService(transaction);

            log.error("Payment failed for transaction: {} with Stripe PaymentIntent: {}, reason: {}",
                    transaction.getId(), paymentIntentId, failureReason);
        } else {
            log.warn("No transaction found for failed Stripe PaymentIntent: {}", paymentIntentId);
        }
    }

    /**
     * Process refund using Stripe
     */
    @Transactional
    public PaymentTransaction processStripeRefund(RefundRequest request) {
        try {
            // Get original transaction
            PaymentTransaction originalTransaction = paymentTransactionRepository
                    .findById(request.getTransactionId())
                    .orElseThrow(() -> new RuntimeException("Original transaction not found"));

            // Create refund with Stripe
            com.stripe.model.Refund stripeRefund = stripePaymentService.createRefund(
                    originalTransaction.getGatewayTransactionId(),
                    request.getAmount(),
                    request.getReason());

            // Create refund transaction record
            PaymentTransaction refundTransaction = new PaymentTransaction();
            refundTransaction.setOrderId(originalTransaction.getOrderId());
            refundTransaction.setAmount(request.getAmount());
            refundTransaction.setType("REFUND");
            refundTransaction.setPaymentMethod(originalTransaction.getPaymentMethod());
            refundTransaction.setReferenceTransactionId(originalTransaction.getId());
            refundTransaction.setStatus("COMPLETED");
            refundTransaction.setGatewayTransactionId(stripeRefund.getId());

            paymentTransactionRepository.save(refundTransaction);

            // Notify other services about refund using new messaging system
            messagePublisher.publishRefundNotification(refundTransaction);
            messagePublisher.notifyOrderService(refundTransaction);

            log.info("Stripe refund processed: {} for original transaction: {}",
                    refundTransaction.getId(), originalTransaction.getId());

            return refundTransaction;

        } catch (StripeException e) {
            log.error("Error processing Stripe refund for transaction: {}", request.getTransactionId(), e);
            throw new RuntimeException("Refund processing failed: " + e.getMessage(), e);
        }
    }
}