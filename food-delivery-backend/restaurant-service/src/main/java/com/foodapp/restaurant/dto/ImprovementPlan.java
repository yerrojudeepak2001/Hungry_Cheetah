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
public class ImprovementPlan {
    private Long id;
    private Long restaurantId;
    private String title;
    private String description;
    private List<String> actions;
    private LocalDateTime startDate;
    private LocalDateTime targetDate;
    private String status;
}