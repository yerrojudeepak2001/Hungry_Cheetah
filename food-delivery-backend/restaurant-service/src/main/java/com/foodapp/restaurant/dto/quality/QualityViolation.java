package com.foodapp.restaurant.dto.quality;

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
    private Long restaurantId;
    private String violationType;
    private String description;
    private LocalDateTime reportedDate;
    private String severity;
    private String status;
    private String resolutionDetails;
    private LocalDateTime resolvedDate;
}