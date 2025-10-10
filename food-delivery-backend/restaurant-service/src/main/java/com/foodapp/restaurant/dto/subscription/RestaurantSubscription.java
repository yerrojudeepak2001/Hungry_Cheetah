package com.foodapp.restaurant.dto.subscription;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantSubscription {
    private Long id;
    private Long restaurantId;
    private Long planId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
    private String paymentStatus;
    private LocalDateTime lastBillingDate;
    private LocalDateTime nextBillingDate;
}