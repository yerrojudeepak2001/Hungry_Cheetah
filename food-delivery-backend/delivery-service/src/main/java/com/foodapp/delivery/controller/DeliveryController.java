package com.foodapp.delivery.controller;

import com.foodapp.common.dto.ApiResponse;
import com.foodapp.delivery.model.DeliveryPartner;
import com.foodapp.delivery.model.DeliveryStatus;
import com.foodapp.delivery.service.DeliveryPartnerService;
import com.foodapp.delivery.service.DeliveryService;
import com.foodapp.delivery.service.RouteOptimizationService;
import com.foodapp.delivery.dto.RouteOptimizationRequest;
import com.foodapp.delivery.dto.SmartAllocationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/delivery")
public class DeliveryController<DeliveryPartnerService, RouteOptimizationService> {

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

    // Analytics and Smart Features can be added similarly...
}
