package com.foodapp.common.exception;

public class SecurityException extends RuntimeException {
    public SecurityException(String message) {
        super(message);
    }
}