package com.foodapp.cart.messaging;

import com.foodapp.cart.config.JmsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class CartMessageProducer {

    private final JmsTemplate jmsTemplate;

    public void sendCartUpdatedMessage(String cartId, String userId, String action, Map<String, Object> details) {
        try {
            CartUpdateMessage message = CartUpdateMessage.builder()
                    .cartId(cartId)
                    .userId(userId)
                    .action(action)
                    .details(details)
                    .timestamp(System.currentTimeMillis())
                    .build();

            jmsTemplate.convertAndSend(JmsConfig.CART_UPDATED_QUEUE, message);
            log.info("Cart updated message sent for cart: {} by user: {}", cartId, userId);
        } catch (Exception e) {
            log.error("Error sending cart updated message for cart: {}", cartId, e);
        }
    }

    public void sendCartAbandonmentMessage(String cartId, String userId, String userEmail, double totalAmount) {
        try {
            CartAbandonmentMessage message = CartAbandonmentMessage.builder()
                    .cartId(cartId)
                    .userId(userId)
                    .userEmail(userEmail)
                    .totalAmount(totalAmount)
                    .abandonedAt(System.currentTimeMillis())
                    .build();

            jmsTemplate.convertAndSend(JmsConfig.CART_ABANDONED_QUEUE, message);
            log.info("Cart abandonment message sent for cart: {} by user: {}", cartId, userId);
        } catch (Exception e) {
            log.error("Error sending cart abandonment message for cart: {}", cartId, e);
        }
    }

    public void sendCartCheckoutMessage(String cartId, String userId, double totalAmount, Map<String, Object> orderDetails) {
        try {
            CartCheckoutMessage message = CartCheckoutMessage.builder()
                    .cartId(cartId)
                    .userId(userId)
                    .totalAmount(totalAmount)
                    .orderDetails(orderDetails)
                    .checkoutAt(System.currentTimeMillis())
                    .build();

            jmsTemplate.convertAndSend(JmsConfig.CART_CHECKOUT_QUEUE, message);
            log.info("Cart checkout message sent for cart: {} by user: {}", cartId, userId);
        } catch (Exception e) {
            log.error("Error sending cart checkout message for cart: {}", cartId, e);
        }
    }

    public void sendNotificationMessage(String type, String recipient, String subject, String content, Map<String, Object> metadata) {
        try {
            NotificationMessage message = NotificationMessage.builder()
                    .type(type)
                    .recipient(recipient)
                    .subject(subject)
                    .content(content)
                    .metadata(metadata)
                    .timestamp(System.currentTimeMillis())
                    .build();

            jmsTemplate.convertAndSend(JmsConfig.NOTIFICATION_QUEUE, message);
            log.info("Notification message sent: {} to {}", type, recipient);
        } catch (Exception e) {
            log.error("Error sending notification message to: {}", recipient, e);
        }
    }

    public void sendInventoryUpdateMessage(String restaurantId, String menuItemId, int quantityChange, String reason) {
        try {
            InventoryUpdateMessage message = InventoryUpdateMessage.builder()
                    .restaurantId(restaurantId)
                    .menuItemId(menuItemId)
                    .quantityChange(quantityChange)
                    .reason(reason)
                    .timestamp(System.currentTimeMillis())
                    .build();

            jmsTemplate.convertAndSend(JmsConfig.INVENTORY_UPDATE_QUEUE, message);
            log.info("Inventory update message sent for item: {} in restaurant: {}", menuItemId, restaurantId);
        } catch (Exception e) {
            log.error("Error sending inventory update message for item: {}", menuItemId, e);
        }
    }
}