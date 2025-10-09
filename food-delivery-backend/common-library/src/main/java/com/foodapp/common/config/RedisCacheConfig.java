package com.foodapp.common.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
public class RedisCacheConfig {

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        
        // Menu items cache - longer duration as menu doesn't change frequently
        cacheConfigurations.put("menuItems", 
            createConfig(Duration.ofHours(24)));
        
        // Restaurant details cache
        cacheConfigurations.put("restaurants", 
            createConfig(Duration.ofHours(12)));
        
        // User profiles cache - shorter duration for frequently updated data
        cacheConfigurations.put("userProfiles", 
            createConfig(Duration.ofMinutes(30)));
        
        // Order status cache - very short duration for real-time updates
        cacheConfigurations.put("orderStatus", 
            createConfig(Duration.ofMinutes(1)));
        
        // AI recommendations cache
        cacheConfigurations.put("aiRecommendations", 
            createConfig(Duration.ofHours(1)));
        
        // Weather data cache
        cacheConfigurations.put("weatherData", 
            createConfig(Duration.ofHours(1)));

        return RedisCacheManager.builder(connectionFactory)
            .cacheDefaults(createConfig(Duration.ofMinutes(10)))
            .withInitialCacheConfigurations(cacheConfigurations)
            .build();
    }

    private RedisCacheConfiguration createConfig(Duration ttl) {
        return RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(ttl)
            .serializeValuesWith(RedisSerializationContext.SerializationPair
                .fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }
}