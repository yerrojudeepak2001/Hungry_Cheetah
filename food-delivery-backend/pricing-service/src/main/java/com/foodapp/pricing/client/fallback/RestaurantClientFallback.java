package com.foodapp.pricing.client.fallback;

import com.foodapp.pricing.client.RestaurantClient;
import com.foodapp.pricing.dto.RestaurantPricingData;
import com.foodapp.pricing.dto.MenuItemPricing;
import com.foodapp.pricing.dto.SurgePricingUpdate;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;

@Component
public class RestaurantClientFallback implements RestaurantClient {

    @Override
    public RestaurantPricingData getRestaurantPricingData(String restaurantId) {
        // Return default pricing data when restaurant service is unavailable
        RestaurantPricingData defaultData = new RestaurantPricingData();
        defaultData.setRestaurantId(Long.parseLong(restaurantId));
        defaultData.setName("Restaurant Service Unavailable");
        defaultData.setBaseDeliveryFee(BigDecimal.valueOf(2.99));
        defaultData.setServiceFeePercentage(BigDecimal.valueOf(0.10));
        defaultData.setMinimumOrderAmount(BigDecimal.valueOf(15.00));
        defaultData.setIsSurgePricingEnabled(false);
        defaultData.setCurrentSurgeMultiplier(BigDecimal.ONE);
        defaultData.setPriceCategory("MODERATE");
        return defaultData;
    }

    @Override
    public List<MenuItemPricing> getMenuItemsPricing(String restaurantId) {
        // Return empty list when restaurant service is unavailable
        return new ArrayList<>();
    }

    @Override
    public void updateSurgePricing(String restaurantId, SurgePricingUpdate update) {
        // Do nothing when restaurant service is unavailable
        // In a real implementation, you might queue this for later processing
    }
}