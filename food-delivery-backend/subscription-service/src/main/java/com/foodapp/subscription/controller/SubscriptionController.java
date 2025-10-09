package com.foodapp.subscription.controller;

import com.foodapp.common.dto.ApiResponse;
import com.foodapp.subscription.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;
    private final PlanService planService;
    private final BillingService billingService;

    public SubscriptionController(SubscriptionService subscriptionService,
                                PlanService planService,
                                BillingService billingService) {
        this.subscriptionService = subscriptionService;
        this.planService = planService;
        this.billingService = billingService;
    }

    // Subscription Plans
    @PostMapping("/plans")
    public ResponseEntity<ApiResponse<?>> createPlan(@RequestBody SubscriptionPlan plan) {
        var created = planService.createPlan(plan);
        return ResponseEntity.ok(new ApiResponse<>(true, "Subscription plan created successfully", created));
    }

    @GetMapping("/plans")
    public ResponseEntity<ApiResponse<?>> getAllPlans() {
        var plans = planService.getAllPlans();
        return ResponseEntity.ok(new ApiResponse<>(true, "Subscription plans fetched successfully", plans));
    }

    @GetMapping("/plans/{planId}")
    public ResponseEntity<ApiResponse<?>> getPlan(@PathVariable String planId) {
        var plan = planService.getPlan(planId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Subscription plan fetched successfully", plan));
    }

    // Subscriptions
    @PostMapping("/subscribe")
    public ResponseEntity<ApiResponse<?>> subscribe(@RequestBody SubscriptionRequest request) {
        var subscription = subscriptionService.subscribe(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Subscribed successfully", subscription));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<?>> getUserSubscriptions(@PathVariable Long userId) {
        var subscriptions = subscriptionService.getUserSubscriptions(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "User subscriptions fetched successfully", subscriptions));
    }

    @PutMapping("/{subscriptionId}/cancel")
    public ResponseEntity<ApiResponse<?>> cancelSubscription(@PathVariable String subscriptionId) {
        var cancelled = subscriptionService.cancelSubscription(subscriptionId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Subscription cancelled successfully", cancelled));
    }

    // Billing
    @GetMapping("/billing/{subscriptionId}")
    public ResponseEntity<ApiResponse<?>> getBillingDetails(@PathVariable String subscriptionId) {
        var billing = billingService.getBillingDetails(subscriptionId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Billing details fetched successfully", billing));
    }

    @PostMapping("/billing/update")
    public ResponseEntity<ApiResponse<?>> updateBillingInfo(@RequestBody BillingInfo billingInfo) {
        var updated = billingService.updateBillingInfo(billingInfo);
        return ResponseEntity.ok(new ApiResponse<>(true, "Billing information updated successfully", updated));
    }

    // Plan Management
    @PutMapping("/plans/{planId}")
    public ResponseEntity<ApiResponse<?>> updatePlan(
            @PathVariable String planId,
            @RequestBody SubscriptionPlan plan) {
        var updated = planService.updatePlan(planId, plan);
        return ResponseEntity.ok(new ApiResponse<>(true, "Subscription plan updated successfully", updated));
    }

    @DeleteMapping("/plans/{planId}")
    public ResponseEntity<ApiResponse<?>> deletePlan(@PathVariable String planId) {
        planService.deletePlan(planId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Subscription plan deleted successfully", null));
    }

    // Features
    @GetMapping("/features/{planId}")
    public ResponseEntity<ApiResponse<?>> getPlanFeatures(@PathVariable String planId) {
        var features = planService.getPlanFeatures(planId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Plan features fetched successfully", features));
    }

    // Usage Tracking
    @GetMapping("/usage/{subscriptionId}")
    public ResponseEntity<ApiResponse<?>> getSubscriptionUsage(@PathVariable String subscriptionId) {
        var usage = subscriptionService.getSubscriptionUsage(subscriptionId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Subscription usage fetched successfully", usage));
    }

    // Renewals
    @PostMapping("/{subscriptionId}/renew")
    public ResponseEntity<ApiResponse<?>> renewSubscription(@PathVariable String subscriptionId) {
        var renewed = subscriptionService.renewSubscription(subscriptionId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Subscription renewed successfully", renewed));
    }

    // Plan Changes
    @PostMapping("/{subscriptionId}/change-plan")
    public ResponseEntity<ApiResponse<?>> changePlan(
            @PathVariable String subscriptionId,
            @RequestBody PlanChangeRequest request) {
        var changed = subscriptionService.changePlan(subscriptionId, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Plan changed successfully", changed));
    }

    // Analytics
    @GetMapping("/analytics")
    public ResponseEntity<ApiResponse<?>> getSubscriptionAnalytics(
            @RequestParam String timeFrame,
            @RequestParam(required = false) String planId) {
        var analytics = subscriptionService.getAnalytics(timeFrame, planId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Subscription analytics fetched successfully", analytics));
    }
}