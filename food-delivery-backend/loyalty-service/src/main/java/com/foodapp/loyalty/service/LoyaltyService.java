package com.foodapp.loyalty.service;

import com.foodapp.loyalty.model.LoyaltyPoints;
import com.foodapp.loyalty.model.PointsTransaction;
import com.foodapp.loyalty.repository.LoyaltyPointsRepository;
import com.foodapp.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class LoyaltyService {
    private final LoyaltyPointsRepository loyaltyPointsRepository;
    private final TierService tierService;
    private final NotificationService notificationService;

    public LoyaltyService(LoyaltyPointsRepository loyaltyPointsRepository,
                         TierService tierService,
                         NotificationService notificationService) {
        this.loyaltyPointsRepository = loyaltyPointsRepository;
        this.tierService = tierService;
        this.notificationService = notificationService;
    }

    @Transactional
    public LoyaltyPoints earnPoints(Long userId, PointsTransaction transaction) {
        LoyaltyPoints points = getLoyaltyPoints(userId);
        
        // Calculate points based on transaction
        Integer earnedPoints = calculatePoints(transaction);
        
        // Update points
        points.setPoints(points.getPoints() + earnedPoints);
        points.setLifetimePoints(points.getLifetimePoints() + earnedPoints);
        points.setLastUpdated(LocalDateTime.now());
        
        // Add transaction
        transaction.setLoyaltyPoints(points);
        points.getTransactions().add(transaction);
        
        // Check and update tier
        checkAndUpdateTier(points);
        
        // Save and notify
        LoyaltyPoints updatedPoints = loyaltyPointsRepository.save(points);
        notificationService.sendPointsEarnedNotification(userId, earnedPoints);
        
        return updatedPoints;
    }

    @Transactional
    public LoyaltyPoints redeemPoints(Long userId, Integer pointsToRedeem) {
        LoyaltyPoints points = getLoyaltyPoints(userId);
        
        // Validate points balance
        if (points.getPoints() < pointsToRedeem) {
            throw new IllegalArgumentException("Insufficient points balance");
        }
        
        // Update points
        points.setPoints(points.getPoints() - pointsToRedeem);
        points.setLastUpdated(LocalDateTime.now());
        
        // Create redemption transaction
        PointsTransaction transaction = createRedemptionTransaction(pointsToRedeem);
        transaction.setLoyaltyPoints(points);
        points.getTransactions().add(transaction);
        
        // Save and notify
        LoyaltyPoints updatedPoints = loyaltyPointsRepository.save(points);
        notificationService.sendPointsRedeemedNotification(userId, pointsToRedeem);
        
        return updatedPoints;
    }

    public LoyaltyPoints getLoyaltyPoints(Long userId) {
        return loyaltyPointsRepository.findByUserId(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Loyalty points not found for user: " + userId));
    }

    private void checkAndUpdateTier(LoyaltyPoints points) {
        String newTier = tierService.calculateTier(points.getLifetimePoints());
        if (!newTier.equals(points.getTier())) {
            points.setTier(newTier);
            points.setTierUpdateDate(LocalDateTime.now());
            notificationService.sendTierUpdateNotification(points.getUserId(), newTier);
        }
    }

    private Integer calculatePoints(PointsTransaction transaction) {
        // Implement points calculation logic based on transaction type and amount
        return 0; // Placeholder
    }

    private PointsTransaction createRedemptionTransaction(Integer points) {
        PointsTransaction transaction = new PointsTransaction();
        transaction.setTransactionType("REDEEM");
        transaction.setPoints(points);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setStatus("COMPLETED");
        return transaction;
    }
}