package com.foodapp.cart.expiry;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CartExpiryHandler {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ExpiryPolicy expiryPolicy;
    private static final String CART_KEY_PREFIX = "cart:";

    @Scheduled(fixedRate = 300000) // Run every 5 minutes
    public void handleExpiredCarts() {
        Set<String> cartKeys = redisTemplate.keys(CART_KEY_PREFIX + "*");
        LocalDateTime now = LocalDateTime.now();

        for (String cartKey : cartKeys) {
            Cart cart = (Cart) redisTemplate.opsForValue().get(cartKey);
            if (cart != null && isCartExpired(cart, now)) {
                handleCartExpiry(cart);
            }
        }
    }

    private boolean isCartExpired(Cart cart, LocalDateTime now) {
        LocalDateTime lastUpdateTime = cart.getUpdatedAt();
        Duration timeElapsed = Duration.between(lastUpdateTime, now);
        return timeElapsed.toMinutes() > expiryPolicy.getExpiryMinutes(cart);
    }

    private void handleCartExpiry(Cart cart) {
        // Archive cart data if needed
        archiveCart(cart);
        
        // Delete cart from Redis
        redisTemplate.delete(CART_KEY_PREFIX + cart.getId());
        
        // Notify user if configured
        if (expiryPolicy.shouldNotifyUser(cart)) {
            notifyUser(cart);
        }
        
        // Release reserved inventory if any
        releaseReservedInventory(cart);
    }

    private void archiveCart(Cart cart) {
        // Archive cart data to persistent storage for analytics
    }

    private void notifyUser(Cart cart) {
        // Send notification to user about cart expiry
    }

    private void releaseReservedInventory(Cart cart) {
        // Release any reserved inventory items
    }
}