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
public class CertificateVerification {
    private String certificateId;
    private String certificateType;
    private String status;
    private LocalDateTime verificationDate;
    private LocalDateTime expiryDate;
}