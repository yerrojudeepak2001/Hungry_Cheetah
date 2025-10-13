package com.foodapp.admin.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplianceStats {
    private long totalViolations;
    private long resolvedViolations;
    private long pendingViolations;
    private double complianceRate;
    private long criticalViolations;
}