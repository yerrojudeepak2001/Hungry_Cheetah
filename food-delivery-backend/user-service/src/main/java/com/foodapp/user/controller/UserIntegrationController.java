package com.foodapp.user.controller;

import com.foodapp.common.dto.ApiResponse;
import com.foodapp.user.dto.*;
import com.foodapp.user.service.ExternalServiceIntegrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/integrations")
public class UserIntegrationController {

    private final ExternalServiceIntegrationService integrationService;

    public UserIntegrationController(ExternalServiceIntegrationService integrationService) {
        this.integrationService = integrationService;
    }

    // Payment Integration Endpoints
    @GetMapping("/payments/methods")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<PaymentMethodDto>>> getPaymentMethods(@PathVariable Long userId) {
        List<PaymentMethodDto> paymentMethods = integrationService.getUserPaymentMethods(userId.toString());
        return ResponseEntity.ok(new ApiResponse<>(true, "Payment methods fetched successfully", paymentMethods));
    }

    @PostMapping("/payments/methods")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PaymentMethodDto>> addPaymentMethod(
            @PathVariable Long userId,
            @Valid @RequestBody PaymentMethodDto paymentMethod) {
        PaymentMethodDto addedMethod = integrationService.addPaymentMethod(userId.toString(), paymentMethod);
        return ResponseEntity.ok(new ApiResponse<>(true, "Payment method added successfully", addedMethod));
    }

    @DeleteMapping("/payments/methods/{methodId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> removePaymentMethod(
            @PathVariable Long userId,
            @PathVariable String methodId) {
        integrationService.removePaymentMethod(userId.toString(), methodId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Payment method removed successfully", null));
    }

    @GetMapping("/payments/wallet/balance")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Double>> getWalletBalance(@PathVariable Long userId) {
        Double balance = integrationService.getWalletBalance(userId.toString());
        return ResponseEntity.ok(new ApiResponse<>(true, "Wallet balance fetched successfully", balance));
    }

    // Notification Integration Endpoints
    @GetMapping("/notifications")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<NotificationDto>>> getNotifications(@PathVariable Long userId) {
        List<NotificationDto> notifications = integrationService.getUserNotifications(userId.toString());
        return ResponseEntity.ok(new ApiResponse<>(true, "Notifications fetched successfully", notifications));
    }

    @GetMapping("/notifications/preferences")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<NotificationPreferenceDto>> getNotificationPreferences(@PathVariable Long userId) {
        NotificationPreferenceDto preferences = integrationService.getNotificationPreferences(userId.toString());
        return ResponseEntity.ok(new ApiResponse<>(true, "Notification preferences fetched successfully", preferences));
    }

    @PutMapping("/notifications/preferences")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<NotificationPreferenceDto>> updateNotificationPreferences(
            @PathVariable Long userId,
            @Valid @RequestBody NotificationPreferenceDto preferences) {
        NotificationPreferenceDto updated = integrationService.updateNotificationPreferences(userId.toString(), preferences);
        return ResponseEntity.ok(new ApiResponse<>(true, "Notification preferences updated successfully", updated));
    }

    // Delivery Integration Endpoints
    @GetMapping("/deliveries/active")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<DeliveryTrackingDto>>> getActiveDeliveries(@PathVariable Long userId) {
        List<DeliveryTrackingDto> deliveries = integrationService.getActiveDeliveries(userId.toString());
        return ResponseEntity.ok(new ApiResponse<>(true, "Active deliveries fetched successfully", deliveries));
    }

    @GetMapping("/deliveries/history")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<DeliveryTrackingDto>>> getDeliveryHistory(@PathVariable Long userId) {
        List<DeliveryTrackingDto> history = integrationService.getDeliveryHistory(userId.toString());
        return ResponseEntity.ok(new ApiResponse<>(true, "Delivery history fetched successfully", history));
    }

    @GetMapping("/deliveries/track/{deliveryId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<DeliveryTrackingDto>> trackDelivery(
            @PathVariable Long userId,
            @PathVariable String deliveryId) {
        DeliveryTrackingDto tracking = integrationService.trackDelivery(deliveryId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Delivery tracking fetched successfully", tracking));
    }

    @PostMapping("/deliveries/validate-address")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Boolean>> validateDeliveryAddress(
            @PathVariable Long userId,
            @Valid @RequestBody DeliveryAddressDto address) {
        Boolean isValid = integrationService.validateDeliveryAddress(userId.toString(), address);
        return ResponseEntity.ok(new ApiResponse<>(true, "Address validation completed", isValid));
    }

    @GetMapping("/deliveries/time-estimate")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Integer>> getDeliveryTimeEstimate(
            @PathVariable Long userId,
            @RequestParam String restaurantId,
            @RequestParam String addressId) {
        Integer estimate = integrationService.getDeliveryTimeEstimate(userId.toString(), restaurantId, addressId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Delivery time estimate fetched successfully", estimate));
    }
}