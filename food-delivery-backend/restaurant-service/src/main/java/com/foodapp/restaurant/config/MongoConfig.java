package com.foodapp.restaurant.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.foodapp.restaurant.repository.mongo")
public class MongoConfig {
}
