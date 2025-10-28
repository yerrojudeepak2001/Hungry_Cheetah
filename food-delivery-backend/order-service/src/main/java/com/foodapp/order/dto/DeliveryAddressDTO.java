package com.foodapp.order.dto;

import lombok.Data;

@Data
public class DeliveryAddressDTO {
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String apartmentNumber;
    private String landmark;
}