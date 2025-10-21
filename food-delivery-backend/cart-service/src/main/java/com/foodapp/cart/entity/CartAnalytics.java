package com.foodapp.cart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cart_analytics")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartAnalytics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "cart_id")
    private Long cartId;

    @Column(name = "action_type", nullable = false)
    private String actionType; // ADD_ITEM, REMOVE_ITEM, UPDATE_QUANTITY, CHECKOUT, ABANDON, etc.

    @Column(name = "menu_item_id")
    private String menuItemId;

    @Column(name = "restaurant_id")
    private String restaurantId;

    @Column(name = "quantity_before")
    private Integer quantityBefore;

    @Column(name = "quantity_after")
    private Integer quantityAfter;

    @Column(name = "price_before", precision = 10, scale = 2)
    private BigDecimal priceBefore;

    @Column(name = "price_after", precision = 10, scale = 2)
    private BigDecimal priceAfter;

    @Column(name = "cart_total_before", precision = 10, scale = 2)
    private BigDecimal cartTotalBefore;

    @Column(name = "cart_total_after", precision = 10, scale = 2)
    private BigDecimal cartTotalAfter;

    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "device_type")
    private String deviceType; // MOBILE, DESKTOP, TABLET

    @Column(name = "platform")
    private String platform; // WEB, ANDROID, IOS

    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "suggestions_shown")
    private Boolean suggestionsShown;

    @Column(name = "suggestion_clicked")
    private String suggestionClicked;

    @Column(name = "combo_deals_shown")
    private Boolean comboDealsShown;

    @Column(name = "combo_deal_applied")
    private String comboDealApplied;

    @Column(name = "checkout_step")
    private String checkoutStep; // For tracking checkout funnel

    @Column(name = "abandonment_reason")
    private String abandonmentReason; // High price, long delivery, etc.

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "metadata", columnDefinition = "JSON")
    private String metadata; // Additional analytics data as JSON

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}