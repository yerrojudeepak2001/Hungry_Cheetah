package com.foodapp.auth.dto;

import lombok.Data;

@Data
public class SuspiciousActivityReport {
    private String activityType;
    private String description;
    private String timestamp;
}