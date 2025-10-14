package com.foodapp.restaurant.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.foodapp.restaurant.repository")
@EnableTransactionManagement
public class DatabaseConfig {
    // Additional database configuration if needed
}