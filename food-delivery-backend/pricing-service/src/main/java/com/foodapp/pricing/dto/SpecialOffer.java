package com.foodapp.pricing.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecialOffer {
    private Long id;
    private String title;
    private String description;
    private String type; // BUY_ONE_GET_ONE, COMBO_DISCOUNT, HAPPY_HOUR
    private BigDecimal discountValue;
    private String discountType; // PERCENTAGE, FIXED_AMOUNT
    private LocalDateTime validFrom;
    private LocalDateTime validTo;
    private String applicableOn; // CATEGORY, SPECIFIC_ITEMS, MINIMUM_ORDER
    private BigDecimal minOrderAmount;
    private String targetCategory;
    private Boolean isActive;
    private Long restaurantId;
    private String imageUrl;
    private String offerName;
    private String offerType;
    private BigDecimal minOrderValue;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean active;
}