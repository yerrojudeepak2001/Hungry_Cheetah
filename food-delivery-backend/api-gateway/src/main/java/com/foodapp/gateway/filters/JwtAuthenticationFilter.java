package com.foodapp.gateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import reactor.core.publisher.Mono;
import com.foodapp.gateway.dto.ApiResponse;
import com.foodapp.gateway.model.TokenValidationResponse;

import java.util.Map;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final WebClient.Builder webClientBuilder;

    public JwtAuthenticationFilter(WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            String path = exchange.getRequest().getURI().getPath();
            logger.info("[JWT Filter] Incoming path: {}", path);

            // Skip auth for public endpoints
            if (path.contains("/api/auth/") || path.startsWith("/api/restaurant")) {
                logger.info("[JWT Filter] Skipping JWT validation for public endpoint: {}", path);
                return chain.filter(exchange);
            }

            HttpHeaders headers = exchange.getRequest().getHeaders();
            String authHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                logger.warn("[JWT Filter] Missing or invalid Authorization header");
                return onError(exchange, "Missing or invalid Authorization header", HttpStatus.UNAUTHORIZED);
            }

            String token = authHeader.substring(7);
            logger.info("[JWT Filter] Validating token with Auth Service");

            // Call Auth Service to validate token
            return webClientBuilder.build()
                    .post()
                    .uri("lb://AUTH-SERVICE/api/auth/validate-token")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(Map.of("token", token))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<ApiResponse<TokenValidationResponse>>() {})
                    .flatMap(apiResponse -> {
                        if (apiResponse != null && apiResponse.getData() != null && apiResponse.getData().isValid()) {
                            logger.info("[JWT Filter] Token validated successfully for user: {}", apiResponse.getData().getUsername());

                            // Forward the Authorization header to downstream services
                            ServerWebExchange mutatedExchange = exchange.mutate()
                                    .request(exchange.getRequest().mutate()
                                            .header(HttpHeaders.AUTHORIZATION, authHeader)
                                            .build())
                                    .build();

                            return chain.filter(mutatedExchange);
                        } else {
                            String reason = (apiResponse != null && apiResponse.getData() != null)
                                    ? apiResponse.getData().getMessage()
                                    : "Unknown reason";
                            logger.warn("[JWT Filter] Token invalid: {}", reason);
                            return onError(exchange, "Invalid token: " + reason, HttpStatus.UNAUTHORIZED);
                        }
                    })
                    .onErrorResume(ex -> {
                        logger.warn("[JWT Filter] Token validation failed: {}", ex.getMessage());
                        return onError(exchange, "Auth Service error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                    });
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus status) {
        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().add("Content-Type", "application/json");
        String errorJson = "{\"error\": \"" + message + "\"}";
        return exchange.getResponse().writeWith(
                Mono.just(exchange.getResponse().bufferFactory().wrap(errorJson.getBytes()))
        );
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
