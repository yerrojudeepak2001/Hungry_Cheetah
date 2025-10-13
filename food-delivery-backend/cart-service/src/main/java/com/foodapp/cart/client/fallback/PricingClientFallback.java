package com.foodapp.cart.client.fallback;

import com.foodapp.cart.client.PricingClient;
import com.foodapp.cart.dto.PricingRequest;
import com.foodapp.cart.dto.PriceBreakdown;
import com.foodapp.cart.dto.Discount;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Component
public class PricingClientFallback implements PricingClient {

    @Override
    public PriceBreakdown calculatePrice(PricingRequest request) {
        PriceBreakdown breakdown = new PriceBreakdown();
        breakdown.setSubtotal(BigDecimal.ZERO);
        breakdown.setDeliveryFee(BigDecimal.ZERO);
        breakdown.setTax(BigDecimal.ZERO);
        breakdown.setDiscount(BigDecimal.ZERO);
        breakdown.setTotal(BigDecimal.ZERO);
        breakdown.setAppliedDiscounts(Collections.emptyList());
        breakdown.setErrorMessage("Pricing service unavailable");
        return breakdown;
    }

    @Override
    public double getDeliveryFee(String restaurantId, String deliveryLocation) {
        return 0.0;
    }

    @Override
    public List<Discount> getApplicableDiscounts(String userId) {
        return Collections.emptyList();
    }
}