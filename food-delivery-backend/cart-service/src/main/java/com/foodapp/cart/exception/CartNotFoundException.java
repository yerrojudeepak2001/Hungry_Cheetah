package com.foodapp.cart.exception;

import com.foodapp.common.exception.ResourceNotFoundException;

public class CartNotFoundException extends ResourceNotFoundException {
    
    public CartNotFoundException(String message) {
        super(message);
    }
    
    public static CartNotFoundException forUser(String userId) {
        return new CartNotFoundException("Cart not found for user: " + userId);
    }
}