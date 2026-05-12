package com.foodapp.driver.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails {
    private String orderId;
    private Long customerId;
    private Long restaurantId;
    private String restaurantName;
    private String restaurantAddress;
    private String deliveryAddress;
    private String customerName;
    private String customerPhone;
    private List<OrderItem> items;
    private BigDecimal totalAmount;
    private String orderStatus;
    private LocalDateTime orderTime;
    private LocalDateTime estimatedDeliveryTime;
    private String specialInstructions;
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderItem {
        private String itemName;
        private Integer quantity;
        private BigDecimal price;
        private String notes;
    }
}