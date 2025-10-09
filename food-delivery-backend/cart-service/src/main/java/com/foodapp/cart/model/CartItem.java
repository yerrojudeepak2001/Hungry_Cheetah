package com.foodapp.cart.model;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CartItem {
    private Long menuItemId;
    private String itemName;
    private Integer quantity;
    private BigDecimal price;
    private String specialInstructions;
    private Boolean isCustomized;
}