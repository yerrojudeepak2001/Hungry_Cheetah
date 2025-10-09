package com.foodapp.loyalty.controller;

import com.foodapp.common.dto.ApiResponse;
import com.foodapp.loyalty.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loyalty")
public class LoyaltyController {
    private final LoyaltyService loyaltyService;
    private final RewardService rewardService;
    private final TierService tierService;

    public LoyaltyController(LoyaltyService loyaltyService,
                           RewardService rewardService,
                           TierService tierService) {
        this.loyaltyService = loyaltyService;
        this.rewardService = rewardService;
        this.tierService = tierService;
    }

    // Points Management
    @PostMapping("/points/{userId}/earn")
    public ResponseEntity<ApiResponse<?>> earnPoints(
            @PathVariable Long userId,
            @RequestBody PointsTransaction transaction) {
        var points = loyaltyService.earnPoints(userId, transaction);
        return ResponseEntity.ok(new ApiResponse<>(true, "Points earned successfully", points));
    }

    @PostMapping("/points/{userId}/redeem")
    public ResponseEntity<ApiResponse<?>> redeemPoints(
            @PathVariable Long userId,
            @RequestBody RedemptionRequest request) {
        var redemption = loyaltyService.redeemPoints(userId, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Points redeemed successfully", redemption));
    }

    @GetMapping("/points/{userId}")
    public ResponseEntity<ApiResponse<?>> getPointsBalance(@PathVariable Long userId) {
        var balance = loyaltyService.getPointsBalance(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Points balance fetched successfully", balance));
    }

    // Rewards
    @PostMapping("/rewards")
    public ResponseEntity<ApiResponse<?>> createReward(@RequestBody Reward reward) {
        var created = rewardService.createReward(reward);
        return ResponseEntity.ok(new ApiResponse<>(true, "Reward created successfully", created));
    }

    @GetMapping("/rewards/{userId}")
    public ResponseEntity<ApiResponse<?>> getAvailableRewards(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        var rewards = rewardService.getAvailableRewards(userId, page, size);
        return ResponseEntity.ok(new ApiResponse<>(true, "Available rewards fetched successfully", rewards));
    }

    @GetMapping("/rewards/history/{userId}")
    public ResponseEntity<ApiResponse<?>> getRewardHistory(@PathVariable Long userId) {
        var history = rewardService.getRewardHistory(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Reward history fetched successfully", history));
    }

    // Loyalty Tiers
    @PostMapping("/tiers")
    public ResponseEntity<ApiResponse<?>> createTier(@RequestBody LoyaltyTier tier) {
        var created = tierService.createTier(tier);
        return ResponseEntity.ok(new ApiResponse<>(true, "Loyalty tier created successfully", created));
    }

    @GetMapping("/tiers/{userId}")
    public ResponseEntity<ApiResponse<?>> getUserTier(@PathVariable Long userId) {
        var tier = tierService.getUserTier(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "User tier fetched successfully", tier));
    }

    @GetMapping("/tiers/{userId}/benefits")
    public ResponseEntity<ApiResponse<?>> getTierBenefits(@PathVariable Long userId) {
        var benefits = tierService.getTierBenefits(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Tier benefits fetched successfully", benefits));
    }

    // Challenges and Achievements
    @PostMapping("/challenges")
    public ResponseEntity<ApiResponse<?>> createChallenge(@RequestBody LoyaltyChallenge challenge) {
        var created = loyaltyService.createChallenge(challenge);
        return ResponseEntity.ok(new ApiResponse<>(true, "Challenge created successfully", created));
    }

    @GetMapping("/challenges/{userId}")
    public ResponseEntity<ApiResponse<?>> getUserChallenges(@PathVariable Long userId) {
        var challenges = loyaltyService.getUserChallenges(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "User challenges fetched successfully", challenges));
    }

    @PostMapping("/challenges/{challengeId}/complete")
    public ResponseEntity<ApiResponse<?>> completeChallenge(
            @PathVariable String challengeId,
            @RequestParam Long userId) {
        var completed = loyaltyService.completeChallenge(challengeId, userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Challenge completed successfully", completed));
    }

    // Analytics
    @GetMapping("/analytics/{userId}")
    public ResponseEntity<ApiResponse<?>> getLoyaltyAnalytics(
            @PathVariable Long userId,
            @RequestParam String timeFrame) {
        var analytics = loyaltyService.getLoyaltyAnalytics(userId, timeFrame);
        return ResponseEntity.ok(new ApiResponse<>(true, "Loyalty analytics fetched successfully", analytics));
    }
}