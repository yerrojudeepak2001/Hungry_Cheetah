package com.foodapp.cart.expiry;

import com.foodapp.cart.entity.Cart;
import com.foodapp.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CartExpiryHandler {
    private final CartRepository cartRepository;
    private final ExpiryPolicy expiryPolicy;

    @Scheduled(fixedRate = 300000) // Run every 5 minutes
    public void handleExpiredCarts() {
        LocalDateTime now = LocalDateTime.now();
        List<Cart> expiredCarts = cartRepository.findAllExpired(now.minusMinutes(30));

        for (Cart cart : expiredCarts) {
            if (isCartExpired(cart, now)) {
                handleCartExpiry(cart);
            }
        }
    }

    private boolean isCartExpired(Cart cart, LocalDateTime now) {
        LocalDateTime lastUpdateTime = cart.getUpdatedAt();
        if (lastUpdateTime == null) {
            lastUpdateTime = cart.getCreatedAt();
        }
        if (lastUpdateTime == null) {
            return true;
        }
        Duration timeElapsed = Duration.between(lastUpdateTime, now);
        return timeElapsed.toMinutes() > expiryPolicy.getExpiryMinutes(cart);
    }

    private void handleCartExpiry(Cart cart) {
        // Archive cart data if needed
        archiveCart(cart);
        
        // Delete cart from database
        cartRepository.delete(cart);
        
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