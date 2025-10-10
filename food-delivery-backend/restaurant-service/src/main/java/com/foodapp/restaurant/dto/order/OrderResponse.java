package com.foodapp.restaurant.dto.order;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long orderId;
    private Long restaurantId;
    private String orderStatus;
    private LocalDateTime orderTime;
    private String orderDetails;
}