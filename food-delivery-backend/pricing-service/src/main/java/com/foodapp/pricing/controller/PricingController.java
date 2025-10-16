package com.foodapp.pricing.controller;

import com.foodapp.common.dto.ApiResponse;
import com.foodapp.pricing.service.PricingService;
import com.foodapp.pricing.service.DynamicPricingService;
import com.foodapp.pricing.service.DiscountService;
import com.foodapp.pricing.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/calculate")
    public ResponseEntity<ApiResponse<Map<String, Object>>> calculatePrice(
            @RequestBody PriceCalculationRequest request) {
        var pricing = pricingService.calculatePrice(request);
        ApiResponse<Map<String, Object>> response = new ApiResponse<>(true, "Pricing calculated successfully", pricing);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/delivery-fee")
    public ResponseEntity<ApiResponse<String>> getDeliveryFee(
            @RequestParam String area, @RequestParam String timeSlot) {
        var fee = pricingService.getDeliveryFee(area, timeSlot);
        ApiResponse<String> response = new ApiResponse<>(true, "Delivery fee calculated", fee.toString());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/dynamic-config/{area}")
    public ResponseEntity<ApiResponse<DynamicPricingConfig>> getDynamicConfig(
            @PathVariable String area) {
        var config = dynamicPricingService.getCurrentPricingConfig(area);
        ApiResponse<DynamicPricingConfig> response = new ApiResponse<>(true, "Dynamic pricing config retrieved", config);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/discounts/{customerId}")
    public ResponseEntity<ApiResponse<List<Discount>>> getAvailableDiscounts(
            @PathVariable String customerId) {
        var discounts = discountService.getAvailableDiscounts(customerId);
        ApiResponse<List<Discount>> response = new ApiResponse<>(true, "Available discounts retrieved", discounts);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/offers/{area}")
    public ResponseEntity<ApiResponse<List<SpecialOffer>>> getActiveOffers(
            @PathVariable String area) {
        var offers = discountService.getActiveOffers(area);
        ApiResponse<List<SpecialOffer>> response = new ApiResponse<>(true, "Active offers retrieved", offers);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/validate-promo")
    public ResponseEntity<ApiResponse<Boolean>> validatePromoCode(
            @RequestParam String promoCode, @RequestParam String customerId) {
        var isValid = discountService.validatePromoCode(promoCode, customerId);
        ApiResponse<Boolean> response = new ApiResponse<>(true, "Promo code validation completed", isValid);
        return ResponseEntity.ok(response);
    }
}