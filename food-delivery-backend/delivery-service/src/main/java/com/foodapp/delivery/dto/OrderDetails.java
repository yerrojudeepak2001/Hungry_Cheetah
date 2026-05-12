package com.foodapp.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails {
    private Long orderId;
    private String customerName;
    private String customerAddress;
    private String restaurantName;
    private String status;
    private Double totalPrice;
}
