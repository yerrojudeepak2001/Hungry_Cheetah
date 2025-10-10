package com.foodapp.cart.dto;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequest {
    private String customerId;
    private String restaurantId;
    private List<ReservationItem> items;
    private Integer reservationTimeMinutes;
    private String sessionId;
    
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReservationItem {
        private String itemId;
        private Integer quantity;
        private Double price;
    }
}