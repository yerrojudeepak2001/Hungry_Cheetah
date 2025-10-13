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
public class CartItemUpdate {
    private String menuItemId;
    private Integer quantity;
    private String customizations;
    private String specialInstructions;
    private BigDecimal unitPrice;
}