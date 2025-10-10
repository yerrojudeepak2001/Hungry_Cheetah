package com.foodapp.restaurant.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryForecast {
    private String itemId;
    private String itemName;
    private Map<LocalDateTime, Integer> forecast;
    private String forecastType;
}