package com.foodapp.subscription.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String description;
    private Double price;
    private String duration; // MONTHLY, QUARTERLY, YEARLY
    private Integer deliveryCredits;
    private Double discountPercentage;
    private Boolean isActive;
    
    @ElementCollection
    private List<String> features;
    
    private Integer maxDeliveriesPerMonth;
    private Double freeDeliveryThreshold;
    private Integer prioritySupport;
    private Boolean exclusiveOffers;
    private Double cashbackPercentage;
    private Integer extraLoyaltyPoints;
    
    @OneToMany(mappedBy = "plan")
    private List<Subscription> subscriptions;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}