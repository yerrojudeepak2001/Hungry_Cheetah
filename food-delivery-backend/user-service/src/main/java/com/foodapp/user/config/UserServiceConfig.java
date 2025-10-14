package com.foodapp.user.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.foodapp.user.client")
public class UserServiceConfig {
    // This class is used primarily for enabling Feign clients
    // Other configuration beans are handled in SecurityConfig
}