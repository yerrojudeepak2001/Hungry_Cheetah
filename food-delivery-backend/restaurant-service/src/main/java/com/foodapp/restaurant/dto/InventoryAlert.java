package com.foodapp.restaurant.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryAlert {
    private String itemId;
    private String itemName;
    private int currentStock;
    private int threshold;
    private String alertType;
    private LocalDateTime alertDate;
}