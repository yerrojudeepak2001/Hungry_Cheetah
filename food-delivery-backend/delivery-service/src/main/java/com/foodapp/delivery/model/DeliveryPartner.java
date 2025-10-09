package com.foodapp.delivery.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryPartner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String phone;
    private String email;
    private String vehicleNumber;
    private String vehicleType;
    private String licenseNumber;
    
    @Embedded
    private Location currentLocation;
    
    private Boolean isAvailable;
    private Boolean isOnline;
    private LocalDateTime lastOnlineTime;
    private Double rating;
    private Integer totalDeliveries;
    private Integer activeDeliveries;
    
    @OneToMany(mappedBy = "deliveryPartnerId")
    private List<Delivery> deliveries;
    
    @ElementCollection
    private List<String> serviceAreas;
    
    private String preferredZone;
    private Integer maxSimultaneousDeliveries;
    private Double averageDeliveryTime;
    private Double successRate;
}