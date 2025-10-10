package com.foodapp.config.configuration;

import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
<<<<<<< HEAD
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
=======
>>>>>>> version1.4
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableConfigServer
<<<<<<< HEAD
@EnableWebSecurity
=======
>>>>>>> version1.4
public class ConfigServerConfiguration {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
<<<<<<< HEAD
        return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/actuator/**").permitAll()
                .anyRequest().authenticated())
            .httpBasic(basic -> {})
            .build();
=======
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/actuator/**").permitAll()
                .anyRequest().authenticated())
            .httpBasic(httpBasic -> {});
        
        return http.build();
>>>>>>> version1.4
    }
}