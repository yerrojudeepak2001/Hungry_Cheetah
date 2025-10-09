package com.foodapp.loyalty.calculator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TierCalculator {
    
    // Points thresholds for each tier
    private static final Map<String, Integer> TIER_THRESHOLDS = Map.of(
        "BRONZE", 0,
        "SILVER", 5000,
        "GOLD", 15000,
        "PLATINUM", 50000
    );
    
    public String calculateTier(int totalPoints) {
        String currentTier = "BRONZE";
        
        for (Map.Entry<String, Integer> entry : TIER_THRESHOLDS.entrySet()) {
            if (totalPoints >= entry.getValue()) {
                currentTier = entry.getKey();
            } else {
                break;
            }
        }
        
        return currentTier;
    }
    
    public Map<String, Object> getTierProgress(int totalPoints) {
        String currentTier = calculateTier(totalPoints);
        String nextTier = getNextTier(currentTier);
        int pointsToNextTier = getPointsToNextTier(totalPoints, currentTier, nextTier);
        
        return Map.of(
            "currentTier", currentTier,
            "nextTier", nextTier,
            "currentPoints", totalPoints,
            "pointsToNextTier", pointsToNextTier,
            "progress", calculateProgress(totalPoints, currentTier, nextTier)
        );
    }
    
    private String getNextTier(String currentTier) {
        return switch (currentTier) {
            case "BRONZE" -> "SILVER";
            case "SILVER" -> "GOLD";
            case "GOLD" -> "PLATINUM";
            default -> "PLATINUM";
        };
    }
    
    private int getPointsToNextTier(int totalPoints, String currentTier, String nextTier) {
        if (currentTier.equals("PLATINUM")) {
            return 0;
        }
        
        int nextTierThreshold = TIER_THRESHOLDS.get(nextTier);
        return nextTierThreshold - totalPoints;
    }
    
    private double calculateProgress(int totalPoints, String currentTier, String nextTier) {
        if (currentTier.equals("PLATINUM")) {
            return 100.0;
        }
        
        int currentTierPoints = TIER_THRESHOLDS.get(currentTier);
        int nextTierPoints = TIER_THRESHOLDS.get(nextTier);
        int pointsInCurrentTier = totalPoints - currentTierPoints;
        int pointsNeededForNextTier = nextTierPoints - currentTierPoints;
        
        return (double) pointsInCurrentTier / pointsNeededForNextTier * 100;
    }
    
    public Map<String, Object> getTierBenefits(String tier) {
        return switch (tier) {
            case "BRONZE" -> Map.of(
                "pointsMultiplier", 1,
                "specialOffers", false,
                "prioritySupport", false,
                "freeDelivery", false,
                "birthdayBonus", 500
            );
            case "SILVER" -> Map.of(
                "pointsMultiplier", 2,
                "specialOffers", true,
                "prioritySupport", false,
                "freeDelivery", false,
                "birthdayBonus", 1000
            );
            case "GOLD" -> Map.of(
                "pointsMultiplier", 3,
                "specialOffers", true,
                "prioritySupport", true,
                "freeDelivery", false,
                "birthdayBonus", 2000
            );
            case "PLATINUM" -> Map.of(
                "pointsMultiplier", 4,
                "specialOffers", true,
                "prioritySupport", true,
                "freeDelivery", true,
                "birthdayBonus", 5000
            );
            default -> Map.of(
                "pointsMultiplier", 1,
                "specialOffers", false,
                "prioritySupport", false,
                "freeDelivery", false,
                "birthdayBonus", 500
            );
        };
    }
}