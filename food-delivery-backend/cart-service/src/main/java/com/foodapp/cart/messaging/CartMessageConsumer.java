package com.foodapp.cart.messaging;

import com.foodapp.cart.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CartMessageConsumer {

    private final EmailService emailService;

    @JmsListener(destination = "order.created")
    public void handleOrderCreated(String orderCreatedMessage) {
        try {
            log.info("Received order created message: {}", orderCreatedMessage);
            // Process order created event
            // You can parse the message and extract order details
            // Then trigger any cart-related cleanup or notifications
        } catch (Exception e) {
            log.error("Error processing order created message: {}", orderCreatedMessage, e);
        }
    }

    @JmsListener(destination = "cart.reminder")
    public void handleCartReminder(String cartReminderMessage) {
        try {
            log.info("Received cart reminder message: {}", cartReminderMessage);
            // Parse the message and send reminder email
            // This could be triggered by a scheduled job in another service
        } catch (Exception e) {
            log.error("Error processing cart reminder message: {}", cartReminderMessage, e);
        }
    }

    @JmsListener(destination = "inventory.low")
    public void handleLowInventory(String inventoryMessage) {
        try {
            log.info("Received low inventory message: {}", inventoryMessage);
            // Handle low inventory notifications
            // Could disable items in carts or send notifications
        } catch (Exception e) {
            log.error("Error processing low inventory message: {}", inventoryMessage, e);
        }
    }

    @JmsListener(destination = "price.update")
    public void handlePriceUpdate(String priceUpdateMessage) {
        try {
            log.info("Received price update message: {}", priceUpdateMessage);
            // Handle price updates for items in carts
            // Update cart totals or notify users of price changes
        } catch (Exception e) {
            log.error("Error processing price update message: {}", priceUpdateMessage, e);
        }
    }

    @JmsListener(destination = "restaurant.status")
    public void handleRestaurantStatus(String restaurantStatusMessage) {
        try {
            log.info("Received restaurant status message: {}", restaurantStatusMessage);
            // Handle restaurant status changes (open/closed)
            // Could affect items in carts from that restaurant
        } catch (Exception e) {
            log.error("Error processing restaurant status message: {}", restaurantStatusMessage, e);
        }
    }
}