package com.foodapp.delivery.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Location {
    private Double latitude;
    private Double longitude;
    private String address;
    private String landmark;
    private String pinCode;
}