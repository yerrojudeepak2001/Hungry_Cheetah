package com.foodapp.gateway.model;

public class TokenValidationResponse {
    private boolean valid;
    private String username;
    private String message;

    // Getters & Setters
    public boolean isValid() {
        return valid;
    }
    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
