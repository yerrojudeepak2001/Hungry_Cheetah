package com.foodapp.driver.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String driverLicense;
    private String vehicleType;
    private String vehicleRegistration;
    private String profilePicture;
    private boolean isVerified;
    private Double rating;
}