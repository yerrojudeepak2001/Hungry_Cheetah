package com.foodapp.cart.dto;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockStatus {
    private String itemId;
    private String itemName;
    private Integer availableStock;
    private Integer reservedStock;
    private Boolean isAvailable;
    private String status; // IN_STOCK, LOW_STOCK, OUT_OF_STOCK
    private java.time.LocalDateTime lastUpdated;
}