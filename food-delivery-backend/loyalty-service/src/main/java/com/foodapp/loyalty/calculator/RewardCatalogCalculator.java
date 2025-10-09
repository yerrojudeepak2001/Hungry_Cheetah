package com.foodapp.loyalty.calculator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RewardCatalogCalculator {
    
    private final Map<String, Integer> REWARD_POINT_COSTS = Map.of(
        "FREE_DELIVERY", 500,
        "FIVE_DOLLAR_OFF", 1000,
        "TEN_DOLLAR_OFF", 1800,
        "TWENTY_DOLLAR_OFF", 3500,
        "PREMIUM_EXPERIENCE", 5000
    );
    
    private final Map<String, Map<String, Integer>> TIER_DISCOUNT_RATES = Map.of(
        "BRONZE", Map.of(
            "FREE_DELIVERY", 0,
            "FIVE_DOLLAR_OFF", 0,
            "TEN_DOLLAR_OFF", 0,
            "TWENTY_DOLLAR_OFF", 0,
            "PREMIUM_EXPERIENCE", 0
        ),
        "SILVER", Map.of(
            "FREE_DELIVERY", 5,
            "FIVE_DOLLAR_OFF", 5,
            "TEN_DOLLAR_OFF", 5,
            "TWENTY_DOLLAR_OFF", 5,
            "PREMIUM_EXPERIENCE", 5
        ),
        "GOLD", Map.of(
            "FREE_DELIVERY", 10,
            "FIVE_DOLLAR_OFF", 10,
            "TEN_DOLLAR_OFF", 10,
            "TWENTY_DOLLAR_OFF", 10,
            "PREMIUM_EXPERIENCE", 10
        ),
        "PLATINUM", Map.of(
            "FREE_DELIVERY", 15,
            "FIVE_DOLLAR_OFF", 15,
            "TEN_DOLLAR_OFF", 15,
            "TWENTY_DOLLAR_OFF", 15,
            "PREMIUM_EXPERIENCE", 15
        )
    );
    
    public List<Map<String, Object>> getAvailableRewards(int userPoints, String userTier) {
        return REWARD_POINT_COSTS.entrySet().stream()
            .map(entry -> {
                String rewardType = entry.getKey();
                int basePointCost = entry.getValue();
                int discountRate = TIER_DISCOUNT_RATES.get(userTier).get(rewardType);
                int actualPointCost = calculateDiscountedPointCost(basePointCost, discountRate);
                
                return Map.of(
                    "rewardType", rewardType,
                    "pointCost", actualPointCost,
                    "isAvailable", userPoints >= actualPointCost,
                    "discount", discountRate,
                    "originalPointCost", basePointCost,
                    "savings", basePointCost - actualPointCost
                );
            })
            .toList();
    }
    
    private int calculateDiscountedPointCost(int basePointCost, int discountRate) {
        return basePointCost - (basePointCost * discountRate / 100);
    }
    
    public boolean isRewardAvailable(String rewardType, int userPoints, String userTier) {
        int basePointCost = REWARD_POINT_COSTS.getOrDefault(rewardType, Integer.MAX_VALUE);
        int discountRate = TIER_DISCOUNT_RATES.get(userTier).getOrDefault(rewardType, 0);
        int actualPointCost = calculateDiscountedPointCost(basePointCost, discountRate);
        
        return userPoints >= actualPointCost;
    }
    
    public Map<String, Object> getRewardDetails(String rewardType, String userTier) {
        int basePointCost = REWARD_POINT_COSTS.get(rewardType);
        int discountRate = TIER_DISCOUNT_RATES.get(userTier).get(rewardType);
        int actualPointCost = calculateDiscountedPointCost(basePointCost, discountRate);
        
        return Map.of(
            "rewardType", rewardType,
            "pointCost", actualPointCost,
            "discount", discountRate,
            "originalPointCost", basePointCost,
            "savings", basePointCost - actualPointCost,
            "description", getRewardDescription(rewardType),
            "termsAndConditions", getRewardTermsAndConditions(rewardType)
        );
    }
    
    private String getRewardDescription(String rewardType) {
        return switch (rewardType) {
            case "FREE_DELIVERY" -> "Get free delivery on your next order";
            case "FIVE_DOLLAR_OFF" -> "Get $5 off your next order";
            case "TEN_DOLLAR_OFF" -> "Get $10 off your next order";
            case "TWENTY_DOLLAR_OFF" -> "Get $20 off your next order";
            case "PREMIUM_EXPERIENCE" -> "Unlock a premium dining experience";
            default -> "Reward description not available";
        };
    }
    
    private List<String> getRewardTermsAndConditions(String rewardType) {
        return switch (rewardType) {
            case "FREE_DELIVERY" -> List.of(
                "Valid for one delivery only",
                "Expires in 30 days",
                "Cannot be combined with other offers"
            );
            case "FIVE_DOLLAR_OFF", "TEN_DOLLAR_OFF", "TWENTY_DOLLAR_OFF" -> List.of(
                "Valid for one order only",
                "Minimum order value applies",
                "Expires in 30 days",
                "Cannot be combined with other offers"
            );
            case "PREMIUM_EXPERIENCE" -> List.of(
                "Reservation required",
                "Valid at participating restaurants only",
                "Expires in 60 days",
                "Cannot be combined with other offers"
            );
            default -> List.of("Terms and conditions not available");
        };
    }
}