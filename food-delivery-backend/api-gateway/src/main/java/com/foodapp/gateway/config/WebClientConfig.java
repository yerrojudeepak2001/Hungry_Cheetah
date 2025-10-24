//package com.foodapp.gateway.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.loadbalancer.reactive.ReactiveLoadBalancerExchangeFilterFunction;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.reactive.function.client.WebClient;
//
//@Configuration
//public class WebClientConfig {
//
//    private final ReactiveLoadBalancerExchangeFilterFunction lbFunction;
//
//    @Autowired
//    public WebClientConfig(ReactiveLoadBalancerExchangeFilterFunction lbFunction) {
//        this.lbFunction = lbFunction;
//    }
//
//    @Bean
//    public WebClient.Builder webClientBuilder() {
//        // Adds load balancing filter to WebClient for calling other microservices by name
//        return WebClient.builder().filter(lbFunction);
//    }
//}
