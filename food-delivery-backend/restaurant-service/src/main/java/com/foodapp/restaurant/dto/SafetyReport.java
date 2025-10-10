package com.foodapp.restaurant.dto;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SafetyReport {
    private Long reportId;
    private Long restaurantId;
    private String reportType;
    private String description;
    private String severity;
    private String status;
    private java.time.LocalDateTime reportDate;
    private String actionTaken;
}