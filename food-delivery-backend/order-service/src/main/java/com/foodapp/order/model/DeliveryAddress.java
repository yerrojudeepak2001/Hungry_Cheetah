package com.foodapp.order.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.persistence.Embeddable;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryAddress {
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private String apartmentNumber;
    private String deliveryInstructions;
    private Double latitude;
    private Double longitude;
}