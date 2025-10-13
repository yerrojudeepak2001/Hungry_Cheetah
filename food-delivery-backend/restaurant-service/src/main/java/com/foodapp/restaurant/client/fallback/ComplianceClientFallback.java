package com.foodapp.restaurant.client.fallback;

import com.foodapp.restaurant.client.ComplianceClient;
import com.foodapp.restaurant.dto.ComplianceCheck;
import com.foodapp.restaurant.dto.ComplianceResult;
import com.foodapp.restaurant.dto.ComplianceRequirement;
import com.foodapp.restaurant.dto.RegulationUpdate;
import com.foodapp.restaurant.dto.CertificateVerification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Collections;

@Component
public class ComplianceClientFallback implements ComplianceClient {

    @Override
    public ComplianceResult checkCompliance(ComplianceCheck check) {
        return ComplianceResult.builder()
                .passed(false)
                .build();
    }

    @Override
    public List<ComplianceRequirement> getComplianceRequirements(String restaurantId) {
        return Collections.emptyList();
    }

    @Override
    public List<RegulationUpdate> getRegulationUpdates(String region) {
        return Collections.emptyList();
    }

    @Override
    public boolean verifyCertificates(CertificateVerification verification) {
        return false;
    }
}