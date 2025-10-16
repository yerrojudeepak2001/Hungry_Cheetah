package com.foodapp.pricing.service;

import com.foodapp.pricing.dto.Discount;
import com.foodapp.pricing.dto.SpecialOffer;
import com.foodapp.pricing.dto.PriceCalculationRequest;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Service
public class DiscountService {
    
    public BigDecimal calculateDiscount(PriceCalculationRequest request) {
        BigDecimal totalDiscount = BigDecimal.ZERO;
        
        // Apply promo code if provided
        if (request.getPromoCode() != null && !request.getPromoCode().isEmpty()) {
            totalDiscount = totalDiscount.add(applyPromoCode(request.getPromoCode(), request.getSubTotal()));
        }
        
        // Apply special offers
        totalDiscount = totalDiscount.add(applySpecialOffers(request));
        
        // Apply first-time user discount
        totalDiscount = totalDiscount.add(applyFirstTimeDiscount(request));
        
        return totalDiscount;
    }
    
    private BigDecimal applyPromoCode(String promoCode, BigDecimal subTotal) {
        // Simple promo code validation - in real implementation would query database
        switch (promoCode.toUpperCase()) {
            case "SAVE10":
                return subTotal.multiply(BigDecimal.valueOf(0.10)); // 10% off
            case "SAVE20":
                return subTotal.multiply(BigDecimal.valueOf(0.20)); // 20% off
            case "FIRSTORDER":
                return subTotal.multiply(BigDecimal.valueOf(0.15)); // 15% off first order
            case "WELCOME5":
                return BigDecimal.valueOf(5.00); // $5 off
            default:
                return BigDecimal.ZERO;
        }
    }
    
    private BigDecimal applySpecialOffers(PriceCalculationRequest request) {
        // Check for lunch special
        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();
        
        if (hour >= 11 && hour <= 14) { // Lunch time
            if (request.getSubTotal().compareTo(BigDecimal.valueOf(25.00)) >= 0) {
                return BigDecimal.valueOf(3.00); // $3 off lunch orders over $25
            }
        }
        
        // Check for bulk order discount
        if (request.getItems() != null && request.getItems().size() >= 5) {
            return request.getSubTotal().multiply(BigDecimal.valueOf(0.05)); // 5% off bulk orders
        }
        
        return BigDecimal.ZERO;
    }
    
    private BigDecimal applyFirstTimeDiscount(PriceCalculationRequest request) {
        // This would normally check user's order history
        // For now, just placeholder logic
        return BigDecimal.ZERO;
    }
    
    public List<Discount> getAvailableDiscounts(String customerId) {
        List<Discount> discounts = new ArrayList<>();
        
        // Create sample discounts
        Discount loyaltyDiscount = new Discount();
        loyaltyDiscount.setCode("LOYALTY10");
        loyaltyDiscount.setDescription("10% off for loyal customers");
        loyaltyDiscount.setDiscountType("PERCENTAGE");
        loyaltyDiscount.setValue(BigDecimal.valueOf(10));
        loyaltyDiscount.setMinOrderValue(BigDecimal.valueOf(20));
        loyaltyDiscount.setExpiryDate(LocalDateTime.now().plusDays(30));
        loyaltyDiscount.setActive(true);
        discounts.add(loyaltyDiscount);
        
        Discount newUserDiscount = new Discount();
        newUserDiscount.setCode("WELCOME5");
        newUserDiscount.setDescription("$5 off your first order");
        newUserDiscount.setDiscountType("FIXED");
        newUserDiscount.setValue(BigDecimal.valueOf(5));
        newUserDiscount.setMinOrderValue(BigDecimal.valueOf(15));
        newUserDiscount.setExpiryDate(LocalDateTime.now().plusDays(7));
        newUserDiscount.setActive(true);
        discounts.add(newUserDiscount);
        
        return discounts;
    }
    
    public List<SpecialOffer> getActiveOffers(String area) {
        List<SpecialOffer> offers = new ArrayList<>();
        
        // Create sample offers
        SpecialOffer lunchSpecial = new SpecialOffer();
        lunchSpecial.setOfferName("Lunch Special");
        lunchSpecial.setDescription("$3 off orders over $25 during lunch hours");
        lunchSpecial.setOfferType("TIME_BASED");
        lunchSpecial.setDiscountValue(BigDecimal.valueOf(3));
        lunchSpecial.setMinOrderValue(BigDecimal.valueOf(25));
        lunchSpecial.setStartTime(LocalDateTime.now().withHour(11).withMinute(0));
        lunchSpecial.setEndTime(LocalDateTime.now().withHour(14).withMinute(0));
        lunchSpecial.setActive(true);
        offers.add(lunchSpecial);
        
        return offers;
    }
    
    public boolean validatePromoCode(String promoCode, String customerId) {
        // Simple validation - in real implementation would check database
        return promoCode != null && (
            promoCode.equalsIgnoreCase("SAVE10") ||
            promoCode.equalsIgnoreCase("SAVE20") ||
            promoCode.equalsIgnoreCase("FIRSTORDER") ||
            promoCode.equalsIgnoreCase("WELCOME5")
        );
    }
}