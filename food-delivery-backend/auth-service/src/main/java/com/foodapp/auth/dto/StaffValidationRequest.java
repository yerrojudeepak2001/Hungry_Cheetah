package com.foodapp.auth.dto;

import lombok.Data;

@Data
public class StaffValidationRequest {
    private String staffId;
    private String restaurantId;
}