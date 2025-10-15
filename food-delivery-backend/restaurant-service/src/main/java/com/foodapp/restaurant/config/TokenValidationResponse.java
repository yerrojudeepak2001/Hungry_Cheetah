package com.foodapp.restaurant.config;

import java.util.Set;

public class TokenValidationResponse {
    private String username;
    private String email;
    private Set<String> roles;
    private String userId;

    public TokenValidationResponse() {
    }

    public TokenValidationResponse(String username, String email, Set<String> roles, String userId) {
        this.username = username;
        this.email = email;
        this.roles = roles;
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
}