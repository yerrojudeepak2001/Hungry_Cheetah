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
public class PointsTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "loyalty_points_id")
    private LoyaltyPoints loyaltyPoints;
    
    private String transactionType; // EARN, REDEEM, EXPIRE, BONUS
    private Integer points;
    private String description;
    private String source; // ORDER, REFERRAL, REVIEW, CHALLENGE
    private Long referenceId; // orderId, referralId, etc.
    private LocalDateTime transactionDate;
    private String status;
    private String category;
    private Boolean isBonus;
    private String bonusReason;
}