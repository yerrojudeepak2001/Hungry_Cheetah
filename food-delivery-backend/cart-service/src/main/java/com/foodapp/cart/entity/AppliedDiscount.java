package com.foodapp.cart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "applied_discounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppliedDiscount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @Column(name = "discount_id")
    private String discountId;

    @Column(name = "discount_code")
    private String discountCode;

    @Column(name = "discount_type") // PERCENTAGE, FIXED_AMOUNT, FREE_DELIVERY, COMBO_DEAL
    private String discountType;

    @Column(name = "discount_value", precision = 10, scale = 2)
    private BigDecimal discountValue;

    @Column(name = "discount_amount", precision = 10, scale = 2)
    private BigDecimal discountAmount; // Actual amount discounted

    @Column(name = "description")
    private String description;

    @Column(name = "applied_at")
    private LocalDateTime appliedAt;

    @Column(name = "is_automatic")
    @Builder.Default
    private Boolean isAutomatic = false; // Whether discount was applied automatically

    @PrePersist
    protected void onCreate() {
        appliedAt = LocalDateTime.now();
    }
}