package com.foodapp.cart.service;

import com.foodapp.cart.entity.Cart;
import com.foodapp.cart.entity.CartItem;
import com.foodapp.cart.repository.CartRepository;
import com.foodapp.cart.dto.CartItemUpdate;
import com.foodapp.cart.dto.MultiRestaurantCartItem;
import com.foodapp.cart.client.InventoryClient;
import com.foodapp.cart.client.PricingClient;
import com.foodapp.cart.client.RestaurantClient;
import com.foodapp.cart.exception.CartNotFoundException;
import com.foodapp.cart.exception.CartValidationException;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final InventoryClient inventoryClient;
    private final PricingClient pricingClient;
    private final RestaurantClient restaurantClient;

    public CartService(CartRepository cartRepository, 
                      InventoryClient inventoryClient,
                      PricingClient pricingClient,
                      RestaurantClient restaurantClient) {
        this.cartRepository = cartRepository;
        this.inventoryClient = inventoryClient;
        this.pricingClient = pricingClient;
        this.restaurantClient = restaurantClient;
    }

    public Cart createCart(String userId, String restaurantId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setRestaurantId(restaurantId);
        cart.setTotalAmount(BigDecimal.ZERO);
        cart.setIsActive(true);
        return cartRepository.save(cart);
    }

    public Optional<Cart> getCartByUserId(String userId) {
        return cartRepository.findByUserIdAndIsActive(userId, true);
    }

    public Optional<Cart> getCartById(Long cartId) {
        return cartRepository.findById(cartId);
    }

    public Cart addItemToCart(String userId, String restaurantId, CartItemUpdate itemUpdate) {
        Cart cart = getCartByUserId(userId)
                .orElseGet(() -> createCart(userId, restaurantId));
        
        // Add item logic here
        return cartRepository.save(cart);
    }

    public Cart updateCartItem(String userId, Long itemId, CartItemUpdate itemUpdate) {
        Cart cart = getCartByUserId(userId)
                .orElseThrow(() -> CartNotFoundException.forUser(userId));
        
        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new CartValidationException("Cart is empty");
        }

        // Find the item to update
        CartItem itemToUpdate = cart.getItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new CartValidationException("Item not found in cart: " + itemId));

        // Update item properties
        if (itemUpdate.getQuantity() != null) {
            if (itemUpdate.getQuantity() <= 0) {
                // Remove item if quantity is 0 or negative
                cart.getItems().removeIf(item -> item.getId().equals(itemId));
            } else {
                itemToUpdate.setQuantity(itemUpdate.getQuantity());
            }
        }

        if (itemUpdate.getCustomizations() != null) {
            itemToUpdate.setCustomizations(itemUpdate.getCustomizations());
        }

        if (itemUpdate.getSpecialInstructions() != null) {
            itemToUpdate.setSpecialInstructions(itemUpdate.getSpecialInstructions());
        }

        if (itemUpdate.getUnitPrice() != null) {
            itemToUpdate.setPrice(itemUpdate.getUnitPrice());
        }

        // Recalculate total
        cart = calculateCartTotal(cart);
        
        return cartRepository.save(cart);
    }

    public void removeItemFromCart(String userId, Long itemId) {
        Optional<Cart> cartOpt = getCartByUserId(userId);
        if (cartOpt.isPresent()) {
            Cart cart = cartOpt.get();
            if (cart.getItems() != null) {
                // Remove the item with the specified ID
                boolean removed = cart.getItems().removeIf(item -> item.getId().equals(itemId));
                
                if (removed) {
                    // Recalculate total after removal
                    cart = calculateCartTotal(cart);
                    cartRepository.save(cart);
                } else {
                    throw new CartValidationException("Item not found in cart: " + itemId);
                }
            }
        } else {
            throw CartNotFoundException.forUser(userId);
        }
    }

    public void updateItemQuantity(String userId, Long itemId, Integer quantity) {
        if (quantity <= 0) {
            removeItemFromCart(userId, itemId);
            return;
        }
        
        Optional<Cart> cartOpt = getCartByUserId(userId);
        if (cartOpt.isPresent()) {
            Cart cart = cartOpt.get();
            if (cart.getItems() != null) {
                // Find and update the item quantity
                boolean found = false;
                for (CartItem item : cart.getItems()) {
                    if (item.getId().equals(itemId)) {
                        item.setQuantity(quantity);
                        found = true;
                        break;
                    }
                }
                
                if (found) {
                    // Recalculate total after quantity update
                    cart = calculateCartTotal(cart);
                    cartRepository.save(cart);
                } else {
                    throw new CartValidationException("Item not found in cart: " + itemId);
                }
            }
        } else {
            throw CartNotFoundException.forUser(userId);
        }
    }

    public void clearCart(String userId) {
        Optional<Cart> cartOpt = getCartByUserId(userId);
        if (cartOpt.isPresent()) {
            Cart cart = cartOpt.get();
            if (cart.getItems() != null) {
                cart.getItems().clear();
            }
            cartRepository.save(cart);
        }
    }

    public Cart mergeMultiRestaurantCart(String userId, List<MultiRestaurantCartItem> items) {
        Cart cart = getCartByUserId(userId).orElse(new Cart());
        if (cart.getId() == null) {
            cart.setUserId(userId);
            cart.setCreatedAt(LocalDateTime.now());
            cart.setIsActive(true);
            cart = cartRepository.save(cart);
        }
        
        if (items != null && !items.isEmpty()) {
            // Group items by restaurant
            Map<String, List<MultiRestaurantCartItem>> itemsByRestaurant = items.stream()
                .collect(Collectors.groupingBy(MultiRestaurantCartItem::getRestaurantId));
            
            for (Map.Entry<String, List<MultiRestaurantCartItem>> entry : itemsByRestaurant.entrySet()) {
                String restaurantId = entry.getKey();
                List<MultiRestaurantCartItem> restaurantItems = entry.getValue();
                
                // Validate restaurant is operational
                try {
                    boolean isAvailable = restaurantClient.checkRestaurantAvailability(restaurantId);
                    if (!isAvailable) {
                        continue; // Skip unavailable restaurants
                    }
                } catch (Exception e) {
                    continue; // Skip on service error
                }
                
                // Convert and add items from this restaurant
                for (MultiRestaurantCartItem multiItem : restaurantItems) {
                    CartItem cartItem = new CartItem();
                    cartItem.setMenuItemId(multiItem.getItemId());
                    cartItem.setRestaurantId(restaurantId);
                    cartItem.setQuantity(multiItem.getQuantity());
                    cartItem.setPrice(multiItem.getUnitPrice());
                    cartItem.setSpecialInstructions(multiItem.getCustomizations());
                    cartItem.setCart(cart);
                    
                    // Check if item already exists in cart and merge quantities
                    Optional<CartItem> existingItem = cart.getItems().stream()
                        .filter(item -> item.getMenuItemId().equals(cartItem.getMenuItemId()) 
                                     && item.getRestaurantId().equals(cartItem.getRestaurantId()))
                        .findFirst();
                    
                    if (existingItem.isPresent()) {
                        existingItem.get().setQuantity(
                            existingItem.get().getQuantity() + cartItem.getQuantity()
                        );
                    } else {
                        cart.getItems().add(cartItem);
                    }
                }
            }
            
            // Recalculate totals and save
            cart = calculateCartTotal(cart);
            cart = cartRepository.save(cart);
        }
        
        return cart;
    }

    public void expireCart(Long cartId) {
        Optional<Cart> cartOpt = cartRepository.findById(cartId);
        if (cartOpt.isPresent()) {
            Cart cart = cartOpt.get();
            cart.setIsActive(false);
            cartRepository.save(cart);
        }
    }

    public List<Cart> getExpiredCarts() {
        return cartRepository.findAllExpired(LocalDateTime.now().minusHours(24));
    }

    public Cart calculateCartTotal(Cart cart) {
        if (cart == null || cart.getItems() == null || cart.getItems().isEmpty()) {
            if (cart != null) {
                cart.setTotalAmount(BigDecimal.ZERO);
            }
            return cart;
        }

        BigDecimal total = cart.getItems().stream()
                .filter(item -> item.getPrice() != null && item.getQuantity() != null)
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalAmount(total);
        cart.setUpdatedAt(LocalDateTime.now());
        return cart;
    }

    public boolean isCartEmpty(String userId) {
        return getCartByUserId(userId)
                .map(cart -> cart.getItems() == null || cart.getItems().isEmpty())
                .orElse(true);
    }

    public void validateCartItems(String userId) {
        Optional<Cart> cartOpt = getCartByUserId(userId);
        if (!cartOpt.isPresent() || cartOpt.get().getItems() == null || cartOpt.get().getItems().isEmpty()) {
            return; // Nothing to validate
        }

        Cart cart = cartOpt.get();
        List<CartItem> itemsToRemove = new java.util.ArrayList<>();

        for (CartItem item : cart.getItems()) {
            try {
                // Check inventory availability
                var stockStatus = inventoryClient.getItemStock(item.getMenuItemId());
                if (stockStatus == null || !stockStatus.getIsAvailable() || 
                    stockStatus.getAvailableStock() < item.getQuantity()) {
                    // Mark item for removal if not available
                    itemsToRemove.add(item);
                    continue;
                }

                // Create pricing request for current price validation
                var pricingRequest = new com.foodapp.cart.dto.PricingRequest();
                pricingRequest.setCustomerId(userId);
                pricingRequest.setRestaurantId(item.getRestaurantId());
                
                // Create pricing item
                var pricingItem = new com.foodapp.cart.dto.PricingRequest.PricingItem();
                pricingItem.setItemId(item.getMenuItemId());
                pricingItem.setQuantity(item.getQuantity());
                pricingItem.setUnitPrice(item.getPrice());
                
                pricingRequest.setItems(List.of(pricingItem));
                
                var priceBreakdown = pricingClient.calculatePrice(pricingRequest);
                if (priceBreakdown != null && priceBreakdown.getSubtotal() != null) {
                    // Calculate expected unit price from subtotal
                    BigDecimal expectedUnitPrice = priceBreakdown.getSubtotal()
                            .divide(BigDecimal.valueOf(item.getQuantity()), 2, java.math.RoundingMode.HALF_UP);
                    
                    // Update price if it has changed significantly (more than 1 cent difference)
                    if (item.getPrice().subtract(expectedUnitPrice).abs().compareTo(new BigDecimal("0.01")) > 0) {
                        item.setPrice(expectedUnitPrice);
                    }
                }

            } catch (Exception e) {
                // If validation fails, mark item for removal
                itemsToRemove.add(item);
            }
        }

        // Remove unavailable items
        cart.getItems().removeAll(itemsToRemove);

        // Recalculate total if items were removed or prices updated
        if (!itemsToRemove.isEmpty()) {
            cart = calculateCartTotal(cart);
            cartRepository.save(cart);
        }
    }

    // Additional methods needed by controller
    public Cart addToCart(Long userId, CartItem item) {
        if (item == null || item.getMenuItemId() == null || item.getQuantity() == null || item.getQuantity() <= 0) {
            throw new IllegalArgumentException("Invalid cart item data");
        }

        String userIdStr = String.valueOf(userId);
        
        // Get or create cart for user
        Cart cart = getCartByUserId(userIdStr)
                .orElseGet(() -> createCart(userIdStr, item.getRestaurantId()));

        // Check if cart is from different restaurant (single restaurant constraint)
        if (cart.getRestaurantId() != null && !cart.getRestaurantId().equals(item.getRestaurantId())) {
            // Clear existing items if switching restaurants
            if (cart.getItems() != null) {
                cart.getItems().clear();
            }
            cart.setRestaurantId(item.getRestaurantId());
        }

        // Initialize items list if null
        if (cart.getItems() == null) {
            cart.setItems(new java.util.ArrayList<>());
        }

        // Check if item already exists in cart
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(cartItem -> cartItem.getMenuItemId().equals(item.getMenuItemId())
                        && Objects.equals(cartItem.getCustomizations(), item.getCustomizations()))
                .findFirst();

        if (existingItem.isPresent()) {
            // Update quantity of existing item
            CartItem existing = existingItem.get();
            existing.setQuantity(existing.getQuantity() + item.getQuantity());
            existing.setSpecialInstructions(item.getSpecialInstructions());
        } else {
            // Add new item to cart
            item.setCart(cart);
            item.setAddedAt(LocalDateTime.now());
            cart.getItems().add(item);
        }

        // Recalculate total
        cart = calculateCartTotal(cart);
        
        return cartRepository.save(cart);
    }

    public Optional<Cart> getCart(Long userId) {
        return getCartByUserId(String.valueOf(userId));
    }

    public void removeFromCart(Long userId, Long itemId) {
        removeItemFromCart(String.valueOf(userId), itemId);
    }

    public List<String> getSmartSuggestions(Long userId) {
        Optional<Cart> cartOpt = getCartByUserId(String.valueOf(userId));
        if (!cartOpt.isPresent() || cartOpt.get().getItems() == null || cartOpt.get().getItems().isEmpty()) {
            return List.of(); // No suggestions for empty cart
        }

        Cart cart = cartOpt.get();
        List<String> suggestions = new java.util.ArrayList<>();

        try {
            // Analyze cart contents for smart suggestions
            boolean hasMainCourse = cart.getItems().stream()
                    .anyMatch(item -> item.getItemName() != null && 
                            (item.getItemName().toLowerCase().contains("burger") ||
                             item.getItemName().toLowerCase().contains("pizza") ||
                             item.getItemName().toLowerCase().contains("pasta") ||
                             item.getItemName().toLowerCase().contains("chicken") ||
                             item.getItemName().toLowerCase().contains("rice")));

            boolean hasBeverage = cart.getItems().stream()
                    .anyMatch(item -> item.getItemName() != null && 
                            (item.getItemName().toLowerCase().contains("drink") ||
                             item.getItemName().toLowerCase().contains("soda") ||
                             item.getItemName().toLowerCase().contains("juice") ||
                             item.getItemName().toLowerCase().contains("water")));

            boolean hasDessert = cart.getItems().stream()
                    .anyMatch(item -> item.getItemName() != null && 
                            (item.getItemName().toLowerCase().contains("cake") ||
                             item.getItemName().toLowerCase().contains("ice cream") ||
                             item.getItemName().toLowerCase().contains("dessert")));

            boolean hasSides = cart.getItems().stream()
                    .anyMatch(item -> item.getItemName() != null && 
                            (item.getItemName().toLowerCase().contains("fries") ||
                             item.getItemName().toLowerCase().contains("salad") ||
                             item.getItemName().toLowerCase().contains("bread")));

            // Generate suggestions based on what's missing
            if (hasMainCourse && !hasBeverage) {
                suggestions.add("Add a refreshing beverage to complement your meal");
            }
            
            if (hasMainCourse && !hasSides) {
                suggestions.add("Consider adding a side dish like fries or salad");
            }
            
            if (hasMainCourse && !hasDessert) {
                suggestions.add("Complete your meal with a delicious dessert");
            }
            
            if (!hasMainCourse && (hasBeverage || hasSides)) {
                suggestions.add("Add a main course to make it a complete meal");
            }

            // Cart value based suggestions
            BigDecimal cartTotal = cart.getTotalAmount() != null ? cart.getTotalAmount() : BigDecimal.ZERO;
            if (cartTotal.compareTo(new BigDecimal("25")) < 0) {
                suggestions.add("Add $" + new BigDecimal("25").subtract(cartTotal) + " more for free delivery");
            }

            // Quantity based suggestions
            int totalItems = cart.getItems().stream()
                    .mapToInt(item -> item.getQuantity() != null ? item.getQuantity() : 0)
                    .sum();
            
            if (totalItems == 1) {
                suggestions.add("Popular combo deals available - save more by bundling items");
            }

        } catch (Exception e) {
            // Fallback suggestions if analysis fails
            suggestions.add("Browse popular items from this restaurant");
            suggestions.add("Check out today's special offers");
        }

        return suggestions.isEmpty() ? 
                List.of("Your cart looks great! Ready to checkout?") : 
                suggestions;
    }

    public Cart applyComboDeals(Long userId) {
        Optional<Cart> cartOpt = getCartByUserId(String.valueOf(userId));
        if (!cartOpt.isPresent()) {
            return null;
        }

        Cart cart = cartOpt.get();
        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            return cart;
        }

        try {
            // Simple combo deal logic - look for common combinations
            boolean hasBurger = cart.getItems().stream()
                    .anyMatch(item -> item.getItemName() != null && 
                            item.getItemName().toLowerCase().contains("burger"));
            
            boolean hasFries = cart.getItems().stream()
                    .anyMatch(item -> item.getItemName() != null && 
                            item.getItemName().toLowerCase().contains("fries"));
            
            boolean hasDrink = cart.getItems().stream()
                    .anyMatch(item -> item.getItemName() != null && 
                            (item.getItemName().toLowerCase().contains("drink") ||
                             item.getItemName().toLowerCase().contains("soda")));

            // Apply burger combo discount if all three items are present
            if (hasBurger && hasFries && hasDrink) {
                BigDecimal comboDiscount = new BigDecimal("2.00"); // $2 off combo
                BigDecimal currentTotal = cart.getTotalAmount() != null ? cart.getTotalAmount() : BigDecimal.ZERO;
                cart.setTotalAmount(currentTotal.subtract(comboDiscount).max(BigDecimal.ZERO));
                cart.setUpdatedAt(LocalDateTime.now());
                cartRepository.save(cart);
            }

            // Look for pizza + drink combo
            boolean hasPizza = cart.getItems().stream()
                    .anyMatch(item -> item.getItemName() != null && 
                            item.getItemName().toLowerCase().contains("pizza"));
            
            if (hasPizza && hasDrink && cart.getItems().size() >= 2) {
                BigDecimal pizzaComboDiscount = new BigDecimal("1.50"); // $1.50 off pizza combo
                BigDecimal currentTotal = cart.getTotalAmount() != null ? cart.getTotalAmount() : BigDecimal.ZERO;
                cart.setTotalAmount(currentTotal.subtract(pizzaComboDiscount).max(BigDecimal.ZERO));
                cart.setUpdatedAt(LocalDateTime.now());
                cartRepository.save(cart);
            }

        } catch (Exception e) {
            // If combo deal application fails, return cart as-is
            System.err.println("Error applying combo deals: " + e.getMessage());
        }

        return cart;
    }

    public Cart addToMultiRestaurantCart(Long userId, MultiRestaurantCartItem item) {
        if (item == null || item.getItemId() == null || item.getQuantity() == null || item.getQuantity() <= 0) {
            throw new IllegalArgumentException("Invalid multi-restaurant cart item data");
        }

        String userIdStr = String.valueOf(userId);
        
        // For multi-restaurant, we'll create or use a cart for the specific restaurant
        Optional<Cart> cartOpt = cartRepository.findByUserIdAndRestaurantId(userIdStr, item.getRestaurantId());
        
        Cart cart;
        if (cartOpt.isPresent()) {
            cart = cartOpt.get();
        } else {
            cart = createCart(userIdStr, item.getRestaurantId());
        }

        // Convert MultiRestaurantCartItem to CartItem
        CartItem cartItem = CartItem.builder()
                .menuItemId(item.getItemId())
                .restaurantId(item.getRestaurantId())
                .itemName(item.getItemName())
                .quantity(item.getQuantity())
                .price(item.getUnitPrice())
                .customizations(item.getCustomizations())
                .cart(cart)
                .addedAt(LocalDateTime.now())
                .build();

        // Initialize items list if null
        if (cart.getItems() == null) {
            cart.setItems(new java.util.ArrayList<>());
        }

        // Check if item already exists in cart
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(cartExistingItem -> cartExistingItem.getMenuItemId().equals(item.getItemId())
                        && Objects.equals(cartExistingItem.getCustomizations(), item.getCustomizations()))
                .findFirst();

        if (existingItem.isPresent()) {
            // Update quantity of existing item
            CartItem existing = existingItem.get();
            existing.setQuantity(existing.getQuantity() + item.getQuantity());
        } else {
            // Add new item to cart
            cart.getItems().add(cartItem);
        }

        // Recalculate total
        cart = calculateCartTotal(cart);
        
        return cartRepository.save(cart);
    }

    public List<Cart> getMultiRestaurantCart(Long userId) {
        // Get all carts for the user (multiple restaurants)
        return cartRepository.findAll().stream()
                .filter(cart -> cart.getUserId().equals(String.valueOf(userId)) && cart.getIsActive())
                .collect(java.util.stream.Collectors.toList());
    }

    public void saveForLater(Long userId, Long itemId) {
        Optional<Cart> cartOpt = getCartByUserId(String.valueOf(userId));
        if (cartOpt.isPresent()) {
            Cart cart = cartOpt.get();
            if (cart.getItems() != null) {
                // Find the item to save for later
                Optional<CartItem> itemToSave = cart.getItems().stream()
                        .filter(item -> item.getId().equals(itemId))
                        .findFirst();
                
                if (itemToSave.isPresent()) {
                    CartItem item = itemToSave.get();
                    // In a full implementation, you'd move this to a "saved items" table
                    // For now, we'll just add a special marker in customizations
                    item.setCustomizations(
                        (item.getCustomizations() != null ? item.getCustomizations() + ";" : "") + "SAVED_FOR_LATER"
                    );
                    
                    // Remove from active cart
                    cart.getItems().remove(item);
                    
                    // Recalculate total
                    cart = calculateCartTotal(cart);
                    cartRepository.save(cart);
                }
            }
        }
    }

    public List<CartItem> getSavedItems(Long userId) {
        // In a full implementation, this would query a separate "saved items" table
        // For now, return empty list as the save-for-later is simplified
        return List.of();
    }

    public Cart getCartSummary(Long userId) {
        return getCartByUserId(String.valueOf(userId)).orElse(null);
    }
}