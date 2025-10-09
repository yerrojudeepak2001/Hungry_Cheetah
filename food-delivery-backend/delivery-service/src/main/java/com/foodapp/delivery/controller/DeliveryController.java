package com.foodapp.delivery.controller;

import com.foodapp.common.dto.ApiResponse;
import com.foodapp.delivery.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {
    private final DeliveryService deliveryService;
    private final DeliveryPartnerService partnerService;
    private final RouteOptimizationService routeService;

    public DeliveryController(DeliveryService deliveryService,
                            DeliveryPartnerService partnerService,
                            RouteOptimizationService routeService) {
        this.deliveryService = deliveryService;
        this.partnerService = partnerService;
        this.routeService = routeService;
    }

    // Delivery Management
    @PostMapping("/assignments")
    public ResponseEntity<ApiResponse<?>> assignDelivery(@RequestBody DeliveryAssignment assignment) {
        var assigned = deliveryService.assignDelivery(assignment);
        return ResponseEntity.ok(new ApiResponse<>(true, "Delivery assigned successfully", assigned));
    }

    @GetMapping("/tracking/{deliveryId}")
    public ResponseEntity<ApiResponse<?>> trackDelivery(@PathVariable String deliveryId) {
        var tracking = deliveryService.getDeliveryTracking(deliveryId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Delivery tracking fetched successfully", tracking));
    }

    @PutMapping("/status/{deliveryId}")
    public ResponseEntity<ApiResponse<?>> updateDeliveryStatus(
            @PathVariable String deliveryId,
            @RequestParam DeliveryStatus status) {
        var updated = deliveryService.updateDeliveryStatus(deliveryId, status);
        return ResponseEntity.ok(new ApiResponse<>(true, "Delivery status updated successfully", updated));
    }

    // Delivery Partner Management
    @PostMapping("/partners")
    public ResponseEntity<ApiResponse<?>> registerDeliveryPartner(@RequestBody DeliveryPartner partner) {
        var registered = partnerService.registerPartner(partner);
        return ResponseEntity.ok(new ApiResponse<>(true, "Delivery partner registered successfully", registered));
    }

    @GetMapping("/partners/{partnerId}")
    public ResponseEntity<ApiResponse<?>> getPartnerDetails(@PathVariable Long partnerId) {
        var partner = partnerService.getPartnerDetails(partnerId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Partner details fetched successfully", partner));
    }

    @PutMapping("/partners/{partnerId}/availability")
    public ResponseEntity<ApiResponse<?>> updatePartnerAvailability(
            @PathVariable Long partnerId,
            @RequestParam boolean available) {
        var updated = partnerService.updateAvailability(partnerId, available);
        return ResponseEntity.ok(new ApiResponse<>(true, "Partner availability updated successfully", updated));
    }

    // Route Optimization
    @PostMapping("/routes/optimize")
    public ResponseEntity<ApiResponse<?>> optimizeDeliveryRoute(@RequestBody RouteOptimizationRequest request) {
        var route = routeService.optimizeRoute(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Route optimized successfully", route));
    }

    @GetMapping("/partners/{partnerId}/route")
    public ResponseEntity<ApiResponse<?>> getPartnerRoute(@PathVariable Long partnerId) {
        var route = routeService.getPartnerRoute(partnerId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Partner route fetched successfully", route));
    }

    // Analytics
    @GetMapping("/analytics/performance")
    public ResponseEntity<ApiResponse<?>> getDeliveryPerformanceMetrics(
            @RequestParam String timeFrame,
            @RequestParam(required = false) Long partnerId) {
        var metrics = deliveryService.getPerformanceMetrics(timeFrame, partnerId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Performance metrics fetched successfully", metrics));
    }

    // Smart Features
    @PostMapping("/smart-allocation")
    public ResponseEntity<ApiResponse<?>> getSmartDeliveryAllocation(@RequestBody SmartAllocationRequest request) {
        var allocation = deliveryService.getSmartAllocation(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Smart allocation completed successfully", allocation));
    }

    @GetMapping("/eta/{deliveryId}")
    public ResponseEntity<ApiResponse<?>> getPredictedETA(@PathVariable String deliveryId) {
        var eta = deliveryService.getPredictedETA(deliveryId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Predicted ETA fetched successfully", eta));
    }
}