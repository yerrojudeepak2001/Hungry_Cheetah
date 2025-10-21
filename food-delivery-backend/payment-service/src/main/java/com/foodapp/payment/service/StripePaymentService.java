package com.foodapp.payment.service;

import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.param.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class StripePaymentService {

    /**
     * Create a payment intent for processing payment
     */
    public PaymentIntent createPaymentIntent(BigDecimal amount, String currency, String customerId, String description) throws StripeException {
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amount.multiply(BigDecimal.valueOf(100)).longValue()) // Stripe expects amount in cents
                .setCurrency(currency.toLowerCase())
                .setCustomer(customerId)
                .setDescription(description)
                .setConfirmationMethod(PaymentIntentCreateParams.ConfirmationMethod.MANUAL)
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);
        log.info("Created PaymentIntent: {}", paymentIntent.getId());
        return paymentIntent;
    }

    /**
     * Confirm a payment intent
     */
    public PaymentIntent confirmPaymentIntent(String paymentIntentId, String paymentMethodId) throws StripeException {
        PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
        
        PaymentIntentConfirmParams params = PaymentIntentConfirmParams.builder()
                .setPaymentMethod(paymentMethodId)
                .build();

        PaymentIntent confirmedPayment = paymentIntent.confirm(params);
        log.info("Confirmed PaymentIntent: {}", confirmedPayment.getId());
        return confirmedPayment;
    }

    /**
     * Create a customer in Stripe
     */
    public Customer createCustomer(String email, String name, String phone) throws StripeException {
        CustomerCreateParams params = CustomerCreateParams.builder()
                .setEmail(email)
                .setName(name)
                .setPhone(phone)
                .build();

        Customer customer = Customer.create(params);
        log.info("Created Stripe customer: {}", customer.getId());
        return customer;
    }

    /**
     * Retrieve a customer from Stripe
     */
    public Customer retrieveCustomer(String customerId) throws StripeException {
        return Customer.retrieve(customerId);
    }

    /**
     * Create a refund for a payment
     */
    public Refund createRefund(String paymentIntentId, BigDecimal amount, String reason) throws StripeException {
        RefundCreateParams.Builder paramsBuilder = RefundCreateParams.builder()
                .setPaymentIntent(paymentIntentId);

        if (amount != null) {
            paramsBuilder.setAmount(amount.multiply(BigDecimal.valueOf(100)).longValue());
        }

        if (reason != null) {
            paramsBuilder.setReason(RefundCreateParams.Reason.valueOf(reason.toUpperCase()));
        }

        Refund refund = Refund.create(paramsBuilder.build());
        log.info("Created refund: {}", refund.getId());
        return refund;
    }

    /**
     * List all payment methods for a customer
     */
    public PaymentMethodCollection listPaymentMethods(String customerId, String type) throws StripeException {
        PaymentMethodListParams params = PaymentMethodListParams.builder()
                .setCustomer(customerId)
                .setType(PaymentMethodListParams.Type.valueOf(type.toUpperCase()))
                .build();

        return PaymentMethod.list(params);
    }

    /**
     * Retrieve payment intent by ID
     */
    public PaymentIntent retrievePaymentIntent(String paymentIntentId) throws StripeException {
        return PaymentIntent.retrieve(paymentIntentId);
    }

    /**
     * Cancel a payment intent
     */
    public PaymentIntent cancelPaymentIntent(String paymentIntentId) throws StripeException {
        PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
        PaymentIntent cancelledPayment = paymentIntent.cancel();
        log.info("Cancelled PaymentIntent: {}", cancelledPayment.getId());
        return cancelledPayment;
    }

    /**
     * Create a setup intent for saving payment methods
     */
    public SetupIntent createSetupIntent(String customerId) throws StripeException {
        SetupIntentCreateParams params = SetupIntentCreateParams.builder()
                .setCustomer(customerId)
                .build();

        SetupIntent setupIntent = SetupIntent.create(params);
        log.info("Created SetupIntent: {}", setupIntent.getId());
        return setupIntent;
    }

    /**
     * Get payment intent status
     */
    public String getPaymentStatus(String paymentIntentId) throws StripeException {
        PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
        return paymentIntent.getStatus();
    }
}