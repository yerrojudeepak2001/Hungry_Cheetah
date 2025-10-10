package com.foodapp.restaurant.client.fallback;

import com.foodapp.restaurant.client.QualityServiceClient;
import com.foodapp.restaurant.dto.QualityAudit;
import com.foodapp.restaurant.dto.quality.QualityStandard;
import com.foodapp.restaurant.dto.quality.QualityViolation;
import com.foodapp.restaurant.dto.ImprovementPlan;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Collections;

@Component
public class QualityClientFallback implements QualityServiceClient {

    @Override
    public void submitQualityAudit(String restaurantId, QualityAudit audit) {
        // Fallback: Do nothing
    }

    @Override
    public List<QualityStandard> getQualityStandards(String restaurantId) {
        return Collections.emptyList();
    }

    @Override
    public List<QualityViolation> getQualityViolations(String restaurantId, LocalDateTime startDate, LocalDateTime endDate) {
        return Collections.emptyList();
    }

    @Override
    public void submitImprovementPlan(String restaurantId, ImprovementPlan plan) {
        // Fallback: Do nothing
    }
}