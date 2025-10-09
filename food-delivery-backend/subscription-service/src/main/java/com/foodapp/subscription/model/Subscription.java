package com.foodapp.subscription.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;

@Data
@Document(collection = "subscriptions")
public class Subscription {
    @Id
    private String id;
    private Long userId;
    private String planType; // BASIC, PREMIUM, ELITE
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean isActive;
    private String status; // ACTIVE, CANCELLED, EXPIRED
    private BigDecimal price;
    private String billingCycle; // MONTHLY, YEARLY
    
    // Benefits
    private Boolean freeDelivery;
    private Integer prioritySupport;
    private Double extraDiscounts;
    private Boolean exclusiveOffers;
    private Boolean priorityRestaurantAccess;
    
    // Usage Stats
    private Integer deliveriesSaved;
    private BigDecimal moneySaved;
    private Integer ordersPlaced;
    
    // Payment Info
    private String paymentMethod;
    private LocalDateTime nextBillingDate;
    private Boolean autoRenew;
    
    // Restrictions
    private Integer monthlyDeliveryLimit;
    private BigDecimal minimumOrderValue;
    private List<String> excludedRestaurants;
    private List<String> excludedItems;
}