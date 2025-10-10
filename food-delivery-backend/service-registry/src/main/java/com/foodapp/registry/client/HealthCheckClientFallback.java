package com.foodapp.registry.client;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
public class HealthCheckClientFallback implements HealthCheckClient {
    @Override
    public Map<String, Object> checkServiceHealth() {
        return Collections.singletonMap("status", "DOWN");
    }

    @Override
    public Map<String, Object> getServiceInfo() {
        return Collections.emptyMap();
    }

    @Override
    public void updateServiceStatus(Map<String, Object> status) {
        // Do nothing in fallback
    }
}