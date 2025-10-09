package com.foodapp.promotion.controller;

import com.foodapp.common.dto.ApiResponse;
import com.foodapp.promotion.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/promotions")
public class PromotionController {
    private final PromotionService promotionService;
    private final CampaignService campaignService;
    private final CouponService couponService;

    public PromotionController(PromotionService promotionService,
                             CampaignService campaignService,
                             CouponService couponService) {
        this.promotionService = promotionService;
        this.campaignService = campaignService;
        this.couponService = couponService;
    }

    // Promotions
    @PostMapping
    public ResponseEntity<ApiResponse<?>> createPromotion(@RequestBody Promotion promotion) {
        var created = promotionService.createPromotion(promotion);
        return ResponseEntity.ok(new ApiResponse<>(true, "Promotion created successfully", created));
    }

    @GetMapping("/{promotionId}")
    public ResponseEntity<ApiResponse<?>> getPromotion(@PathVariable String promotionId) {
        var promotion = promotionService.getPromotion(promotionId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Promotion fetched successfully", promotion));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<?>> getActivePromotions(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String region) {
        var promotions = promotionService.getActivePromotions(category, region);
        return ResponseEntity.ok(new ApiResponse<>(true, "Active promotions fetched successfully", promotions));
    }

    // Campaigns
    @PostMapping("/campaigns")
    public ResponseEntity<ApiResponse<?>> createCampaign(@RequestBody Campaign campaign) {
        var created = campaignService.createCampaign(campaign);
        return ResponseEntity.ok(new ApiResponse<>(true, "Campaign created successfully", created));
    }

    @GetMapping("/campaigns/{campaignId}")
    public ResponseEntity<ApiResponse<?>> getCampaign(@PathVariable String campaignId) {
        var campaign = campaignService.getCampaign(campaignId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Campaign fetched successfully", campaign));
    }

    @GetMapping("/campaigns/active")
    public ResponseEntity<ApiResponse<?>> getActiveCampaigns() {
        var campaigns = campaignService.getActiveCampaigns();
        return ResponseEntity.ok(new ApiResponse<>(true, "Active campaigns fetched successfully", campaigns));
    }

    // Coupons
    @PostMapping("/coupons")
    public ResponseEntity<ApiResponse<?>> createCoupon(@RequestBody Coupon coupon) {
        var created = couponService.createCoupon(coupon);
        return ResponseEntity.ok(new ApiResponse<>(true, "Coupon created successfully", created));
    }

    @GetMapping("/coupons/{code}")
    public ResponseEntity<ApiResponse<?>> validateCoupon(
            @PathVariable String code,
            @RequestParam(required = false) Long userId) {
        var validation = couponService.validateCoupon(code, userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Coupon validated successfully", validation));
    }

    @GetMapping("/coupons/user/{userId}")
    public ResponseEntity<ApiResponse<?>> getUserCoupons(@PathVariable Long userId) {
        var coupons = couponService.getUserCoupons(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "User coupons fetched successfully", coupons));
    }

    // Targeted Promotions
    @PostMapping("/targeted")
    public ResponseEntity<ApiResponse<?>> createTargetedPromotion(@RequestBody TargetedPromotion promotion) {
        var created = promotionService.createTargetedPromotion(promotion);
        return ResponseEntity.ok(new ApiResponse<>(true, "Targeted promotion created successfully", created));
    }

    @GetMapping("/targeted/{userId}")
    public ResponseEntity<ApiResponse<?>> getUserTargetedPromotions(@PathVariable Long userId) {
        var promotions = promotionService.getUserTargetedPromotions(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Targeted promotions fetched successfully", promotions));
    }

    // Referral Programs
    @PostMapping("/referrals")
    public ResponseEntity<ApiResponse<?>> createReferralProgram(@RequestBody ReferralProgram program) {
        var created = promotionService.createReferralProgram(program);
        return ResponseEntity.ok(new ApiResponse<>(true, "Referral program created successfully", created));
    }

    @PostMapping("/referrals/{code}/redeem")
    public ResponseEntity<ApiResponse<?>> redeemReferral(
            @PathVariable String code,
            @RequestParam Long userId) {
        var redemption = promotionService.redeemReferral(code, userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Referral redeemed successfully", redemption));
    }

    // Analytics
    @GetMapping("/analytics")
    public ResponseEntity<ApiResponse<?>> getPromotionAnalytics(
            @RequestParam String timeFrame,
            @RequestParam(required = false) String promotionType) {
        var analytics = promotionService.getPromotionAnalytics(timeFrame, promotionType);
        return ResponseEntity.ok(new ApiResponse<>(true, "Promotion analytics fetched successfully", analytics));
    }

    // Performance Tracking
    @GetMapping("/performance/{promotionId}")
    public ResponseEntity<ApiResponse<?>> getPromotionPerformance(@PathVariable String promotionId) {
        var performance = promotionService.getPromotionPerformance(promotionId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Promotion performance fetched successfully", performance));
    }
}