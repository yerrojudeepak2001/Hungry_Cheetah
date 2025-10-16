package com.foodapp.driver.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationUpdate {
    private Long driverId;
    private Double latitude;
    private Double longitude;
    private LocalDateTime timestamp;
    private Double speed; // km/h
    private Double heading; // degrees
    private Double accuracy; // meters
    private String source; // GPS, NETWORK, PASSIVE
}