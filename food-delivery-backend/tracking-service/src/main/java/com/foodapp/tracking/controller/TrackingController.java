package com.foodapp.tracking.controller;

import com.foodapp.common.dto.ApiResponse;
import com.foodapp.tracking.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tracking")
public class TrackingController {
    private final TrackingService trackingService;
    private final LocationService locationService;
    private final ETAService etaService;

    public TrackingController(TrackingService trackingService,
                            LocationService locationService,
                            ETAService etaService) {
        this.trackingService = trackingService;
        this.locationService = locationService;
        this.etaService = etaService;
    }

    // Real-time Tracking
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponse<?>> trackOrder(@PathVariable String orderId) {
        var tracking = trackingService.trackOrder(orderId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Order tracking fetched successfully", tracking));
    }

    @PostMapping("/location/update")
    public ResponseEntity<ApiResponse<?>> updateLocation(@RequestBody LocationUpdate update) {
        var updated = locationService.updateLocation(update);
        return ResponseEntity.ok(new ApiResponse<>(true, "Location updated successfully", updated));
    }

    // ETA Management
    @GetMapping("/eta/{orderId}")
    public ResponseEntity<ApiResponse<?>> getOrderETA(@PathVariable String orderId) {
        var eta = etaService.calculateETA(orderId);
        return ResponseEntity.ok(new ApiResponse<>(true, "ETA calculated successfully", eta));
    }

    @PostMapping("/eta/update/{orderId}")
    public ResponseEntity<ApiResponse<?>> updateETA(
            @PathVariable String orderId,
            @RequestBody ETAUpdate update) {
        var updated = etaService.updateETA(orderId, update);
        return ResponseEntity.ok(new ApiResponse<>(true, "ETA updated successfully", updated));
    }

    // Multi-order Tracking
    @GetMapping("/multi-order/{groupId}")
    public ResponseEntity<ApiResponse<?>> trackMultiOrder(@PathVariable String groupId) {
        var tracking = trackingService.trackMultiOrder(groupId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Multi-order tracking fetched successfully", tracking));
    }

    // Route Tracking
    @GetMapping("/route/{deliveryId}")
    public ResponseEntity<ApiResponse<?>> getDeliveryRoute(@PathVariable String deliveryId) {
        var route = trackingService.getDeliveryRoute(deliveryId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Delivery route fetched successfully", route));
    }

    @PostMapping("/route/optimize/{deliveryId}")
    public ResponseEntity<ApiResponse<?>> optimizeRoute(@PathVariable String deliveryId) {
        var optimized = trackingService.optimizeRoute(deliveryId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Route optimized successfully", optimized));
    }

    // Status Updates
    @PostMapping("/status/update/{orderId}")
    public ResponseEntity<ApiResponse<?>> updateTrackingStatus(
            @PathVariable String orderId,
            @RequestBody StatusUpdate update) {
        var status = trackingService.updateStatus(orderId, update);
        return ResponseEntity.ok(new ApiResponse<>(true, "Status updated successfully", status));
    }

    // Analytics
    @GetMapping("/analytics/{deliveryId}")
    public ResponseEntity<ApiResponse<?>> getTrackingAnalytics(@PathVariable String deliveryId) {
        var analytics = trackingService.getTrackingAnalytics(deliveryId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Tracking analytics fetched successfully", analytics));
    }

    // Geofencing
    @PostMapping("/geofence/create")
    public ResponseEntity<ApiResponse<?>> createGeofence(@RequestBody Geofence geofence) {
        var created = trackingService.createGeofence(geofence);
        return ResponseEntity.ok(new ApiResponse<>(true, "Geofence created successfully", created));
    }

    @GetMapping("/geofence/{geofenceId}/status")
    public ResponseEntity<ApiResponse<?>> checkGeofenceStatus(@PathVariable String geofenceId) {
        var status = trackingService.checkGeofenceStatus(geofenceId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Geofence status fetched successfully", status));
    }

    // Live Tracking Sharing
    @PostMapping("/share/{orderId}")
    public ResponseEntity<ApiResponse<?>> shareLiveTracking(
            @PathVariable String orderId,
            @RequestBody SharingRequest request) {
        var sharing = trackingService.shareLiveTracking(orderId, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Live tracking shared successfully", sharing));
    }

    // Historical Tracking
    @GetMapping("/history/{orderId}")
    public ResponseEntity<ApiResponse<?>> getTrackingHistory(@PathVariable String orderId) {
        var history = trackingService.getTrackingHistory(orderId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Tracking history fetched successfully", history));
    }
}