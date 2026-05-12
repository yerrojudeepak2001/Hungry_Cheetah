package com.foodapp.driver.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryIssue {
    private String issueId;
    private String orderId;
    private Long driverId;
    private String issueType; // CUSTOMER_UNAVAILABLE, WRONG_ADDRESS, DAMAGED_FOOD, PAYMENT_ISSUE
    private String description;
    private LocalDateTime reportedTime;
    private String resolution;
    private String status; // OPEN, IN_PROGRESS, RESOLVED
    private String photoUrl;
}