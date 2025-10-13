package com.foodapp.restaurant.dto;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryUpdate {
    private Long itemId;
    private String itemName;
    private Integer quantity;
    private String updateType; // STOCK_IN, STOCK_OUT, ADJUSTMENT
    private String reason;
    private java.time.LocalDateTime updateTime;
    private String updatedBy;
}