package com.foodapp.gateway.config;

import com.foodapp.gateway.filters.JwtAuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class RouterConfig {
    
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    
    public RouterConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
    
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
            // Auth Service - No JWT filter
            .route("auth-service", r -> r
                .path("/api/auth/**")
                .uri("lb://AUTH-SERVICE"))
            
            // User Service - with JWT filter
            .route("user-service", r -> r
                .path("/api/user/**")
                .filters(f -> f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config())))
                .uri("lb://USER-SERVICE"))
                
            // Restaurant Service - with JWT filter
            .route("restaurant-service", r -> r
                .path("/api/restaurant/**")
                .filters(f -> f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config())))
                .uri("lb://RESTAURANT-SERVICE"))
                
            // Order Service - with JWT filter
            .route("order-service", r -> r
                .path("/api/order/**")
                .filters(f -> f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config())))
                .uri("lb://ORDER-SERVICE"))
                
            // Payment Service - with JWT filter
            .route("payment-service", r -> r
                .path("/api/payment/**")
                .filters(f -> f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config())))
                .uri("lb://PAYMENT-SERVICE"))
                
            // Delivery Service - with JWT filter
            .route("delivery-service", r -> r
                .path("/api/delivery/**")
                .filters(f -> f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config())))
                .uri("lb://DELIVERY-SERVICE"))
                
            // AI Service - with JWT filter
            .route("ai-service", r -> r
                .path("/api/ai/**")
                .filters(f -> f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config())))
                .uri("lb://AI-SERVICE"))
                
            .build();
    }
    
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}