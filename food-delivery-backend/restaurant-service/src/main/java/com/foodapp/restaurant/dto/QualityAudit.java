package com.foodapp.restaurant.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QualityAudit {
    private Long id;
    private Long restaurantId;
    private String auditType;
    private LocalDateTime auditDate;
    private String auditorName;
    private List<String> findings;
    private String overallRating;
}