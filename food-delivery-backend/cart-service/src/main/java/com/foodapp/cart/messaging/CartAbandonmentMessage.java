package com.foodapp.cart.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartAbandonmentMessage implements Serializable {
    private String cartId;
    private String userId;
    private String userEmail;
    private double totalAmount;
    private long abandonedAt;
}