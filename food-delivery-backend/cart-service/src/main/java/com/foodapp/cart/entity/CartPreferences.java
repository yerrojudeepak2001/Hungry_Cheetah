package com.foodapp.cart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "cart_preferences")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartPreferences {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;

    @Column(name = "auto_save_enabled")
    @Builder.Default
    private Boolean autoSaveEnabled = true;

    @Column(name = "smart_suggestions_enabled")
    @Builder.Default
    private Boolean smartSuggestionsEnabled = true;

    @Column(name = "combo_deals_enabled")
    @Builder.Default
    private Boolean comboDealsEnabled = true;

    @Column(name = "price_alerts_enabled")
    @Builder.Default
    private Boolean priceAlertsEnabled = true;

    @Column(name = "notification_preferences")
    private String notificationPreferences; // JSON string for complex preferences

    @Column(name = "preferred_currency")
    @Builder.Default
    private String preferredCurrency = "USD";

    @Column(name = "cart_expiry_hours")
    @Builder.Default
    private Integer cartExpiryHours = 24;

    @Column(name = "max_cart_items")
    @Builder.Default
    private Integer maxCartItems = 50;

    @Column(name = "allow_multi_restaurant")
    @Builder.Default
    private Boolean allowMultiRestaurant = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}