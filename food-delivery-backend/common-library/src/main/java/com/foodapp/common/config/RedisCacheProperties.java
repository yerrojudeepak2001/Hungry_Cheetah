package com.foodapp.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "redis.cache")
public class RedisCacheProperties {
    private String host = "localhost";
    private int port = 6379;
    private String password;
    private int database = 0;
    private long defaultTtl = 3600; // 1 hour in seconds
    
    private CacheTtl ttl = new CacheTtl();
    
    @Data
    public static class CacheTtl {
        private long user = 3600; // 1 hour
        private long restaurant = 3600;
        private long menu = 1800; // 30 minutes
        private long order = 7200; // 2 hours
        private long payment = 86400; // 24 hours
        private long delivery = 3600;
        private long promotion = 1800;
        private long notification = 86400;
        private long review = 3600;
    }
}