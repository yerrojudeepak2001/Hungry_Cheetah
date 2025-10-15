package com.foodapp.delivery.service;

import com.foodapp.common.constants.AppConstants;
import com.foodapp.common.dto.OrderDTO;
import com.foodapp.delivery.model.Delivery;
import com.foodapp.delivery.model.DeliveryStatus;
import com.foodapp.delivery.model.Location;
import com.foodapp.delivery.repository.DeliveryRepository;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
        // For now, hardcode a delivery partner ID (replace with actual logic)
        Long deliveryPartnerId = 1L;

        // Create a new Delivery entity
        Delivery delivery = new Delivery();
        delivery.setOrderId(order.getId());
        delivery.setDeliveryPartnerId(deliveryPartnerId);
        delivery.setStatus(DeliveryStatus.ASSIGNED);  // Make sure DeliveryStatus enum exists
        delivery.setPickupTime(LocalDateTime.now());

        // Optionally, set current location if available
        delivery.setCurrentLocation(new Location());

        deliveryRepository.save(delivery);
    }

    public void updateDeliveryLocation(Long orderId, double latitude, double longitude, String address, String landmark, String pinCode) {
        List<Delivery> deliveries = deliveryRepository.findByOrderId(orderId);
        for (Delivery delivery : deliveries) {
            delivery.setCurrentLocation(new Location(latitude, longitude, address, landmark, pinCode));
            delivery.setDeliveryTime(LocalDateTime.now());
            deliveryRepository.save(delivery);
        }
    }

    public void markDeliveryComplete(Long orderId) {
        List<Delivery> deliveries = deliveryRepository.findByOrderId(orderId);
        for (Delivery delivery : deliveries) {
            delivery.setStatus(DeliveryStatus.COMPLETED);
            delivery.setDeliveryTime(LocalDateTime.now());
            deliveryRepository.save(delivery);

            // Notify other services
            jmsTemplate.convertAndSend(AppConstants.DELIVERY_COMPLETED_QUEUE, orderId);
        }
    }

    // Placeholder method for assigning delivery partners
    private Long findNearestPartnerId(String deliveryAddress) {
        // TODO: Implement logic to find nearest available delivery partner
        return 1L;
    }
}
