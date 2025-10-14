package com.foodapp.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryTrackingDto {
    private String deliveryId;
    private String orderId;
    private String userId;
    private String driverId;
    private String driverName;
    private String driverPhone;
    private String vehicleInfo;
    
    private String status; // ASSIGNED, PICKED_UP, IN_TRANSIT, DELIVERED, CANCELLED
    private String statusDescription;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime estimatedDeliveryTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime actualDeliveryTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime pickedUpAt;
    
    // Current location of delivery
    private Double currentLatitude;
    private Double currentLongitude;
    
    // Delivery address
    private String deliveryAddress;
    private Double deliveryLatitude;
    private Double deliveryLongitude;
    
    // Restaurant location
    private String restaurantAddress;
    private Double restaurantLatitude;
    private Double restaurantLongitude;
    
    private Integer estimatedTimeToDelivery; // Minutes
    private Double distanceToDelivery; // Kilometers
    
    private String deliveryNotes;
    private String specialInstructions;
    
    // Rating and feedback
    private Integer rating;
    private String feedback;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}