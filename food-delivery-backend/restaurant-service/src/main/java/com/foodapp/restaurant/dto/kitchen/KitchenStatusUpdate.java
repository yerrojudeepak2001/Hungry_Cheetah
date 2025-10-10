package com.foodapp.restaurant.dto.kitchen;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KitchenStatusUpdate {
    private Long restaurantId;
    private String newStatus;
    private String reason;
    private LocalDateTime timestamp;
}