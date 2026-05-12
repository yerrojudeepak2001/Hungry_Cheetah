package com.foodapp.cart.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryUpdateMessage implements Serializable {
    private String restaurantId;
    private String menuItemId;
    private int quantityChange; // positive for increase, negative for decrease
    private String reason; // CART_CHECKOUT, CART_ABANDONED, MANUAL_ADJUSTMENT, etc.
    private long timestamp;
}