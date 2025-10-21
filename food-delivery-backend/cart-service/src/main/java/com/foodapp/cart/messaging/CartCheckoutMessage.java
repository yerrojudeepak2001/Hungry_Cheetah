package com.foodapp.cart.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartCheckoutMessage implements Serializable {
    private String cartId;
    private String userId;
    private double totalAmount;
    private Map<String, Object> orderDetails;
    private long checkoutAt;
}