package com.foodapp.admin.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QualityViolation {
    private Long id;
    private String violationType;
    private String description;
    private String status;
    private Long restaurantId;
    private String severity;
    private LocalDateTime reportedDate;
    private LocalDateTime resolvedDate;
}