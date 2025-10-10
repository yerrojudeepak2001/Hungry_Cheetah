package com.foodapp.restaurant.dto.inventory;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryForecast {
    private Long itemId;
    private int predictedDemand;
    private int suggestedStockLevel;
    private LocalDateTime forecastDate;
}