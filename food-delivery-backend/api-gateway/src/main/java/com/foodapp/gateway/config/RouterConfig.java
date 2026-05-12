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
                // Auth Service - No JWT filter needed for login/register
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

//                // AI Service
//                .route("ai-service", r -> r
//                        .path("/api/ai/**")
//                        .filters(f -> f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config())))
//                        .uri("lb://AI-SERVICE"))
                // In your Gateway configuration class
                .route("ai-service", r -> r
                        .path("/api/ai/**")
                        .filters(f -> f
                                // Validate JWT in the gateway
                                .filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config()))

                                // Remove unnecessary headers (optional)
                                .removeRequestHeader("Cookie")

                                // Rewrite path if needed (optional)
                                .rewritePath("/api/ai/(?<segment>.*)", "/api/ai/${segment}")

                                // Preserve host header
                                .preserveHostHeader()

                                // Forward Authorization header to AI service
                                .filter((exchange, chain) -> {
                                    String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
                                    if (authHeader != null) {
                                        exchange.getRequest()
                                                .mutate()
                                                .header("Authorization", authHeader)
                                                .build();
                                    }
                                    return chain.filter(exchange);
                                })
                        )
                        .uri("lb://AI-SERVICE") // Load-balanced service name registered in Eureka
                )



                // Search Service
                .route("search-service", r -> r
                        .path("/api/search/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config())))
                        .uri("lb://SEARCH-SERVICE"))

                // Notification Service
                .route("notification-service", r -> r
                        .path("/api/notification/**", "/api/notifications/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config())))
                        .uri("lb://NOTIFICATION-SERVICE"))

                // Analytics Service
                .route("analytics-service", r -> r
                        .path("/api/analytics/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config())))
                        .uri("lb://ANALYTICS-SERVICE"))

                // Tracking Service
                .route("tracking-service", r -> r
                        .path("/api/tracking/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config())))
                        .uri("lb://TRACKING-SERVICE"))

                // Driver Service
                .route("driver-service", r -> r
                        .path("/api/driver/**", "/api/drivers/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config())))
                        .uri("lb://DRIVER-SERVICE"))

                // Recommendation Service
                .route("recommendation-service", r -> r
                        .path("/api/recommendation/**", "/api/recommendations/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config())))
                        .uri("lb://RECOMMENDATION-SERVICE"))

                // Admin Service
                .route("admin-service", r -> r
                        .path("/api/admin/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config())))
                        .uri("lb://ADMIN-SERVICE"))

                // Health check endpoints (no auth required)
                .route("health-check", r -> r
                        .path("/actuator/**", "/health/**")
                        .uri("lb://SERVICE-REGISTRY"))

                .build();
    }
}