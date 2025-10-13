package com.foodapp.order.mapper;

import com.foodapp.common.dto.OrderDTO;
import com.foodapp.order.model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    
    public OrderDTO toDTO(Order order) {
        if (order == null) {
            return null;
        }
        
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setUserId(order.getUserId());
        dto.setRestaurantId(order.getRestaurantId());
        dto.setOrderStatus(order.getOrderStatus());
        dto.setTotalAmount(java.math.BigDecimal.valueOf(order.getTotalAmount()));
        dto.setCreatedAt(order.getOrderTime());
        
        return dto;
    }
    
    public Order toEntity(OrderDTO orderDTO) {
        if (orderDTO == null) {
            return null;
        }
        
        Order order = new Order();
        order.setId(orderDTO.getId());
        order.setUserId(orderDTO.getUserId());
        order.setRestaurantId(orderDTO.getRestaurantId());
        order.setOrderStatus(orderDTO.getOrderStatus());
        order.setTotalAmount(orderDTO.getTotalAmount().doubleValue());
        order.setOrderTime(orderDTO.getCreatedAt());
        
        return order;
    }
}