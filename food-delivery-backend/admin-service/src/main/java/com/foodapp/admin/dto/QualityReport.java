package com.foodapp.admin.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QualityReport {
    private Long id;
    private String reportType;
    private String status;
    private String details;
    private Long restaurantId;
    private Long orderId;
    private LocalDateTime reportDate;
    private String reportedBy;
    private String severity;
}