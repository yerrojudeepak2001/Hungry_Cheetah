package com.foodapp.common.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    private Long userId;
    private Long restaurantId;
    private List<OrderItemDTO> items;
    private BigDecimal totalAmount;
    private String orderStatus;
    private String deliveryAddress;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}