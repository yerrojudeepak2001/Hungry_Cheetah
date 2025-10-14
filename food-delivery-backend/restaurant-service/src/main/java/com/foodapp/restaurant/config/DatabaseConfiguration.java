package com.foodapp.restaurant.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.foodapp.restaurant.repository")
@EnableMongoRepositories(basePackages = "com.foodapp.restaurant.repository.mongo")
public class DatabaseConfiguration {
    // This configuration enables both JPA and MongoDB repositories
    // JPA repositories are in: com.foodapp.restaurant.repository
    // MongoDB repositories are in: com.foodapp.restaurant.repository.mongo
}
