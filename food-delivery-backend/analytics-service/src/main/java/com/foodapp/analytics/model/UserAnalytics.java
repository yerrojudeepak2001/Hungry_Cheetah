package com.foodapp.analytics.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserAnalytics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long userId;
    private LocalDateTime timestamp;
    
    @ElementCollection
    private Map<String, Integer> orderFrequency;
    
    @ElementCollection
    private Map<String, Double> spendingPattern;
    
    @ElementCollection
    private Map<String, String> preferredCuisines;
    
    private Double averageOrderValue;
    private Integer totalOrders;
    private Integer cancelledOrders;
    private Integer returningCustomerRate;
    
    @ElementCollection
    private Map<String, Integer> peakOrderingHours;
    
    private Double customerLifetimeValue;
    private String userSegment;
    private Double loyaltyScore;
    
    @ElementCollection
    private Map<String, Integer> deviceUsage;
    
    private String preferredPaymentMethod;
    private Double averageDeliveryTime;
}