package com.foodapp.auth.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class StaffValidationRequest {
    @NotBlank
    private String userId;
    
    private String staffId;
    
    @NotBlank
    private String restaurantId;
    
    @NotBlank
    private String role;
}