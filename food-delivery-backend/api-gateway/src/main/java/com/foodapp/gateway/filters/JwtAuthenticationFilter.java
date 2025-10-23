package com.foodapp.gateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final WebClient.Builder webClientBuilder;

    // Inject JWT secret from application.yml
    @Value("${spring.jwt.secret:34510220d4bf1609c88acb0256b07a2e4d5e6f7a8b9c0d1e2f3a4b5c6d7e8f9a0b}")
    private String jwtSecret;

    public JwtAuthenticationFilter(WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getURI().getPath();
            logger.info("[JWT Filter] Incoming path: {}", path);
            if (path.contains("/api/auth/")) {
                logger.info("[JWT Filter] Skipping JWT validation for /api/auth/**");
                return chain.filter(exchange);
            }
            if (path.startsWith("/api/restaurant") || path.startsWith("/api/restaurants")) {
                logger.info("[JWT Filter] Skipping JWT validation for public restaurant endpoint: {}", path);
                return chain.filter(exchange);
            }
            if (config.isSecured()) {
                logger.info("[JWT Filter] Secured route, checking Authorization header");
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    logger.warn("[JWT Filter] Missing authorization header");
                    return onError(exchange, "Missing authorization header", HttpStatus.UNAUTHORIZED);
                }
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    logger.warn("[JWT Filter] Invalid authorization header format");
                    return onError(exchange, "Invalid authorization header format", HttpStatus.UNAUTHORIZED);
                }
                String token = authHeader.substring(7);
                logger.info("[JWT Filter] Validating token with auth service");
                return webClientBuilder.build()
                        .post()
                        .uri("lb://AUTH-SERVICE/api/auth/validate-token")
                        .contentType(MediaType.TEXT_PLAIN)
                        .bodyValue(token)
                        .retrieve()
                        .onStatus(status -> status.is4xxClientError(), response -> 
                            Mono.error(new RuntimeException("Invalid token")))
                        .onStatus(status -> status.is5xxServerError(), response -> 
                            Mono.error(new RuntimeException("Auth service error")))
                        .bodyToMono(Void.class)
                        .then(chain.filter(exchange));
            }
            logger.info("[JWT Filter] Not secured, allowing request");
            return chain.filter(exchange);
        };
    }
    
    private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus httpStatus) {
        exchange.getResponse().setStatusCode(httpStatus);
        exchange.getResponse().getHeaders().add("Content-Type", "application/json");
        String errorMessage = "{\"error\": \"" + message + "\"}";
        return exchange.getResponse().writeWith(
                Mono.just(exchange.getResponse().bufferFactory().wrap(errorMessage.getBytes())));
    }

    public static class Config {
        private boolean secured = true;
        
        public boolean isSecured() {
            return secured;
        }
        
        public void setSecured(boolean secured) {
            this.secured = secured;
        }
    }
}