package com.foodapp.config.client;

import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.Map;

@Component
public class ConfigRefreshClientFallback implements ConfigRefreshClient {
    @Override
    public void refreshConfiguration() {
        // Do nothing in fallback
    }

    @Override
    public Map<String, Object> getEnvironment() {
        return Collections.emptyMap();
    }

    @Override
    public void refreshConfigurationBus() {
        // Do nothing in fallback
    }
}