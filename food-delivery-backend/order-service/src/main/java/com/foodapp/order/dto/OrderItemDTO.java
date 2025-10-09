package com.foodapp.order.dto;

import lombok.Data;

@Data
public class OrderItemDTO {
    private Long id;
    private Long menuItemId;
    private Integer quantity;
    private Double price;
    private String specialInstructions;
    private String itemName;
    private String customizations;
}