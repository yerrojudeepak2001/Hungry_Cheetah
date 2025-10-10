package com.foodapp.registry.client;

import com.foodapp.registry.dto.ServiceHealth;
import com.foodapp.registry.dto.ServiceStatusUpdate;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
public class HealthCheckClientFallback implements HealthCheckClient {
    @Override
    public ServiceHealth checkServiceHealth() {
        return new ServiceHealth("DOWN", Collections.emptyMap());
    }

    @Override
    public Map<String, Object> getServiceInfo() {
        return Collections.emptyMap();
    }

    @Override
    public void updateServiceStatus(ServiceStatusUpdate status) {
        // Do nothing in fallback
    }
}