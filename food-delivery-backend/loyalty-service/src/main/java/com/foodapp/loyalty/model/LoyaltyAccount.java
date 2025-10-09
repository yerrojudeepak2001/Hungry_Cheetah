package com.foodapp.loyalty.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "loyalty_accounts")
public class LoyaltyAccount {
    @Id
    private String id;
    private Long userId;
    private Integer points;
    private String tier; // BRONZE, SILVER, GOLD, PLATINUM
    private LocalDateTime tierExpiryDate;
    
    // Point History
    private List<PointTransaction> pointTransactions;
    private Integer lifetimePoints;
    private Integer pointsExpiringSoon;
    
    // Rewards
    private List<Reward> availableRewards;
    private List<Reward> redeemedRewards;
    private List<String> favoriteRewards;
    
    // Achievement Stats
    private Integer ordersCompleted;
    private Integer restaurantsVisited;
    private Integer cuisinesTried;
    
    // Special Status
    private Boolean vipStatus;
    private List<String> unlockedPerks;
    private List<String> currentChallenges;
    
    @Data
    public static class PointTransaction {
        private String transactionId;
        private LocalDateTime date;
        private Integer points;
        private String type; // EARNED, REDEEMED, EXPIRED
        private String source; // ORDER, PROMOTION, CHALLENGE
        private Long orderId;
        private String description;
    }
    
    @Data
    public static class Reward {
        private String rewardId;
        private String name;
        private String description;
        private Integer pointsCost;
        private String type; // FREE_DELIVERY, DISCOUNT, FREE_ITEM
        private LocalDateTime expiryDate;
        private Boolean isRedeemed;
        private LocalDateTime redeemedDate;
        private String status; // AVAILABLE, REDEEMED, EXPIRED
    }
}