package com.foodapp.loyalty.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class LoyaltyPoints {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long userId;
    private Integer points;
    private Integer lifetimePoints;
    private String tier;
    private LocalDateTime lastUpdated;
    private LocalDateTime tierUpdateDate;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loyaltyPoints")
    private List<PointsTransaction> transactions;
    
    private Integer pointsToNextTier;
    private LocalDateTime pointsExpiryDate;
    private Boolean isActive;
    
    @ElementCollection
    private Map<String, Integer> pointsByCategory;
    
    private Integer consecutiveOrders;
    private Double averageOrderValue;
}