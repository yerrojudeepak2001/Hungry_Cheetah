package com.foodapp.restaurant.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplianceCheck {
    private Long restaurantId;
    private List<String> checkItems;
    private LocalDateTime checkDate;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplianceResult {
    private Long restaurantId;
    private boolean passed;
    private List<String> violations;
    private LocalDateTime checkDate;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplianceRequirement {
    private String code;
    private String description;
    private String category;
    private LocalDateTime effectiveDate;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegulationUpdate {
    private String code;
    private String description;
    private String changeType;
    private LocalDateTime updateDate;
}

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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KitchenOrder {
    private Long orderId;
    private List<String> items;
    private String specialInstructions;
    private LocalDateTime requiredBy;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KitchenStatus {
    private Long restaurantId;
    private String status;
    private int queueLength;
    private int estimatedWaitTime;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KitchenStatusUpdate {
    private Long restaurantId;
    private String newStatus;
    private String reason;
    private LocalDateTime timestamp;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QualityCheck {
    private Long restaurantId;
    private String checkType;
    private String result;
    private List<String> findings;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SafetyReport {
    private Long restaurantId;
    private LocalDateTime reportDate;
    private List<String> observations;
    private String overallRating;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QualityIncident {
    private Long restaurantId;
    private String incidentType;
    private String description;
    private LocalDateTime reportedAt;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QualityStandard {
    private String code;
    private String description;
    private String category;
    private LocalDateTime effectiveDate;
}