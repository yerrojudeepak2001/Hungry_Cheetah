package com.foodapp.loyalty.calculator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PointsCalculator {
    private final RewardEligibilityChecker eligibilityChecker;
    
    // Points for different activities
    private static final int POINTS_PER_DOLLAR = 1;
    private static final int POINTS_FOR_REVIEW = 50;
    private static final int POINTS_FOR_REFERRAL = 200;
    private static final Map<String, Integer> TIER_MULTIPLIERS = Map.of(
        "BRONZE", 1,
        "SILVER", 2,
        "GOLD", 3,
        "PLATINUM", 4
    );

    public int calculateOrderPoints(Order order, String userTier) {
        if (!eligibilityChecker.isOrderEligibleForPoints(order)) {
            return 0;
        }

        int basePoints = calculateBaseOrderPoints(order);
        int tierMultiplier = TIER_MULTIPLIERS.getOrDefault(userTier, 1);
        int bonusPoints = calculateBonusPoints(order);

        return (basePoints * tierMultiplier) + bonusPoints;
    }

    public int calculateReviewPoints(Review review, String userTier) {
        if (!eligibilityChecker.isReviewEligibleForPoints(review)) {
            return 0;
        }

        int basePoints = POINTS_FOR_REVIEW;
        int tierMultiplier = TIER_MULTIPLIERS.getOrDefault(userTier, 1);
        int qualityBonus = calculateReviewQualityBonus(review);

        return (basePoints * tierMultiplier) + qualityBonus;
    }

    public int calculateReferralPoints(Referral referral, String userTier) {
        if (!eligibilityChecker.isReferralEligibleForPoints(referral)) {
            return 0;
        }

        int basePoints = POINTS_FOR_REFERRAL;
        int tierMultiplier = TIER_MULTIPLIERS.getOrDefault(userTier, 1);

        return basePoints * tierMultiplier;
    }

    private int calculateBaseOrderPoints(Order order) {
        return (int) (order.getTotalAmount() * POINTS_PER_DOLLAR);
    }

    private int calculateBonusPoints(Order order) {
        int bonus = 0;
        
        // First order bonus
        if (eligibilityChecker.isFirstOrder(order.getUserId())) {
            bonus += 500;
        }
        
        // Large order bonus
        if (order.getTotalAmount() > 100) {
            bonus += 200;
        }
        
        // Weekend bonus
        if (eligibilityChecker.isWeekendOrder(order)) {
            bonus += 100;
        }

        return bonus;
    }

    private int calculateReviewQualityBonus(Review review) {
        int bonus = 0;
        
        // Bonus for detailed review
        if (review.getContent().length() > 100) {
            bonus += 25;
        }
        
        // Bonus for adding photos
        if (review.getPhotos() != null && !review.getPhotos().isEmpty()) {
            bonus += 25;
        }

        return bonus;
    }
}