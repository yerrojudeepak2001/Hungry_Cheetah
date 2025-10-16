package com.foodapp.order.service;

// import com.foodapp.common.constants.AppConstants; // Temporarily disabled for testing
import com.foodapp.common.dto.OrderDTO;
import com.foodapp.common.exception.ResourceNotFoundException;
import com.foodapp.order.model.Order;
import com.foodapp.order.repository.OrderRepository;
import com.foodapp.order.mapper.OrderMapper;
// import org.springframework.jms.core.JmsTemplate; // Temporarily disabled for testing
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    // private final JmsTemplate jmsTemplate; // Temporarily disabled for testing
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderService(/* JmsTemplate jmsTemplate, */ OrderRepository orderRepository, OrderMapper orderMapper) {
        // this.jmsTemplate = jmsTemplate; // Temporarily disabled for testing
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        // Save order to database
        Order order = orderRepository.save(orderMapper.toEntity(orderDTO));
        
        // Publish order created event
        // jmsTemplate.convertAndSend(AppConstants.ORDER_CREATED_QUEUE, order); // Temporarily disabled for testing
        
        return orderMapper.toDTO(order);
    }

    public OrderDTO getOrderStatus(Long orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return orderMapper.toDTO(order);
    }

    public void updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        order.setOrderStatus(status);
        orderRepository.save(order);
    }
    
    public java.util.List<OrderDTO> getUserOrders(Long userId) {
        java.util.List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(orderMapper::toDTO)
                .collect(java.util.stream.Collectors.toList());
    }
}