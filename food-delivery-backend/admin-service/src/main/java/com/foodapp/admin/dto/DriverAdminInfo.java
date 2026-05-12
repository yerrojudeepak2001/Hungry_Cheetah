package com.foodapp.admin.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverAdminInfo {
    private String driverId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String licenseNumber;
    private String vehicleType;
    private String vehiclePlate;
    private String status;
    private boolean isActive;
    private boolean isOnline;
    private boolean isVerified;
    private double rating;
    private int totalDeliveries;
    private int completedDeliveries;
    private int cancelledDeliveries;
    private String currentLocation;
    private LocalDateTime lastActiveTime;
    private LocalDateTime joinedDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}