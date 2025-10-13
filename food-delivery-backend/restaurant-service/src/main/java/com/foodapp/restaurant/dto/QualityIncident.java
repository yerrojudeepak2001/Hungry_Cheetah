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
public class QualityIncident {
    private Long id;
    private String incidentType;
    private String description;
    private String severity;
    private LocalDateTime reportedDate;
    private String status;
    private String assignedTo;
}