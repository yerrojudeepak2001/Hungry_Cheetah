package com.foodapp.payment.service;

import com.foodapp.common.constants.AppConstants;
import com.foodapp.payment.model.PaymentTransaction;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {
    private final JmsTemplate jmsTemplate;
    private final PaymentRepository paymentRepository;

    public PaymentService(JmsTemplate jmsTemplate, PaymentRepository paymentRepository) {
        this.jmsTemplate = jmsTemplate;
        this.paymentRepository = paymentRepository;
    }

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

        paymentRepository.save(transaction);

        // If payment successful, notify other services
        if (gatewayResponse.isSuccessful()) {
            jmsTemplate.convertAndSend(AppConstants.PAYMENT_SUCCESS_QUEUE, transaction);
        }

        return transaction;
    }

    @Transactional
    public PaymentTransaction processRefund(RefundRequest request) {
        // Validate refund request
        PaymentTransaction originalTransaction = paymentRepository.findById(request.getTransactionId())
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

        paymentRepository.save(refundTransaction);

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
}