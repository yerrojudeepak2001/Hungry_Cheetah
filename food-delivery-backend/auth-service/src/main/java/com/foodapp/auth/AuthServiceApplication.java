package com.foodapp.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ComponentScan.Filter;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(
    basePackages = {"com.foodapp.auth"},
    excludeFilters = {
        @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = com.foodapp.common.security.JwtTokenProvider.class)
    }
)
public class AuthServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }
}