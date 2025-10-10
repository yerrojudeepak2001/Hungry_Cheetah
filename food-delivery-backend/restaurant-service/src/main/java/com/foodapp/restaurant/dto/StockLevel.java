package com.foodapp.restaurant.dto;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockLevel {
    private Long itemId;
    private String itemName;
    private Integer currentStock;
    private Integer minStockLevel;
    private Integer maxStockLevel;
    private String unit;
    private String status; // IN_STOCK, LOW_STOCK, OUT_OF_STOCK
    private java.time.LocalDateTime lastUpdated;
}