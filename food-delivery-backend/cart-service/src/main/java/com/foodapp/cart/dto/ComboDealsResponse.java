package com.foodapp.cart.dto;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComboDealsResponse {
    private String dealId;
    private String dealName;
    private String dealCode;
    private String description;
    private String discountType;
    private BigDecimal discountValue;
    private BigDecimal potentialSavings;
    private List<String> requiredItemIds;
    private List<String> requiredItemNames;
    private Integer minimumQuantity;
    private BigDecimal minimumOrderValue;
    private Boolean isApplicable;
    private Boolean isApplied;
    private String applicationStatus; // APPLICABLE, MISSING_ITEMS, MINIMUM_NOT_MET, EXPIRED, ALREADY_APPLIED
    private List<String> missingItems;
    private LocalDateTime validUntil;
    private String termsAndConditions;
}