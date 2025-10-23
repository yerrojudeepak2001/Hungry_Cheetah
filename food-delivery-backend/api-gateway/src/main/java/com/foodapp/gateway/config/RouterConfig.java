package com.foodapp.gateway.config;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.foodapp.gateway.filters.JwtAuthenticationFilter;

@Configuration
public class RouterConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public RouterConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

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
                .filters(f -> f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config())))
                .uri("lb://USER-SERVICE"))
            // Restaurant Service
            .route("restaurant-service", r -> r
                .path("/api/restaurant/**", "/api/restaurants/**")
                .filters(f -> f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config())))
                .uri("lb://RESTAURANT-SERVICE"))
            // Order Service
            .route("order-service", r -> r
                .path("/api/order/**", "/api/orders/**")
                .filters(f -> f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config())))
                .uri("lb://ORDER-SERVICE"))
            // Payment Service
            .route("payment-service", r -> r
                .path("/api/payment/**", "/api/payments/**")
                .filters(f -> f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config())))
                .uri("lb://PAYMENT-SERVICE"))
            // Delivery Service
            .route("delivery-service", r -> r
                .path("/api/delivery/**", "/api/deliveries/**")
                .filters(f -> f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config())))
                .uri("lb://DELIVERY-SERVICE"))
            // Cart Service
            .route("cart-service", r -> r
                .path("/api/cart/**")
                .filters(f -> f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config())))
                .uri("lb://CART-SERVICE"))
            // AI Service
            .route("ai-service", r -> r
                .path("/api/ai/**")
                .filters(f -> f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config())))
                .uri("lb://AI-SERVICE"))
            .build();
    }
}