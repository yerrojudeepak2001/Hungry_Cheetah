package com.foodapp.restaurant.dto.inventory;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockLevel {
    private Long itemId;
    private int quantity;
    private String status;
}