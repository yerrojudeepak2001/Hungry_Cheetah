package com.foodapp.gateway.client;

import com.foodapp.gateway.dto.ServiceHealth;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.Map;

@Component
public class ServiceHealthClientFallback implements ServiceHealthClient {
    @Override
    public ServiceHealth checkHealth() {
        return new ServiceHealth("DOWN", Collections.emptyMap());
    }
    
    @Override
    public Map<String, Object> getMetrics() {
        return Collections.emptyMap();
    }
    
    @Override
    public Map<String, Object> getServiceInfo() {
        return Collections.emptyMap();
    }
}