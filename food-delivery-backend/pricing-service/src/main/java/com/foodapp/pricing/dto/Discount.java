package com.foodapp.pricing.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Discount {
    private Long id;
    private String code;
    private String type; // PERCENTAGE, FIXED_AMOUNT, FREE_DELIVERY
    private BigDecimal value;
    private BigDecimal minOrderAmount;
    private BigDecimal maxDiscountAmount;
    private LocalDateTime validFrom;
    private LocalDateTime validTo;
    private Boolean isActive;
    private String applicableFor; // ALL, FIRST_TIME, EXISTING_CUSTOMERS
    private Long restaurantId;
    private String description;
    private String discountType; // PERCENTAGE, FIXED
    private BigDecimal minOrderValue;
    private LocalDateTime expiryDate;
    private boolean active;
}