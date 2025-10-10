package com.foodapp.auth.dto;

import lombok.Data;

@Data
public class UserDetails {
    private String userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String role;
}