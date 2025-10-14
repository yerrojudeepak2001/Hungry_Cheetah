package com.foodapp.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String dateOfBirth;
    
    private String gender;
    private String language;
    private String timezone;
    
    private Boolean isEnabled;
    private Boolean isEmailVerified;
    private Boolean isPhoneVerified;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registrationDate;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLoginDate;
    
    private Integer totalOrders;
    private Double totalSpent;
    private String loyaltyTier;
    private Integer loyaltyPoints;
    
    // Profile completion percentage
    private Integer profileCompleteness;
    
    // Account status
    private String accountStatus; // ACTIVE, SUSPENDED, DEACTIVATED
    
    // Privacy settings
    private Boolean profilePublic;
    private Boolean allowLocationTracking;
}