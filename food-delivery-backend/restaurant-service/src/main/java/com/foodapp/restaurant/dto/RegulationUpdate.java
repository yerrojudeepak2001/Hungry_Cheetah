package com.foodapp.restaurant.dto;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegulationUpdate {
    private Long updateId;
    private String regulationType;
    private String title;
    private String description;
    private java.time.LocalDateTime effectiveDate;
    private String severity;
    private Boolean isActive;
}