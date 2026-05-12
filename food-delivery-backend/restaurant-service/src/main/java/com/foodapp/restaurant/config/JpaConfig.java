package com.foodapp.restaurant.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.foodapp.restaurant.repository.jpa")
@EntityScan("com.foodapp.restaurant.model")
public class JpaConfig {
}
