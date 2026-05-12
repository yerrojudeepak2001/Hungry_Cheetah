package com.foodapp.order.mapper;

import com.foodapp.order.dto.OrderDTO;
import com.foodapp.order.dto.DeliveryAddressDTO;
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
        dto.setTotalAmount(order.getTotalAmount());
        dto.setOrderTime(order.getOrderTime());
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
        order.setOrderTime(orderDTO.getOrderTime());
        order.setDeliveryAddress(toDeliveryAddress(orderDTO.getDeliveryAddress()));

        return order;
    }

    private DeliveryAddressDTO toDeliveryAddressDTO(DeliveryAddress address) {
        if (address == null) {
            return null;
        }

        DeliveryAddressDTO dto = new DeliveryAddressDTO();
        dto.setStreet(address.getStreet());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setPostalCode(address.getZipCode());
        dto.setCountry(address.getCountry());
        dto.setApartmentNumber(address.getApartmentNumber());
        return dto;
    }

    private DeliveryAddress toDeliveryAddress(DeliveryAddressDTO dto) {
        if (dto == null) {
            return null;
        }

        DeliveryAddress address = new DeliveryAddress();
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setZipCode(dto.getPostalCode());
        address.setCountry(dto.getCountry());
        address.setApartmentNumber(dto.getApartmentNumber());
        address.setDeliveryInstructions(dto.getLandmark()); // Map landmark to delivery instructions

        return address;
    }
}