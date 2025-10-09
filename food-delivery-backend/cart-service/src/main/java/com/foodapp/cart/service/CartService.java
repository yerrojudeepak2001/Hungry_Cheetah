package com.foodapp.cart.service;

import com.foodapp.cart.model.Cart;
import com.foodapp.cart.model.CartItem;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final RestaurantClient restaurantClient;

    public CartService(CartRepository cartRepository, RestaurantClient restaurantClient) {
        this.cartRepository = cartRepository;
        this.restaurantClient = restaurantClient;
    }

    public Mono<Cart> addToCart(Long userId, Long restaurantId, CartItem item) {
        return cartRepository.findByUserIdAndIsActive(userId, true)
            .flatMap(existingCart -> {
                // Check if item is from same restaurant
                if (!existingCart.getRestaurantId().equals(restaurantId)) {
                    throw new InvalidCartOperationException("Items must be from the same restaurant");
                }
                
                // Update existing item or add new one
                boolean itemExists = false;
                for (CartItem cartItem : existingCart.getItems()) {
                    if (cartItem.getMenuItemId().equals(item.getMenuItemId())) {
                        cartItem.setQuantity(cartItem.getQuantity() + item.getQuantity());
                        itemExists = true;
                        break;
                    }
                }
                
                if (!itemExists) {
                    existingCart.getItems().add(item);
                }
                
                existingCart.setTotalAmount(calculateTotal(existingCart.getItems()));
                existingCart.setLastUpdated(LocalDateTime.now());
                
                return cartRepository.save(existingCart);
            })
            .switchIfEmpty(Mono.defer(() -> {
                // Create new cart
                Cart newCart = new Cart();
                newCart.setUserId(userId);
                newCart.setRestaurantId(restaurantId);
                newCart.setItems(Arrays.asList(item));
                newCart.setTotalAmount(calculateTotal(Arrays.asList(item)));
                newCart.setLastUpdated(LocalDateTime.now());
                newCart.setIsActive(true);
                
                return cartRepository.save(newCart);
            }));
    }

    public Mono<Cart> updateCartItem(Long userId, Long menuItemId, Integer quantity) {
        return cartRepository.findByUserIdAndIsActive(userId, true)
            .flatMap(cart -> {
                cart.getItems().stream()
                    .filter(item -> item.getMenuItemId().equals(menuItemId))
                    .findFirst()
                    .ifPresent(item -> {
                        if (quantity <= 0) {
                            cart.getItems().remove(item);
                        } else {
                            item.setQuantity(quantity);
                        }
                    });
                
                cart.setTotalAmount(calculateTotal(cart.getItems()));
                cart.setLastUpdated(LocalDateTime.now());
                
                return cartRepository.save(cart);
            });
    }

    public Mono<Cart> getActiveCart(Long userId) {
        return cartRepository.findByUserIdAndIsActive(userId, true);
    }

    public Mono<Void> clearCart(Long userId) {
        return cartRepository.findByUserIdAndIsActive(userId, true)
            .flatMap(cart -> {
                cart.setIsActive(false);
                return cartRepository.save(cart);
            })
            .then();
    }

    private BigDecimal calculateTotal(List<CartItem> items) {
        return items.stream()
            .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}