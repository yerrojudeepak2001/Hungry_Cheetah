package com.foodapp.delivery.repository;

import com.foodapp.delivery.model.Delivery;
import com.foodapp.delivery.model.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findByDeliveryPartnerId(Long partnerId);
    List<Delivery> findByOrderId(Long orderId);
    List<Delivery> findByStatus(DeliveryStatus status);
    List<Delivery> findByDeliveryPartnerIdAndStatus(Long partnerId, DeliveryStatus status);
}