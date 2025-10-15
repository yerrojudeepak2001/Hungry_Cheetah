package com.foodapp.delivery.service;

import com.foodapp.delivery.model.Delivery;
import com.foodapp.delivery.model.DeliveryPartner;
import com.foodapp.delivery.model.DeliveryStatus;
import com.foodapp.delivery.repository.DeliveryRepository;
import com.foodapp.delivery.repository.DeliveryPartnerRepository;
import com.foodapp.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final DeliveryPartnerRepository partnerRepository;
    private final RouteOptimizationService routeService;
    private final NotificationService notificationService;

    public DeliveryService(DeliveryRepository deliveryRepository,
                          DeliveryPartnerRepository partnerRepository,
                          RouteOptimizationService routeService,
                          NotificationService notificationService) {
        this.deliveryRepository = deliveryRepository;
        this.partnerRepository = partnerRepository;
        this.routeService = routeService;
        this.notificationService = notificationService;
    }

    @Transactional
    public Delivery createDelivery(Delivery delivery) {
        // Assign delivery partner
        DeliveryPartner partner = findOptimalDeliveryPartner(delivery);
        delivery.setDeliveryPartnerId(partner.getId());
        
        // Calculate ETA
        Integer estimatedDuration = routeService.calculateEstimatedDuration(
            delivery.getPickupLocation(),
            delivery.getDeliveryLocation()
        );
        delivery.setEstimatedDuration(estimatedDuration);
        delivery.setEstimatedDeliveryTime(LocalDateTime.now().plusMinutes(estimatedDuration));
        
        // Save delivery
        Delivery savedDelivery = deliveryRepository.save(delivery);
        
        // Update partner status
        updatePartnerStatus(partner, savedDelivery);
        
        // Send notifications
        notificationService.notifyPartner(partner.getId(), savedDelivery);
        notificationService.notifyCustomer(savedDelivery.getOrderId(), "Delivery assigned");

        return savedDelivery;
    }

    @Transactional
    public Delivery updateDeliveryStatus(Long deliveryId, DeliveryStatus status) {
        Delivery delivery = getDelivery(deliveryId);
        delivery.setStatus(status);
        
        // Update timestamps based on status
        updateDeliveryTimestamps(delivery, status);
        
        // Save and notify
        Delivery updatedDelivery = deliveryRepository.save(delivery);
        notificationService.notifyCustomer(delivery.getOrderId(), "Delivery status: " + status);
        
        // If completed, update partner status
        if (status == DeliveryStatus.COMPLETED) {
            completeDelivery(delivery);
        }
        
        return updatedDelivery;
    }

    public Delivery getDelivery(Long deliveryId) {
        return deliveryRepository.findById(deliveryId)
            .orElseThrow(() -> new ResourceNotFoundException("Delivery not found with id: " + deliveryId));
    }

    private DeliveryPartner findOptimalDeliveryPartner(Delivery delivery) {
        // Implement partner selection logic based on:
        // - Current location
        // - Availability
        // - Rating
        // - Active deliveries
        // - Service area
        return null; // Placeholder
    }

    private void updatePartnerStatus(DeliveryPartner partner, Delivery delivery) {
        partner.setActiveDeliveries(partner.getActiveDeliveries() + 1);
        if (partner.getActiveDeliveries() >= partner.getMaxSimultaneousDeliveries()) {
            partner.setIsAvailable(false);
        }
        partnerRepository.save(partner);
    }

    private void updateDeliveryTimestamps(Delivery delivery, DeliveryStatus status) {
        switch (status) {
            case PICKED_UP:
                delivery.setPickupTime(LocalDateTime.now());
                break;
            case COMPLETED:
                delivery.setDeliveryTime(LocalDateTime.now());
                break;
        }
    }

    private void completeDelivery(Delivery delivery) {
        DeliveryPartner partner = partnerRepository.findById(delivery.getDeliveryPartnerId())
            .orElseThrow(() -> new ResourceNotFoundException("Partner not found"));
            
        partner.setActiveDeliveries(partner.getActiveDeliveries() - 1);
        partner.setTotalDeliveries(partner.getTotalDeliveries() + 1);
        partner.setIsAvailable(true);
        
        partnerRepository.save(partner);
    }
}