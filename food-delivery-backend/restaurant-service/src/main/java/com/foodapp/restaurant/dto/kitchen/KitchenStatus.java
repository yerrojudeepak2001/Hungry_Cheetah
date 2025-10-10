package com.foodapp.restaurant.dto.kitchen;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KitchenStatus {
    private Long restaurantId;
    private String status;
    private int queueLength;
    private int estimatedWaitTime;
}