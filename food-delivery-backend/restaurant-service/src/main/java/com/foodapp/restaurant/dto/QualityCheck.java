package com.foodapp.restaurant.dto;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QualityCheck {
    private Long checkId;
    private Long restaurantId;
    private String checkType;
    private String status;
    private String inspector;
    private java.time.LocalDateTime checkDate;
    private String notes;
    private Integer score;
}