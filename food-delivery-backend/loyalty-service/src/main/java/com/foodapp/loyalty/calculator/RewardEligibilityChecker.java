package com.foodapp.loyalty.calculator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RewardEligibilityChecker {
    
    public boolean isOrderEligibleForPoints(Order order) {
        // Check if order is cancelled or refunded
        if (order.getStatus().equals("CANCELLED") || order.getStatus().equals("REFUNDED")) {
            return false;
        }
        
        // Check if payment was successful
        if (!order.getPaymentStatus().equals("SUCCESS")) {
            return false;
        }
        
        // Check if order meets minimum amount requirement
        if (order.getTotalAmount() < 10.0) {
            return false;
        }
        
        return true;
    }
    
    public boolean isReviewEligibleForPoints(Review review) {
        // Check if review is from a verified purchase
        if (!review.isVerifiedPurchase()) {
            return false;
        }
        
        // Check if review has minimum content length
        if (review.getContent().length() < 20) {
            return false;
        }
        
        // Check if review is not flagged
        if (review.isFlagged()) {
            return false;
        }
        
        return true;
    }
    
    public boolean isReferralEligibleForPoints(Referral referral) {
        // Check if referral resulted in a successful signup
        if (!referral.isSignupCompleted()) {
            return false;
        }
        
        // Check if referred user made their first order
        if (!referral.isFirstOrderCompleted()) {
            return false;
        }
        
        // Check if referral is not expired
        if (referral.isExpired()) {
            return false;
        }
        
        return true;
    }
    
    public boolean isFirstOrder(String userId) {
        // Implementation to check if this is user's first order
        // This would typically involve a database query
        return false; // Placeholder
    }
    
    public boolean isWeekendOrder(Order order) {
        // Check if order was placed on weekend
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(order.getOrderDate());
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        
        return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;
    }
}