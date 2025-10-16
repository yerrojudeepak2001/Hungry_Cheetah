package com.foodapp.driver.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverRating {
    private String ratingId;
    private Long driverId;
    private String orderId;
    private Long customerId;
    private Integer rating; // 1-5
    private String feedback;
    private LocalDateTime ratingTime;
    private String category; // PUNCTUALITY, COMMUNICATION, PROFESSIONALISM
}