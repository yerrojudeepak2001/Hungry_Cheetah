package com.foodapp.analytics.mapper;

import com.foodapp.analytics.dto.*;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AnalyticsMapper {

    public OrderAnalytics mapToOrderAnalytics(Map<String, Object> orderData) {
        // TODO: Implement mapping logic
        OrderAnalytics analytics = new OrderAnalytics();
        if (orderData.containsKey("orderId")) {
            analytics.setOrderId((String) orderData.get("orderId"));
        }
        if (orderData.containsKey("restaurantId")) {
            analytics.setRestaurantId((String) orderData.get("restaurantId"));
        }
        if (orderData.containsKey("userId")) {
            analytics.setUserId((String) orderData.get("userId"));
        }
        if (orderData.containsKey("totalAmount")) {
            analytics.setTotalAmount((Double) orderData.get("totalAmount"));
        }
        return analytics;
    }

    public DeliveryAnalytics mapToDeliveryAnalytics(Map<String, Object> deliveryData) {
        // TODO: Implement mapping logic
        DeliveryAnalytics analytics = new DeliveryAnalytics();
        if (deliveryData.containsKey("deliveryId")) {
            analytics.setDeliveryId((String) deliveryData.get("deliveryId"));
        }
        if (deliveryData.containsKey("orderId")) {
            analytics.setOrderId((String) deliveryData.get("orderId"));
        }
        if (deliveryData.containsKey("driverId")) {
            analytics.setDriverId((String) deliveryData.get("driverId"));
        }
        return analytics;
    }

    public PaymentAnalytics mapToPaymentAnalytics(Map<String, Object> paymentData) {
        // TODO: Implement mapping logic
        PaymentAnalytics analytics = new PaymentAnalytics();
        if (paymentData.containsKey("paymentId")) {
            analytics.setPaymentId((String) paymentData.get("paymentId"));
        }
        if (paymentData.containsKey("orderId")) {
            analytics.setOrderId((String) paymentData.get("orderId"));
        }
        if (paymentData.containsKey("amount")) {
            analytics.setAmount((Double) paymentData.get("amount"));
        }
        return analytics;
    }

    public DriverAnalytics mapToDriverAnalytics(Map<String, Object> driverData) {
        // TODO: Implement mapping logic
        DriverAnalytics analytics = new DriverAnalytics();
        if (driverData.containsKey("driverId")) {
            analytics.setDriverId((String) driverData.get("driverId"));
        }
        if (driverData.containsKey("name")) {
            analytics.setName((String) driverData.get("name"));
        }
        return analytics;
    }

    public RestaurantAnalytics mapToRestaurantAnalytics(Map<String, Object> restaurantData) {
        // TODO: Implement mapping logic
        RestaurantAnalytics analytics = new RestaurantAnalytics();
        if (restaurantData.containsKey("restaurantId")) {
            analytics.setRestaurantId((String) restaurantData.get("restaurantId"));
        }
        if (restaurantData.containsKey("name")) {
            analytics.setName((String) restaurantData.get("name"));
        }
        return analytics;
    }

    public AnalyticsResponse mapToAnalyticsResponse(String type, Map<String, Object> data) {
        return new AnalyticsResponse(type, data);
    }
}