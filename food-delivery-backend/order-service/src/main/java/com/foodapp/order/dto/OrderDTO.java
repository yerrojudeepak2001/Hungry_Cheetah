package com.foodapp.order.dto;

import com.foodapp.common.dto.DeliveryAddressDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private Long userId;
    private Long restaurantId;
    private String orderStatus;
    private Double totalAmount;
    private LocalDateTime orderTime;
    private LocalDateTime deliveryTime;
    private List<OrderItemDTO> items;
    private DeliveryAddressDTO deliveryAddress;
    private String paymentStatus;
    private String paymentMethod;
    private Boolean isGroupOrder;
    private String specialInstructions;
    
    // Used for order creation
    private String promoCode;
    private String deliveryPreference;
    private Boolean contactlessDelivery;
    private String scheduledDeliveryTime;
}