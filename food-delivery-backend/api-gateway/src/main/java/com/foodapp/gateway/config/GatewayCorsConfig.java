package com.foodapp.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@Configuration
public class GatewayCorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Allow your frontend origins
        // Use allowedOriginPatterns to avoid duplicate Access-Control-Allow-Origin
        // values
        configuration.setAllowedOriginPatterns(Arrays.asList(
                "http://localhost:3000", // React default
                "http://127.0.0.1:3000",
                "http://localhost:3001", // React alternative
                "http://127.0.0.1:3001",
                "http://localhost:4200", // Angular default
                "http://127.0.0.1:4200",
                "http://localhost:5173", // Vite default
                "http://127.0.0.1:5173",
                "http://localhost:5174", // Vite alternative
                "http://127.0.0.1:5174",
                "http://localhost:8081", // Alternative port
                "http://127.0.0.1:8081",
                "https://*.vercel.app", // Vercel deployments
                "https://*.netlify.app", // Netlify deployments
                "https://*.herokuapp.com", // Heroku deployments
                "https://yourdomain.com", // Your production domain
                "https://www.yourdomain.com"));

        // Allow all common HTTP methods
        configuration.setAllowedMethods(Arrays.asList(
                "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD"));

        // Allow all headers
        configuration.setAllowedHeaders(Arrays.asList("*"));

        // Allow credentials for authentication
        configuration.setAllowCredentials(true);

        // Cache preflight response for 1 hour
        configuration.setMaxAge(3600L);

        // Expose headers that frontend might need
        configuration.setExposedHeaders(Arrays.asList(
                "Authorization",
                "Content-Type",
                "X-Requested-With",
                "Accept",
                "X-Total-Count",
                "X-Page-Count",
                "Access-Control-Allow-Origin",
                "Access-Control-Allow-Credentials"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return new CorsWebFilter(source);
    }
}