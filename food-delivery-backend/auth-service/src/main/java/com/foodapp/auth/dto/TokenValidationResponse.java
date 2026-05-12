package com.foodapp.auth.dto;

import java.util.Set;

public class TokenValidationResponse {
    private String username;
    private String email;
    private Set<String> roles;
    private String userId;
    private boolean valid;

    public TokenValidationResponse() {
    }

    public TokenValidationResponse(String username, String email, Set<String> roles, String userId) {
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.userId = userId;
        this.valid = true; // Token is valid if object is created
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

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}