package com.foodapp.cart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "combo_deals")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComboDeal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "deal_name", nullable = false)
    private String dealName;

    @Column(name = "deal_code", unique = true)
    private String dealCode;

    @Column(name = "description")
    private String description;

    @Column(name = "restaurant_id")
    private String restaurantId;

    @ElementCollection
    @CollectionTable(name = "combo_deal_items", joinColumns = @JoinColumn(name = "combo_deal_id"))
    @Column(name = "menu_item_id")
    private List<String> requiredItemIds;

    @Column(name = "minimum_quantity")
    private Integer minimumQuantity;

    @Column(name = "discount_type") // PERCENTAGE, FIXED_AMOUNT, BUY_X_GET_Y
    private String discountType;

    @Column(name = "discount_value", precision = 10, scale = 2)
    private BigDecimal discountValue;

    @Column(name = "minimum_order_value", precision = 10, scale = 2)
    private BigDecimal minimumOrderValue;

    @Column(name = "maximum_discount", precision = 10, scale = 2)
    private BigDecimal maximumDiscount;

    @Column(name = "valid_from")
    private LocalDateTime validFrom;

    @Column(name = "valid_until")
    private LocalDateTime validUntil;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "max_uses_per_user")
    private Integer maxUsesPerUser;

    @Column(name = "max_total_uses")
    private Integer maxTotalUses;

    @Column(name = "current_uses")
    @Builder.Default
    private Integer currentUses = 0;

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

    public boolean isValidForDate(LocalDateTime dateTime) {
        return (validFrom == null || dateTime.isAfter(validFrom)) &&
               (validUntil == null || dateTime.isBefore(validUntil));
    }

    public boolean hasUsesRemaining() {
        return maxTotalUses == null || currentUses < maxTotalUses;
    }
}