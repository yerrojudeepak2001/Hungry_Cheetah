package com.foodapp.payment.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.persistence.Embeddable;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMetadata {
    private String cardLast4;
    private String cardType;
    private String cardNetwork;
    private String upiId;
    private String walletType;
    private String bankName;
    private String bankTransactionId;
    private String deviceId;
    private String ipAddress;
    private String userAgent;
    private String location;
    private Boolean is3DSecure;
}