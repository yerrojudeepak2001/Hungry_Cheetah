package com.foodapp.gateway.client;

import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.Map;

@Component
public class ServiceHealthClientFallback implements ServiceHealthClient {
    @Override
    public Map<String, Object> checkHealth() {
        return Collections.singletonMap("status", "DOWN");
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