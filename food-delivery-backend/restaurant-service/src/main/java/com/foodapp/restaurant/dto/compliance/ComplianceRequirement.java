package com.foodapp.restaurant.dto.compliance;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplianceRequirement {
    private String code;
    private String description;
    private String category;
    private LocalDateTime effectiveDate;
}