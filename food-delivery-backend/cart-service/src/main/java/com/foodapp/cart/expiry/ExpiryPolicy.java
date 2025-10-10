package com.foodapp.cart.expiry;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "cart.expiry")
public class ExpiryPolicy {
    private int defaultExpiryMinutes = 30;
    private int checkIntervalMinutes = 5;
    private boolean enableAutoExpiry = true;
    private int maxCartItems = 50;

    public int getExpiryMinutes(com.foodapp.cart.entity.Cart cart) {
        return defaultExpiryMinutes;
    }

    public boolean shouldNotifyUser(com.foodapp.cart.entity.Cart cart) {
        return enableAutoExpiry;
    }
}