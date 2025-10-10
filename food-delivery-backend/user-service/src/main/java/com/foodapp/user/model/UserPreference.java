package com.foodapp.user.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    private User user;
    
    @ElementCollection
    private Set<String> dietaryPreferences;
    
    @ElementCollection
    private Set<String> allergies;
    
    @ElementCollection
    private Set<String> cuisinePreferences;
    
    private Boolean vegetarian;
    private Boolean vegan;
    private Integer spicyLevel;
    private Double maxPrice;
    private Integer maxDeliveryTime;
    private Boolean contactlessDelivery;
    
    @ElementCollection
    private Set<String> notificationPreferences;
    
    private String paymentPreference;
    private Boolean savePaymentInfo;
    private String orderNotes;
    private Boolean autoApplyCoupons;
}