package com.foodapp.restaurant.dto.compliance;

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
public class ComplianceResult {
    private Long restaurantId;
    private boolean passed;
    private List<String> violations;
    private LocalDateTime checkDate;
}