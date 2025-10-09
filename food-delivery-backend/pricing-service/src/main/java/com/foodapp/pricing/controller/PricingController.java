package com.foodapp.pricing.controller;

import com.foodapp.common.dto.ApiResponse;
import com.foodapp.pricing.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pricing")
public class PricingController {
    private final PricingService pricingService;
    private final DynamicPricingService dynamicPricingService;
    private final DiscountService discountService;

    public PricingController(PricingService pricingService,
                           DynamicPricingService dynamicPricingService,
                           DiscountService discountService) {
        this.pricingService = pricingService;
        this.dynamicPricingService = dynamicPricingService;
        this.discountService = discountService;
    }

    // Base Pricing
    @PostMapping("/calculate")
    public ResponseEntity<ApiResponse<?>> calculatePrice(@RequestBody PriceCalculationRequest request) {
        var price = pricingService.calculatePrice(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Price calculated successfully", price));
    }

    @GetMapping("/delivery-fee")
    public ResponseEntity<ApiResponse<?>> calculateDeliveryFee(
            @RequestParam double distance,
            @RequestParam(required = false) String timeSlot) {
        var fee = pricingService.calculateDeliveryFee(distance, timeSlot);
        return ResponseEntity.ok(new ApiResponse<>(true, "Delivery fee calculated successfully", fee));
    }

    // Dynamic Pricing
    @PostMapping("/dynamic/update")
    public ResponseEntity<ApiResponse<?>> updateDynamicPricing(@RequestBody DynamicPricingConfig config) {
        var updated = dynamicPricingService.updatePricing(config);
        return ResponseEntity.ok(new ApiResponse<>(true, "Dynamic pricing updated successfully", updated));
    }

    @GetMapping("/dynamic/current")
    public ResponseEntity<ApiResponse<?>> getCurrentPricingFactors(
            @RequestParam String region,
            @RequestParam(required = false) String category) {
        var factors = dynamicPricingService.getCurrentFactors(region, category);
        return ResponseEntity.ok(new ApiResponse<>(true, "Current pricing factors fetched successfully", factors));
    }

    // Discounts
    @PostMapping("/discounts")
    public ResponseEntity<ApiResponse<?>> createDiscount(@RequestBody Discount discount) {
        var created = discountService.createDiscount(discount);
        return ResponseEntity.ok(new ApiResponse<>(true, "Discount created successfully", created));
    }

    @GetMapping("/discounts/applicable")
    public ResponseEntity<ApiResponse<?>> getApplicableDiscounts(
            @RequestParam Long userId,
            @RequestParam(required = false) String category) {
        var discounts = discountService.getApplicableDiscounts(userId, category);
        return ResponseEntity.ok(new ApiResponse<>(true, "Applicable discounts fetched successfully", discounts));
    }

    // Special Pricing
    @PostMapping("/special-offers")
    public ResponseEntity<ApiResponse<?>> createSpecialOffer(@RequestBody SpecialOffer offer) {
        var created = pricingService.createSpecialOffer(offer);
        return ResponseEntity.ok(new ApiResponse<>(true, "Special offer created successfully", created));
    }

    @GetMapping("/special-offers/active")
    public ResponseEntity<ApiResponse<?>> getActiveSpecialOffers() {
        var offers = pricingService.getActiveSpecialOffers();
        return ResponseEntity.ok(new ApiResponse<>(true, "Active special offers fetched successfully", offers));
    }

    // Surge Pricing
    @PostMapping("/surge/configure")
    public ResponseEntity<ApiResponse<?>> configureSurgePricing(@RequestBody SurgePricingConfig config) {
        var configured = dynamicPricingService.configureSurge(config);
        return ResponseEntity.ok(new ApiResponse<>(true, "Surge pricing configured successfully", configured));
    }

    @GetMapping("/surge/status")
    public ResponseEntity<ApiResponse<?>> getSurgeStatus(
            @RequestParam String region,
            @RequestParam String timeSlot) {
        var status = dynamicPricingService.getSurgeStatus(region, timeSlot);
        return ResponseEntity.ok(new ApiResponse<>(true, "Surge status fetched successfully", status));
    }

    // Bulk Pricing
    @PostMapping("/bulk/calculate")
    public ResponseEntity<ApiResponse<?>> calculateBulkPricing(@RequestBody BulkPricingRequest request) {
        var pricing = pricingService.calculateBulkPricing(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Bulk pricing calculated successfully", pricing));
    }

    // Price History
    @GetMapping("/history/{itemId}")
    public ResponseEntity<ApiResponse<?>> getPriceHistory(
            @PathVariable String itemId,
            @RequestParam String timeFrame) {
        var history = pricingService.getPriceHistory(itemId, timeFrame);
        return ResponseEntity.ok(new ApiResponse<>(true, "Price history fetched successfully", history));
    }

    // Pricing Analytics
    @GetMapping("/analytics")
    public ResponseEntity<ApiResponse<?>> getPricingAnalytics(
            @RequestParam String timeFrame,
            @RequestParam(required = false) String category) {
        var analytics = pricingService.getPricingAnalytics(timeFrame, category);
        return ResponseEntity.ok(new ApiResponse<>(true, "Pricing analytics fetched successfully", analytics));
    }
}