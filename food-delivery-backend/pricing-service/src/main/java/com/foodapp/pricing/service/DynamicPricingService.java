package com.foodapp.pricing.service;

import com.foodapp.pricing.dto.PriceCalculationRequest;
import com.foodapp.pricing.dto.DynamicPricingConfig;
import com.foodapp.pricing.dto.SurgePricingConfig;
import com.foodapp.pricing.dto.WeatherImpact;
import com.foodapp.pricing.client.WeatherClient;
import com.foodapp.pricing.client.OrderClient;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Service
public class DynamicPricingService {
    
    private final WeatherClient weatherClient;
    private final OrderClient orderClient;
    
    public DynamicPricingService(WeatherClient weatherClient, OrderClient orderClient) {
        this.weatherClient = weatherClient;
        this.orderClient = orderClient;
    }
    
    public BigDecimal calculateDynamicAdjustment(PriceCalculationRequest request) {
        BigDecimal adjustment = BigDecimal.ZERO;
        
        // Apply surge pricing based on demand
        adjustment = adjustment.add(calculateSurgePricing(request));
        
        // Apply weather-based adjustments
        adjustment = adjustment.add(calculateWeatherAdjustment(request));
        
        // Apply time-based adjustments
        adjustment = adjustment.add(calculateTimeBasedAdjustment());
        
        return adjustment;
    }
    
    private BigDecimal calculateSurgePricing(PriceCalculationRequest request) {
        try {
            // Get current order volume for the area
            int activeOrders = orderClient.getActiveOrdersCount(request.getArea());
            
            // Calculate surge multiplier based on demand
            if (activeOrders > 100) {
                return request.getSubTotal().multiply(BigDecimal.valueOf(0.20)); // 20% surge
            } else if (activeOrders > 50) {
                return request.getSubTotal().multiply(BigDecimal.valueOf(0.10)); // 10% surge
            }
        } catch (Exception e) {
            // Fallback to no surge pricing
            return BigDecimal.ZERO;
        }
        
        return BigDecimal.ZERO;
    }
    
    private BigDecimal calculateWeatherAdjustment(PriceCalculationRequest request) {
        try {
            WeatherImpact weather = weatherClient.getWeatherImpact(request.getArea());
            
            if (weather != null && weather.getSeverity() != null) {
                switch (weather.getSeverity()) {
                    case "HIGH":
                        return BigDecimal.valueOf(5.00); // $5 bad weather fee
                    case "MEDIUM":
                        return BigDecimal.valueOf(2.50); // $2.50 weather fee
                    case "LOW":
                        return BigDecimal.valueOf(1.00); // $1 minor weather fee
                }
            }
        } catch (Exception e) {
            // Weather service unavailable, no adjustment
            return BigDecimal.ZERO;
        }
        
        return BigDecimal.ZERO;
    }
    
    private BigDecimal calculateTimeBasedAdjustment() {
        LocalTime now = LocalTime.now();
        LocalTime peakStart = LocalTime.of(11, 30); // 11:30 AM
        LocalTime peakEnd = LocalTime.of(14, 30);   // 2:30 PM
        LocalTime dinnerStart = LocalTime.of(18, 0); // 6:00 PM
        LocalTime dinnerEnd = LocalTime.of(21, 0);   // 9:00 PM
        
        // Peak lunch time adjustment
        if (now.isAfter(peakStart) && now.isBefore(peakEnd)) {
            return BigDecimal.valueOf(1.50); // $1.50 peak lunch fee
        }
        
        // Peak dinner time adjustment
        if (now.isAfter(dinnerStart) && now.isBefore(dinnerEnd)) {
            return BigDecimal.valueOf(2.00); // $2.00 peak dinner fee
        }
        
        return BigDecimal.ZERO;
    }
    
    public DynamicPricingConfig getCurrentPricingConfig(String area) {
        DynamicPricingConfig config = new DynamicPricingConfig();
        config.setArea(area);
        config.setTimestamp(LocalDateTime.now());
        
        // Get current conditions
        try {
            int activeOrders = orderClient.getActiveOrdersCount(area);
            WeatherImpact weather = weatherClient.getWeatherImpact(area);
            
            // Set surge config
            SurgePricingConfig surgeConfig = new SurgePricingConfig();
            surgeConfig.setActiveOrders(activeOrders);
            surgeConfig.setThreshold(50);
            
            if (activeOrders > 100) {
                surgeConfig.setMultiplier(BigDecimal.valueOf(1.20));
                surgeConfig.setActive(true);
            } else if (activeOrders > 50) {
                surgeConfig.setMultiplier(BigDecimal.valueOf(1.10));
                surgeConfig.setActive(true);
            } else {
                surgeConfig.setMultiplier(BigDecimal.ONE);
                surgeConfig.setActive(false);
            }
            
            config.setSurgeConfig(surgeConfig);
            config.setWeatherImpact(weather);
            
        } catch (Exception e) {
            // Set default config
            SurgePricingConfig defaultSurge = new SurgePricingConfig();
            defaultSurge.setMultiplier(BigDecimal.ONE);
            defaultSurge.setActive(false);
            config.setSurgeConfig(defaultSurge);
        }
        
        return config;
    }
}