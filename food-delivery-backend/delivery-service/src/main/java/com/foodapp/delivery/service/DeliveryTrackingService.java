package com.foodapp.delivery.service;

import com.foodapp.common.constants.AppConstants;
import com.foodapp.common.dto.OrderDTO;
import org.springframework.data.mongodb.core.geo.GeoJson;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class DeliveryTrackingService {
    private final JmsTemplate jmsTemplate;
    private final DeliveryRepository deliveryRepository;

    public DeliveryTrackingService(JmsTemplate jmsTemplate, DeliveryRepository deliveryRepository) {
        this.jmsTemplate = jmsTemplate;
        this.deliveryRepository = deliveryRepository;
    }

    @JmsListener(destination = AppConstants.ORDER_CREATED_QUEUE)
    public void assignDeliveryAgent(OrderDTO order) {
        // Find nearest available delivery agent
        DeliveryAgent agent = findNearestAgent(order.getDeliveryAddress());
        
        // Create delivery assignment
        DeliveryAssignment assignment = new DeliveryAssignment();
        assignment.setOrderId(order.getId());
        assignment.setAgentId(agent.getId());
        assignment.setStatus("ASSIGNED");
        
        deliveryRepository.save(assignment);
    }

    public void updateDeliveryLocation(Long orderId, GeoJson location) {
        DeliveryAssignment assignment = deliveryRepository.findByOrderId(orderId);
        assignment.setCurrentLocation(location);
        assignment.setLastUpdated(LocalDateTime.now());
        deliveryRepository.save(assignment);
    }

    public void markDeliveryComplete(Long orderId) {
        DeliveryAssignment assignment = deliveryRepository.findByOrderId(orderId);
        assignment.setStatus("COMPLETED");
        assignment.setDeliveredAt(LocalDateTime.now());
        deliveryRepository.save(assignment);
        
        // Notify other services
        jmsTemplate.convertAndSend(AppConstants.DELIVERY_COMPLETED_QUEUE, orderId);
    }

    private DeliveryAgent findNearestAgent(String deliveryAddress) {
        // TODO: Implement logic to find nearest available delivery agent
        // Using geospatial queries in MongoDB
        return null;
    }
}