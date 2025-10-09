package com.foodapp.delivery.repository;

import com.foodapp.delivery.model.DeliveryPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DeliveryPartnerRepository extends JpaRepository<DeliveryPartner, Long> {
    List<DeliveryPartner> findByIsAvailableAndIsOnline(Boolean isAvailable, Boolean isOnline);
    List<DeliveryPartner> findByServiceAreasContaining(String area);
    List<DeliveryPartner> findByPreferredZone(String zone);
    List<DeliveryPartner> findByActiveDeliveriesLessThan(Integer maxDeliveries);
}