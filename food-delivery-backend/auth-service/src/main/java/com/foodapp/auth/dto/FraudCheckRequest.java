package com.foodapp.auth.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class FraudCheckRequest {
    @NotBlank
    private String userId;
    private String ipAddress;
    private String deviceId;
    private String location;
    private String action;
}