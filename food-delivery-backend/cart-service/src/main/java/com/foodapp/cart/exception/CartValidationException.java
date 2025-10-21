package com.foodapp.cart.exception;

import com.foodapp.common.exception.ValidationException;

public class CartValidationException extends ValidationException {
    
    public CartValidationException(String message) {
        super(message);
    }
}