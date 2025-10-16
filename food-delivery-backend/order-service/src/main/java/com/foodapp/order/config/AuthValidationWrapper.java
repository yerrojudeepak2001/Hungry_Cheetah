package com.foodapp.order.config;

public class AuthValidationWrapper {
    private boolean success;
    private String message;
    private TokenValidationResponse data;

    public AuthValidationWrapper() {
    }

    public AuthValidationWrapper(boolean success, String message, TokenValidationResponse data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TokenValidationResponse getData() {
        return data;
    }

    public void setData(TokenValidationResponse data) {
        this.data = data;
    }
}