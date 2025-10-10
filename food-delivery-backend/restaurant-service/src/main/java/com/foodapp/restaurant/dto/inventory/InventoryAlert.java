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
public class InventoryAlert {
    private Long itemId;
    private String alertType;
    private String message;
    private LocalDateTime timestamp;
}