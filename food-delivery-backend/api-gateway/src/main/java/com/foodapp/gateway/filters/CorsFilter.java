package com.foodapp.gateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;

@Component
public class CorsFilter extends AbstractGatewayFilterFactory<CorsFilter.Config> {

    // Define allowed origins
    private static final List<String> ALLOWED_ORIGINS = Arrays.asList(
            "http://localhost:3000",
            "http://127.0.0.1:3000",
            "http://localhost:3001",
            "http://127.0.0.1:3001",
            "http://localhost:4200",
            "http://127.0.0.1:4200",
            "http://localhost:5173", // Vite default
            "http://127.0.0.1:5173",
            "http://localhost:5174",
            "http://127.0.0.1:5174",
            "http://localhost:8081",
            "http://127.0.0.1:8081");

    public CorsFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String origin = exchange.getRequest().getHeaders().getFirst("Origin");

            // Check if origin is allowed
            if (origin != null && (ALLOWED_ORIGINS.contains(origin)
                    || origin.matches("https://.*\\.(vercel|netlify|herokuapp)\\.app"))) {
                exchange.getResponse().getHeaders().add("Access-Control-Allow-Origin", origin);
                exchange.getResponse().getHeaders().add("Access-Control-Allow-Credentials", "true");
            }

            exchange.getResponse().getHeaders().add("Access-Control-Allow-Methods",
                    "GET, POST, PUT, DELETE, OPTIONS, PATCH, HEAD");
            exchange.getResponse().getHeaders().add("Access-Control-Allow-Headers",
                    "DNT,X-CustomHeader,Keep-Alive,User-Agent,X-Requested-With," +
                            "If-Modified-Since,Cache-Control,Content-Type,Content-Range,Range," +
                            "Authorization,Accept,Origin,X-Total-Count,X-Page-Count");
            exchange.getResponse().getHeaders().add("Access-Control-Expose-Headers",
                    "Authorization,Content-Type,X-Requested-With,Accept,X-Total-Count,X-Page-Count");

            if (exchange.getRequest().getMethod() == HttpMethod.OPTIONS) {
                exchange.getResponse().getHeaders().add("Access-Control-Max-Age", "3600");
                return exchange.getResponse().setComplete();
            }

            return chain.filter(exchange);
        };
    }

    public static class Config {
        // Configuration properties if needed
    }
}