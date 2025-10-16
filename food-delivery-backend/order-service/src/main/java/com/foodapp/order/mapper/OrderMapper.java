package com.foodapp.order.mapper;

import com.foodapp.common.dto.OrderDTO;
import com.foodapp.common.dto.DeliveryAddressDTO;
import com.foodapp.order.model.Order;
import com.foodapp.order.model.DeliveryAddress;
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
        dto.setDeliveryAddress(toDeliveryAddressDTO(order.getDeliveryAddress()));
        
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
        order.setDeliveryAddress(toDeliveryAddress(orderDTO.getDeliveryAddress()));
        
        return order;
    }
    
    private DeliveryAddressDTO toDeliveryAddressDTO(DeliveryAddress address) {
        if (address == null) {
            return null;
        }

        return DeliveryAddressDTO.builder()
                .streetAddress(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .postalCode(address.getZipCode())
                .country(address.getCountry())
                .apartmentNumber(address.getApartmentNumber())
                .latitude(address.getLatitude())
                .longitude(address.getLongitude())
                .build();
    }
    
    private DeliveryAddress toDeliveryAddress(DeliveryAddressDTO dto) {
        if (dto == null) {
            return null;
        }
        
        DeliveryAddress address = new DeliveryAddress();
        address.setStreet(dto.getStreetAddress());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setZipCode(dto.getPostalCode());
        address.setCountry(dto.getCountry());
        address.setApartmentNumber(dto.getApartmentNumber());
        address.setDeliveryInstructions(dto.getLandmark()); // Map landmark to delivery instructions
        address.setLatitude(dto.getLatitude());
        address.setLongitude(dto.getLongitude());
        
        return address;
    }
}