package com.foodapp.auth.dto;

import lombok.Data;
import java.util.Set;

@Data
public class UserDetails {
    private Long id;
    private String userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Set<String> roles;
    private String role;
    private boolean enabled;
}