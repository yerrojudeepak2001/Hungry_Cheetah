package com.foodapp.admin.client;

import com.foodapp.admin.dto.*;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Collections;

@Component
public class QualityClientFallback implements QualityAdminClient {
    
    @Override
    public List<QualityReport> getQualityReports(LocalDateTime startDate, LocalDateTime endDate) {
        return Collections.emptyList();
    }
    
    @Override
    public List<QualityViolation> getQualityViolations(String severity, String status) {
        return Collections.emptyList();
    }
    
    @Override
    public void updateViolationStatus(String violationId, ViolationStatusUpdate update) {
        // Fallback - do nothing
    }
    
    @Override
    public Map<String, ComplianceStats> getComplianceStatistics(String region) {
        return Collections.emptyMap();
    }
}