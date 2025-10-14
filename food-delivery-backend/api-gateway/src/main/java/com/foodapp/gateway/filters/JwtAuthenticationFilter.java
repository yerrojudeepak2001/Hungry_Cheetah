package com.foodapp.gateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {
    private final WebClient.Builder webClientBuilder;
    
    public JwtAuthenticationFilter(WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // Skip validation for auth service paths
            if (exchange.getRequest().getURI().getPath().contains("/api/auth/")) {
                return chain.filter(exchange);
            }
            
            // Skip validation for non-secured paths if configured
            if (config.isSecured()) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    return onError(exchange, "Missing authorization header", HttpStatus.UNAUTHORIZED);
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    return onError(exchange, "Invalid authorization header format", HttpStatus.UNAUTHORIZED);
                }
                
                String token = authHeader.substring(7);
                
                // Validate token with auth service
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