package com.foodapp.restaurant.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QualityViolation {
    private Long id;
    private String violationType;
    private String description;
    private String severity;
    private LocalDateTime reportedDate;
    private String status;
}