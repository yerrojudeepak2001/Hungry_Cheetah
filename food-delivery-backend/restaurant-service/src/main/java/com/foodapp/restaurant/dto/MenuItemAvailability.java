package com.foodapp.restaurant.dto;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemAvailability {
    private Long itemId;
    private String itemName;
    private Boolean isAvailable;
    private Integer stockCount;
    private String unavailableReason;
    private java.time.LocalDateTime expectedAvailableTime;
}