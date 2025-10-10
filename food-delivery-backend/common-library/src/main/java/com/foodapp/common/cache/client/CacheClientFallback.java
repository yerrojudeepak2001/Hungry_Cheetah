package com.foodapp.common.cache.client;

import com.foodapp.common.cache.dto.CacheOperation;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class CacheClientFallback implements CacheClient {
    
    @Override
    public Object getCachedValue(String key) {
        return null; // Default fallback returning null
    }
    
    @Override
    public void setCachedValue(String key, CacheOperation operation) {
        // No-op fallback
    }
    
    @Override
    public void invalidateCache(String key) {
        // No-op fallback
    }
    
    @Override
    public void invalidateByPattern(String pattern) {
        // No-op fallback
    }
    
    @Override
    public Map<String, Object> getCacheStatistics() {
        return new HashMap<>(); // Return empty statistics
    }
}