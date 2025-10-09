package com.foodapp.order.service;

import com.foodapp.common.constants.AppConstants;
import com.foodapp.common.dto.OrderDTO;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    private final JmsTemplate jmsTemplate;
    private final OrderRepository orderRepository;

    public OrderService(JmsTemplate jmsTemplate, OrderRepository orderRepository) {
        this.jmsTemplate = jmsTemplate;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        // Save order to database
        Order order = orderRepository.save(convertToEntity(orderDTO));
        
        // Publish order created event
        jmsTemplate.convertAndSend(AppConstants.ORDER_CREATED_QUEUE, order);
        
        return convertToDTO(order);
    }

    public OrderDTO getOrderStatus(Long orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return convertToDTO(order);
    }

    public void updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        order.setOrderStatus(status);
        orderRepository.save(order);
    }
}