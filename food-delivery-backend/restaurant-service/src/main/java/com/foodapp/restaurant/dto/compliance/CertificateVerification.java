package com.foodapp.restaurant.dto.compliance;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificateVerification {
    private Long restaurantId;
    private String certificateType;
    private String status;
    private LocalDateTime expiryDate;
}