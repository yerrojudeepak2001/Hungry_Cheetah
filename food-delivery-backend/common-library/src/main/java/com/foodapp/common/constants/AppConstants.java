package com.foodapp.common.constants;

public class AppConstants {
    // Queue Constants
    public static final String ORDER_CREATED_QUEUE = "order.created.queue";
    public static final String PAYMENT_SUCCESS_QUEUE = "payment.success.queue";
    public static final String DELIVERY_COMPLETED_QUEUE = "delivery.completed.queue";
    public static final String USER_REGISTERED_QUEUE = "user.registered.queue";
    
    // JWT Constants
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    
    private AppConstants() {
        // Private constructor to prevent instantiation
    }
}