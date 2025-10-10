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
public class ComplianceCheck {
    private Long restaurantId;
    private List<String> checkItems;
    private LocalDateTime checkDate;
}