package com.foodapp.admin.dto;

import java.util.List;

public class AuthValidationResponse {
    private boolean valid;
    private String userId;
    private String username;
    private String email;
    private List<String> roles;
    private String message;
    
    public AuthValidationResponse() {}
    
    public AuthValidationResponse(boolean valid, String userId, String username, String email, List<String> roles) {
        this.valid = valid;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
    
    // Getters and setters
    public boolean isValid() {
        return valid;
    }
    
    public void setValid(boolean valid) {
        this.valid = valid;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public List<String> getRoles() {
        return roles;
    }
    
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}