package com.foodapp.user.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    
    private Long userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Set<String> roles;
    private String token;  // JWT token (if using JWT)
    private LocalDateTime loginTime;
    private LocalDateTime tokenExpiration;
    private boolean isEmailVerified;
    private boolean isPhoneVerified;
    private String profilePicture;
    private String message;
}