package com.foodapp.tracking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderTimelineEvent {
    private String orderId;
    private String event;
    private long timestamp;
    private String description;
    private String location;
}