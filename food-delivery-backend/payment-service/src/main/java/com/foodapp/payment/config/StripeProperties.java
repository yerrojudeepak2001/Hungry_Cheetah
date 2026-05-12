package com.foodapp.payment.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "stripe")
@Data
public class StripeProperties {
    private String secretKey;
    private String publishableKey;
    private String webhookSecret;
}