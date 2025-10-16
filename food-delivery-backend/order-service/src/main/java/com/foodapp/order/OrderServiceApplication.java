package com.foodapp.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
// import org.springframework.jms.annotation.EnableJms; // Temporarily disabled for testing

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
// @EnableJms // Temporarily disabled for testing
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}