package com.foodapp.order.mapper;

import com.foodapp.order.dto.OrderDTO;
import com.foodapp.order.dto.OrderItemDTO;
import com.foodapp.order.model.Order;
import com.foodapp.order.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);
    
    OrderDTO toDTO(Order order);
    
    Order toEntity(OrderDTO orderDTO);
    
    OrderItemDTO toDTO(OrderItem orderItem);
    
    OrderItem toEntity(OrderItemDTO orderItemDTO);
}