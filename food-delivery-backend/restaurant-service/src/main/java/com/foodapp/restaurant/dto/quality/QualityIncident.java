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
public class QualityIncident {
    private Long restaurantId;
    private String incidentType;
    private String description;
    private LocalDateTime reportedAt;
}