package com.foodapp.payment.controller;

import com.foodapp.payment.dto.ApiResponse;
import com.foodapp.payment.service.StripePaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/payment/stripe")
@RequiredArgsConstructor
@Slf4j
public class StripeController {

    private final StripePaymentService stripePaymentService;

    /**
     * Create a payment intent
     */
    @PostMapping("/payment-intent")
    public ResponseEntity<ApiResponse<PaymentIntent>> createPaymentIntent(
            @RequestBody Map<String, Object> paymentRequest) {
        try {
            BigDecimal amount = new BigDecimal(paymentRequest.get("amount").toString());
            String currency = paymentRequest.getOrDefault("currency", "usd").toString();
            String customerId = paymentRequest.get("customerId").toString();
            String description = paymentRequest.getOrDefault("description", "Food order payment").toString();

            PaymentIntent paymentIntent = stripePaymentService.createPaymentIntent(
                    amount, currency, customerId, description);

            return ResponseEntity.ok(new ApiResponse<>(true,
                    "Payment intent created successfully", paymentIntent));
        } catch (StripeException e) {
            log.error("Error creating payment intent: ", e);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Error creating payment intent: " + e.getMessage(), null));
        } catch (Exception e) {
            log.error("Unexpected error: ", e);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Unexpected error: " + e.getMessage(), null));
        }
    }

    /**
     * Confirm a payment intent
     */
    @PostMapping("/payment-intent/{paymentIntentId}/confirm")
    public ResponseEntity<ApiResponse<PaymentIntent>> confirmPaymentIntent(
            @PathVariable String paymentIntentId,
            @RequestBody Map<String, String> request) {
        try {
            String paymentMethodId = request.get("paymentMethodId");
            PaymentIntent paymentIntent = stripePaymentService.confirmPaymentIntent(
                    paymentIntentId, paymentMethodId);

            return ResponseEntity.ok(new ApiResponse<>(true,
                    "Payment confirmed successfully", paymentIntent));
        } catch (StripeException e) {
            log.error("Error confirming payment: ", e);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Error confirming payment: " + e.getMessage(), null));
        }
    }

    /**
     * Create a customer
     */
    @PostMapping("/customers")
    public ResponseEntity<ApiResponse<Customer>> createCustomer(
            @RequestBody Map<String, String> customerRequest) {
        try {
            String email = customerRequest.get("email");
            String name = customerRequest.get("name");
            String phone = customerRequest.get("phone");

            Customer customer = stripePaymentService.createCustomer(email, name, phone);

            return ResponseEntity.ok(new ApiResponse<>(true,
                    "Customer created successfully", customer));
        } catch (StripeException e) {
            log.error("Error creating customer: ", e);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Error creating customer: " + e.getMessage(), null));
        }
    }

    /**
     * Get customer details
     */
    @GetMapping("/customers/{customerId}")
    public ResponseEntity<ApiResponse<Customer>> getCustomer(@PathVariable String customerId) {
        try {
            Customer customer = stripePaymentService.retrieveCustomer(customerId);
            return ResponseEntity.ok(new ApiResponse<>(true,
                    "Customer retrieved successfully", customer));
        } catch (StripeException e) {
            log.error("Error retrieving customer: ", e);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Error retrieving customer: " + e.getMessage(), null));
        }
    }

    /**
     * Create a refund
     */
    @PostMapping("/refunds")
    public ResponseEntity<ApiResponse<Refund>> createRefund(
            @RequestBody Map<String, Object> refundRequest) {
        try {
            String paymentIntentId = refundRequest.get("paymentIntentId").toString();
            BigDecimal amount = refundRequest.containsKey("amount")
                    ? new BigDecimal(refundRequest.get("amount").toString())
                    : null;
            String reason = refundRequest.getOrDefault("reason", "requested_by_customer").toString();

            Refund refund = stripePaymentService.createRefund(paymentIntentId, amount, reason);

            return ResponseEntity.ok(new ApiResponse<>(true,
                    "Refund created successfully", refund));
        } catch (StripeException e) {
            log.error("Error creating refund: ", e);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Error creating refund: " + e.getMessage(), null));
        }
    }

    /**
     * Get payment intent status
     */
    @GetMapping("/payment-intent/{paymentIntentId}/status")
    public ResponseEntity<ApiResponse<String>> getPaymentStatus(@PathVariable String paymentIntentId) {
        try {
            String status = stripePaymentService.getPaymentStatus(paymentIntentId);
            return ResponseEntity.ok(new ApiResponse<>(true,
                    "Payment status retrieved successfully", status));
        } catch (StripeException e) {
            log.error("Error retrieving payment status: ", e);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Error retrieving payment status: " + e.getMessage(), null));
        }
    }

    /**
     * Cancel a payment intent
     */
    @PostMapping("/payment-intent/{paymentIntentId}/cancel")
    public ResponseEntity<ApiResponse<PaymentIntent>> cancelPaymentIntent(@PathVariable String paymentIntentId) {
        try {
            PaymentIntent paymentIntent = stripePaymentService.cancelPaymentIntent(paymentIntentId);
            return ResponseEntity.ok(new ApiResponse<>(true,
                    "Payment cancelled successfully", paymentIntent));
        } catch (StripeException e) {
            log.error("Error cancelling payment: ", e);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Error cancelling payment: " + e.getMessage(), null));
        }
    }

    /**
     * Create setup intent for saving payment methods
     */
    @PostMapping("/setup-intent")
    public ResponseEntity<ApiResponse<SetupIntent>> createSetupIntent(
            @RequestBody Map<String, String> request) {
        try {
            String customerId = request.get("customerId");
            SetupIntent setupIntent = stripePaymentService.createSetupIntent(customerId);

            return ResponseEntity.ok(new ApiResponse<>(true,
                    "Setup intent created successfully", setupIntent));
        } catch (StripeException e) {
            log.error("Error creating setup intent: ", e);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Error creating setup intent: " + e.getMessage(), null));
        }
    }

    /**
     * List customer payment methods
     */
    @GetMapping("/customers/{customerId}/payment-methods")
    public ResponseEntity<ApiResponse<PaymentMethodCollection>> listPaymentMethods(
            @PathVariable String customerId,
            @RequestParam(defaultValue = "card") String type) {
        try {
            PaymentMethodCollection paymentMethods = stripePaymentService.listPaymentMethods(customerId, type);
            return ResponseEntity.ok(new ApiResponse<>(true,
                    "Payment methods retrieved successfully", paymentMethods));
        } catch (StripeException e) {
            log.error("Error retrieving payment methods: ", e);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Error retrieving payment methods: " + e.getMessage(), null));
        }
    }
}