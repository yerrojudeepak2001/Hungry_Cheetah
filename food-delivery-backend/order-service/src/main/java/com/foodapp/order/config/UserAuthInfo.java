package com.foodapp.order.config;

import java.util.List;

public class UserAuthInfo {
    private String username;
    private String userId;
    private List<String> roles;
    private boolean valid;
    private String email;
    
    // Constructors
    public UserAuthInfo() {}
    
    public UserAuthInfo(String username, String userId, List<String> roles, boolean valid, String email) {
        this.username = username;
        this.userId = userId;
        this.roles = roles;
        this.valid = valid;
        this.email = email;
    }
    
    // Getters and Setters
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public List<String> getRoles() {
        return roles;
    }
    
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
    
    public boolean isValid() {
        return valid;
    }
    
    public void setValid(boolean valid) {
        this.valid = valid;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
}