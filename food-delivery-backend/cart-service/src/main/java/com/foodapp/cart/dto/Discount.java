package com.foodapp.cart.dto;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Discount {
    private String discountId;
    private String discountCode;
    private String discountType; // PERCENTAGE, FIXED_AMOUNT, FREE_DELIVERY
    private BigDecimal discountValue;
    private BigDecimal minimumOrderAmount;
    private String description;
    private java.time.LocalDateTime validFrom;
    private java.time.LocalDateTime validUntil;
    private Boolean isActive;
}