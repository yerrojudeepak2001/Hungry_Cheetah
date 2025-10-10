package com.foodapp.admin.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantAdminInfo {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String status;
    private String category;
    private double rating;
    private LocalDateTime registrationDate;
    private long totalOrders;
    private double totalRevenue;
}