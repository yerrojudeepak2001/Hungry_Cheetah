package com.foodapp.cart.dto;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemValidation {
    private String itemId;
    private String itemName;
    private Boolean isAvailable;
    private Boolean isActive;
    private Boolean isValid;
    private BigDecimal currentPrice;
    private BigDecimal price;
    private String restaurantId;
    private String category;
    private List<String> allergens;
    private Integer preparationTime;
    private String unavailableReason;
    private String errorMessage;
}