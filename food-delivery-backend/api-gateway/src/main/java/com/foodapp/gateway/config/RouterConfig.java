package com.foodapp.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouterConfig {
    
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
            // Auth Service - No JWT filter needed
            .route("auth-service", r -> r
                .path("/api/auth/**")
                .uri("lb://AUTH-SERVICE"))
            
            // User Service
            .route("user-service", r -> r
                .path("/api/user/**", "/api/users/**")
                .uri("lb://USER-SERVICE"))
                
            // Restaurant Service
            .route("restaurant-service", r -> r
                .path("/api/restaurant/**", "/api/restaurants/**")
                .uri("lb://RESTAURANT-SERVICE"))
                
            // Order Service
            .route("order-service", r -> r
                .path("/api/order/**", "/api/orders/**")
                .uri("lb://ORDER-SERVICE"))
                
            // Payment Service
            .route("payment-service", r -> r
                .path("/api/payment/**", "/api/payments/**")
                .uri("lb://PAYMENT-SERVICE"))
                
            // Delivery Service
            .route("delivery-service", r -> r
                .path("/api/delivery/**", "/api/deliveries/**")
                .uri("lb://DELIVERY-SERVICE"))
                
            // AI Service
            .route("ai-service", r -> r
                .path("/api/ai/**")
                .uri("lb://AI-SERVICE"))
                
            .build();
    }
}