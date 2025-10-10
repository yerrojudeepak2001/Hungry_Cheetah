package com.foodapp.restaurant.dto.delivery;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryTimeResponse {
    private Long restaurantId;
    private Long orderId;
    private LocalDateTime estimatedDeliveryTime;
    private double distance;
    private String deliveryNotes;
}