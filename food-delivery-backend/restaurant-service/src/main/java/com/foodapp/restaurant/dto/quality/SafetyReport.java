package com.foodapp.restaurant.dto.quality;

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
public class SafetyReport {
    private Long restaurantId;
    private LocalDateTime reportDate;
    private List<String> observations;
    private String overallRating;
}