package com.foodapp.pricing.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.Map;
import java.util.List;

@Data
@Document(collection = "dynamic_pricing")
public class DynamicPricing {
    @Id
    private String id;
    private String region;
    private LocalDateTime timestamp;
    private String pricingStatus; // NORMAL, SURGE, SPECIAL
    
    // Demand Factors
    private Integer activeOrders;
    private Integer availableDrivers;
    private Double demandMultiplier;
    private Map<String, Double> regionLoadFactors;
    
    // Weather Impact
    private String weatherCondition;
    private Double weatherMultiplier;
    private Map<String, Double> weatherFactors;
    
    // Time-based Factors
    private String timeSlot;
    private Double timeMultiplier;
    private Map<String, Double> peakHourFactors;
    
    // Event Impact
    private List<String> activeEvents;
    private Double eventMultiplier;
    private Map<String, Double> eventFactors;
    
    // Special Conditions
    private Boolean isHoliday;
    private Boolean isWeekend;
    private Double specialDayMultiplier;
    
    // Price Adjustments
    private BigDecimal baseDeliveryFee;
    private BigDecimal currentDeliveryFee;
    private Double surgePriceMultiplier;
    private Map<String, BigDecimal> distanceBasedFees;
    
    // Caps and Limits
    private BigDecimal maxSurgePrice;
    private BigDecimal minDeliveryFee;
    private Double maxMultiplier;
    
    // Historical Data
    private Map<String, Double> historicalDemand;
    private Map<String, Double> historicalPricing;
    private List<PriceChange> priceChangeHistory;
    
    @Data
    public static class PriceChange {
        private LocalDateTime timestamp;
        private BigDecimal oldPrice;
        private BigDecimal newPrice;
        private String reason;
        private Map<String, Double> factors;
    }
}