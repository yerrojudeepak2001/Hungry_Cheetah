package com.foodapp.tracking.controller;

import com.foodapp.tracking.dto.*;
import com.foodapp.tracking.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tracking")
public class TrackingController {
    private final TrackingService trackingService;
    private final ETAService etaService;

    public TrackingController(TrackingService trackingService,
            ETAService etaService) {
        this.trackingService = trackingService;
        this.etaService = etaService;
    }

    // Real-time Tracking
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponse<?>> trackOrder(@PathVariable String orderId) {
        var tracking = trackingService.getOrderTracking(orderId);
        return ResponseEntity.ok(ApiResponse.success("Order tracking fetched successfully", tracking));
    }

    @PostMapping("/location/update")
    public ResponseEntity<ApiResponse<?>> updateLocation(@RequestBody LocationUpdate update) {
        var updated = trackingService.updateLocation(update);
        return ResponseEntity.ok(ApiResponse.success("Location updated successfully", updated));
    }

    // ETA Management
    @GetMapping("/eta/{orderId}")
    public ResponseEntity<ApiResponse<?>> getOrderETA(@PathVariable String orderId) {
        var eta = etaService.calculateETA(orderId);
        return ResponseEntity.ok(ApiResponse.success("ETA calculated successfully", eta));
    }

    @PostMapping("/eta/update/{orderId}")
    public ResponseEntity<ApiResponse<?>> updateETA(
            @PathVariable String orderId,
            @RequestBody ETAUpdate update) {
        var updated = etaService.updateETA(update);
        return ResponseEntity.ok(ApiResponse.success("ETA updated successfully", updated));
    }

    // Multi-order Tracking
    @GetMapping("/multi-order/{groupId}")
    public ResponseEntity<ApiResponse<?>> trackMultiOrder(@PathVariable String groupId) {
        var tracking = trackingService.getOrderTracking(groupId);
        return ResponseEntity.ok(ApiResponse.success("Multi-order tracking fetched successfully", tracking));
    }

    // Route Tracking
    @GetMapping("/route/{deliveryId}")
    public ResponseEntity<ApiResponse<?>> getDeliveryRoute(@PathVariable String deliveryId) {
        var route = trackingService.getOrderTracking(deliveryId);
        return ResponseEntity.ok(ApiResponse.success("Delivery route fetched successfully", route));
    }

    @PostMapping("/route/optimize/{deliveryId}")
    public ResponseEntity<ApiResponse<?>> optimizeRoute(@PathVariable String deliveryId) {
        var optimized = trackingService.getOrderTracking(deliveryId);
        return ResponseEntity.ok(ApiResponse.success("Route optimized successfully", optimized));
    }

    // Status Updates
    @PostMapping("/status/update/{orderId}")
    public ResponseEntity<ApiResponse<?>> updateTrackingStatus(
            @PathVariable String orderId,
            @RequestBody StatusUpdate update) {
        var status = trackingService.updateStatus(update);
        return ResponseEntity.ok(ApiResponse.success("Status updated successfully", status));
    }

    // Analytics
    @GetMapping("/analytics/{deliveryId}")
    public ResponseEntity<ApiResponse<?>> getTrackingAnalytics(@PathVariable String deliveryId) {
        var analytics = trackingService.getOrderTracking(deliveryId);
        return ResponseEntity.ok(ApiResponse.success("Tracking analytics fetched successfully", analytics));
    }

    // Geofencing
    @PostMapping("/geofence/create")
    public ResponseEntity<ApiResponse<?>> createGeofence(@RequestBody Geofence geofence) {
        var created = trackingService.createGeofence(geofence);
        return ResponseEntity.ok(ApiResponse.success("Geofence created successfully", created));
    }

    @GetMapping("/geofence/{geofenceId}/status")
    public ResponseEntity<ApiResponse<?>> checkGeofenceStatus(@PathVariable String geofenceId) {
        var status = trackingService.getOrderTracking(geofenceId);
        return ResponseEntity.ok(ApiResponse.success("Geofence status fetched successfully", status));
    }

    // Live Tracking Sharing
    @PostMapping("/share/{orderId}")
    public ResponseEntity<ApiResponse<?>> shareLiveTracking(
            @PathVariable String orderId,
            @RequestBody SharingRequest request) {
        var sharing = trackingService.getOrderTracking(orderId);
        return ResponseEntity.ok(ApiResponse.success("Live tracking shared successfully", sharing));
    }

    // Historical Tracking
    @GetMapping("/history/{orderId}")
    public ResponseEntity<ApiResponse<?>> getTrackingHistory(@PathVariable String orderId) {
        var history = trackingService.getOrderTracking(orderId);
        return ResponseEntity.ok(ApiResponse.success("Tracking history fetched successfully", history));
    }
}