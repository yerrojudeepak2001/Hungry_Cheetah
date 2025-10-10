package com.foodapp.restaurant.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class POSIntegrationStatus {
    private String posSystemId;
    private String status;
    private LocalDateTime lastSync;
    private String errorMessage;
}