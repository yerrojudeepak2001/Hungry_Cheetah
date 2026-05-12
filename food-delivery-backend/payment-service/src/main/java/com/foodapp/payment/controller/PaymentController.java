package com.foodapp.payment.controller;

import com.foodapp.payment.dto.ApiResponse;
import com.foodapp.payment.model.Payment;
import com.foodapp.payment.model.PaymentMethod;
import com.foodapp.payment.model.Refund;
import com.foodapp.payment.model.Subscription;
import com.foodapp.payment.service.PaymentService;
import com.foodapp.payment.service.RefundService;
import com.foodapp.payment.service.SubscriptionService;
import com.foodapp.payment.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;
    private final RefundService refundService;
    private final SubscriptionService subscriptionService;
    private final JwtUtil jwtUtil;

    public PaymentController(PaymentService paymentService,
            RefundService refundService,
            SubscriptionService subscriptionService,
            JwtUtil jwtUtil) {
        this.paymentService = paymentService;
        this.refundService = refundService;
        this.subscriptionService = subscriptionService;
        this.jwtUtil = jwtUtil;
    }

    // Payment Processing
    @PostMapping("/process")
    public ResponseEntity<ApiResponse<?>> processPayment(
            @RequestBody com.foodapp.payment.dto.PaymentRequest paymentRequest) {
        var processedPayment = paymentService.processPayment(paymentRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Payment processed successfully", processedPayment));
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<ApiResponse<?>> getPaymentStatus(@PathVariable String paymentId) {
        var status = paymentService.getPaymentStatus(paymentId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Payment status fetched successfully", status));
    }

    // Split Payments
    @PostMapping("/split")
    public ResponseEntity<ApiResponse<?>> processSplitPayment(
            @RequestBody List<Payment> payments,
            @RequestParam String groupOrderId) {
        var processedPayments = paymentService.processSplitPayment(payments, groupOrderId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Split payments processed successfully", processedPayments));
    }

    // Refunds
    @PostMapping("/refunds")
    public ResponseEntity<ApiResponse<?>> processRefund(
            @RequestBody com.foodapp.payment.dto.RefundRequest refundRequest) {
        var processedRefund = refundService.processRefund(refundRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Refund processed successfully", processedRefund));
    }

    @GetMapping("/refunds/{refundId}")
    public ResponseEntity<ApiResponse<?>> getRefundStatus(@PathVariable String refundId) {
        var status = refundService.getRefundStatus(refundId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Refund status fetched successfully", status));
    }

    // Subscriptions
    @PostMapping("/subscriptions")
    public ResponseEntity<ApiResponse<?>> createSubscription(@RequestBody Subscription subscription) {
        var createdSubscription = subscriptionService.createSubscription(subscription);
        return ResponseEntity.ok(new ApiResponse<>(true, "Subscription created successfully", createdSubscription));
    }

    @GetMapping("/subscriptions")
    public ResponseEntity<ApiResponse<?>> getUserSubscriptions(@RequestHeader("X-User-Id") String userId) {
        var subscriptions = subscriptionService.getUserSubscriptions(Long.parseLong(userId));
        return ResponseEntity.ok(new ApiResponse<>(true, "User subscriptions fetched successfully", subscriptions));
    }

    @PutMapping("/subscriptions/{subscriptionId}")
    public ResponseEntity<ApiResponse<?>> updateSubscription(
            @PathVariable String subscriptionId,
            @RequestBody Subscription subscription) {
        var updatedSubscription = subscriptionService.updateSubscription(subscriptionId, subscription);
        return ResponseEntity.ok(new ApiResponse<>(true, "Subscription updated successfully", updatedSubscription));
    }

    @DeleteMapping("/subscriptions/{subscriptionId}")
    public ResponseEntity<ApiResponse<?>> cancelSubscription(@PathVariable String subscriptionId) {
        subscriptionService.cancelSubscription(Long.parseLong(subscriptionId));
        return ResponseEntity.ok(new ApiResponse<>(true, "Subscription cancelled successfully", null));
    }

    // Payment Methods
    @PostMapping("/methods")
    public ResponseEntity<ApiResponse<?>> addPaymentMethod(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody PaymentMethod paymentMethod) {
        var added = paymentService.addPaymentMethod(Long.parseLong(userId), paymentMethod);
        return ResponseEntity.ok(new ApiResponse<>(true, "Payment method added successfully", added));
    }

    @GetMapping("/methods")
    public ResponseEntity<ApiResponse<?>> getUserPaymentMethods(@RequestHeader("X-User-Id") String userId) {
        var methods = paymentService.getUserPaymentMethods(Long.parseLong(userId));
        return ResponseEntity.ok(new ApiResponse<>(true, "Payment methods fetched successfully", methods));
    }

    @DeleteMapping("/methods/{methodId}")
    public ResponseEntity<ApiResponse<?>> removePaymentMethod(
            @PathVariable String methodId,
            @RequestHeader("X-User-Id") String userId) {
        paymentService.removePaymentMethod(Long.parseLong(userId), methodId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Payment method removed successfully", null));
    }
}