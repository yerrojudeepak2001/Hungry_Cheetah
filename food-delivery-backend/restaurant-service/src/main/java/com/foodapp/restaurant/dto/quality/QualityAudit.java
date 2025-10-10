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
public class QualityAudit {
    private Long id;
    private Long restaurantId;
    private LocalDateTime auditDate;
    private String auditorName;
    private String findings;
    private String recommendations;
    private String status;
    private Double score;
}