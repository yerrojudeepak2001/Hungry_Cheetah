package com.foodapp.cart.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartUpdateMessage implements Serializable {
    private String cartId;
    private String userId;
    private String action; // ADD_ITEM, REMOVE_ITEM, UPDATE_QUANTITY, CLEAR_CART, etc.
    private Map<String, Object> details;
    private long timestamp;
}