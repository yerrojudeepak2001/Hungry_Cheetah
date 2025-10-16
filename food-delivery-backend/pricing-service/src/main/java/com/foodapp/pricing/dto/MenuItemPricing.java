package com.foodapp.pricing.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemPricing {
    private Long menuItemId;
    private String name;
    private BigDecimal basePrice;
    private BigDecimal currentPrice;
    private BigDecimal discountPercentage;
    private Boolean hasSpecialOffer;
    private String offerDescription;
}