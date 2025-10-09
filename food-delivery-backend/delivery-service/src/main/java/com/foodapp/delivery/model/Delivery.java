package com.foodapp.delivery.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long orderId;
    private Long deliveryPartnerId;
    private DeliveryStatus status;
    private LocalDateTime pickupTime;
    private LocalDateTime deliveryTime;
    private LocalDateTime estimatedDeliveryTime;
    
    @Embedded
    private Location currentLocation;
    
    @Embedded
    private Location pickupLocation;
    
    @Embedded
    private Location deliveryLocation;
    
    private String deliveryInstructions;
    private Boolean contactlessDelivery;
    private String proofOfDelivery;
    private String deliveryNotes;
    private Double distance;
    private Integer estimatedDuration;
    private String trackingId;
    private Boolean isLive;
}

@Embeddable
@Data
class Location {
    private Double latitude;
    private Double longitude;
    private String address;
    private String landmark;
    private String pinCode;
}