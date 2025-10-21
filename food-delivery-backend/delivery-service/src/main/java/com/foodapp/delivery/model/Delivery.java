package com.foodapp.delivery.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
    @AttributeOverrides({
        @AttributeOverride(name = "latitude", column = @Column(name = "current_latitude")),
        @AttributeOverride(name = "longitude", column = @Column(name = "current_longitude")),
        @AttributeOverride(name = "address", column = @Column(name = "current_address")),
        @AttributeOverride(name = "landmark", column = @Column(name = "current_landmark")),
        @AttributeOverride(name = "pinCode", column = @Column(name = "current_pin_code"))
    })
    private Location currentLocation;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "latitude", column = @Column(name = "pickup_latitude")),
        @AttributeOverride(name = "longitude", column = @Column(name = "pickup_longitude")),
        @AttributeOverride(name = "address", column = @Column(name = "pickup_address")),
        @AttributeOverride(name = "landmark", column = @Column(name = "pickup_landmark")),
        @AttributeOverride(name = "pinCode", column = @Column(name = "pickup_pin_code"))
    })
    private Location pickupLocation;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "latitude", column = @Column(name = "delivery_latitude")),
        @AttributeOverride(name = "longitude", column = @Column(name = "delivery_longitude")),
        @AttributeOverride(name = "address", column = @Column(name = "delivery_address")),
        @AttributeOverride(name = "landmark", column = @Column(name = "delivery_landmark")),
        @AttributeOverride(name = "pinCode", column = @Column(name = "delivery_pin_code"))
    })
    private Location deliveryLocation;
    
    private String deliveryInstructions;
    private Boolean contactlessDelivery;
    private String proofOfDelivery;
    private String deliveryNotes;
    private Double distance;
    private Integer estimatedDuration;
    private String trackingId;
    private Boolean isLive;
    
    // Google Maps integration fields
    private Integer distanceMeters;
    @Column(columnDefinition = "TEXT")
    private String routePolyline;
    private String trafficCondition;
    
    // Helper methods for Google Maps integration
    public Double getPickupLatitude() {
        return pickupLocation != null ? pickupLocation.getLatitude() : null;
    }
    
    public Double getPickupLongitude() {
        return pickupLocation != null ? pickupLocation.getLongitude() : null;
    }
    
    public Double getDeliveryLatitude() {
        return deliveryLocation != null ? deliveryLocation.getLatitude() : null;
    }
    
    public Double getDeliveryLongitude() {
        return deliveryLocation != null ? deliveryLocation.getLongitude() : null;
    }
}