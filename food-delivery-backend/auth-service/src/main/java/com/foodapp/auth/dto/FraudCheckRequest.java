package com.foodapp.auth.dto;

import lombok.Data;

@Data
public class FraudCheckRequest {
    private String userId;
    private String ipAddress;
    private String deviceId;
    private String action;
}