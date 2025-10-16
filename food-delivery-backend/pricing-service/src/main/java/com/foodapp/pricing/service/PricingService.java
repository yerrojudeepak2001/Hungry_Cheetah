package com.foodapp.pricing.service;

import com.foodapp.pricing.dto.PriceCalculationRequest;
import com.foodapp.pricing.client.RestaurantClient;
import com.foodapp.pricing.client.WeatherClient;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Map;
import java.util.HashMap;

@Service
public class PricingService {
    
    private final RestaurantClient restaurantClient;
    private final WeatherClient weatherClient;
    private final DynamicPricingService dynamicPricingService;
    
    public PricingService(RestaurantClient restaurantClient,
                         WeatherClient weatherClient,
                         DynamicPricingService dynamicPricingService) {
        this.restaurantClient = restaurantClient;
        this.weatherClient = weatherClient;
        this.dynamicPricingService = dynamicPricingService;
    }
    
    public Map<String, Object> calculatePrice(PriceCalculationRequest request) {
        Map<String, Object> pricing = new HashMap<>();
        
        // Calculate base pricing
        BigDecimal subTotal = request.getSubTotal() != null ? request.getSubTotal() : calculateSubTotal(request);
        BigDecimal deliveryFee = calculateDeliveryFee(request);
        BigDecimal serviceFee = calculateServiceFee(subTotal);
        BigDecimal taxes = calculateTaxes(subTotal.add(serviceFee));
        
        // Apply dynamic pricing adjustments
        BigDecimal dynamicAdjustment = dynamicPricingService.calculateDynamicAdjustment(request);
        
        BigDecimal total = subTotal.add(deliveryFee).add(serviceFee).add(taxes).add(dynamicAdjustment);
        
        pricing.put("subTotal", subTotal);
        pricing.put("deliveryFee", deliveryFee);
        pricing.put("serviceFee", serviceFee);
        pricing.put("taxes", taxes);
        pricing.put("dynamicAdjustment", dynamicAdjustment);
        pricing.put("total", total);
        pricing.put("breakdown", createPriceBreakdown(subTotal, deliveryFee, serviceFee, taxes, dynamicAdjustment));
        
        return pricing;
    }
    
    private BigDecimal calculateSubTotal(PriceCalculationRequest request) {
        // Calculate subtotal from items
        return request.getItems().stream()
            .map(item -> item.getBasePrice().multiply(BigDecimal.valueOf(item.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    private BigDecimal calculateDeliveryFee(PriceCalculationRequest request) {
        // Base delivery fee - would be enhanced with distance calculation
        return BigDecimal.valueOf(2.99);
    }
    
    private BigDecimal calculateServiceFee(BigDecimal subTotal) {
        // 10% service fee
        return subTotal.multiply(BigDecimal.valueOf(0.10));
    }
    
    private BigDecimal calculateTaxes(BigDecimal taxableAmount) {
        // 8.5% tax rate
        return taxableAmount.multiply(BigDecimal.valueOf(0.085));
    }
    
    private Map<String, Object> createPriceBreakdown(BigDecimal subTotal, BigDecimal deliveryFee, 
                                                   BigDecimal serviceFee, BigDecimal taxes, 
                                                   BigDecimal dynamicAdjustment) {
        Map<String, Object> breakdown = new HashMap<>();
        breakdown.put("itemsTotal", subTotal);
        breakdown.put("delivery", deliveryFee);
        breakdown.put("service", serviceFee);
        breakdown.put("taxes", taxes);
        breakdown.put("adjustments", dynamicAdjustment);
        return breakdown;
    }
    
    public BigDecimal getDeliveryFee(String area, String timeSlot) {
        // TODO: Implement area and time-based delivery fee calculation
        return BigDecimal.valueOf(2.99);
    }
}