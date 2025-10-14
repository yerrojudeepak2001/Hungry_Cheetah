package com.foodapp.restaurant.client.fallback;

import com.foodapp.restaurant.client.QualityClient;
import com.foodapp.restaurant.dto.QualityAudit;
import com.foodapp.restaurant.dto.quality.*;
import com.foodapp.restaurant.dto.ImprovementPlan;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Collections;

@Component
public class QualityClientFallback implements QualityClient {

    @Override
    public void submitQualityAudit(String restaurantId, QualityAudit audit) {
        // Fallback: Do nothing
    }

    @Override
    public void submitQualityCheck(QualityCheck check) {

    }

    @Override
    public List<SafetyReport> getRestaurantReports(String restaurantId) {
        return null;
    }

    @Override
    public void reportQualityIncident(QualityIncident incident) {

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