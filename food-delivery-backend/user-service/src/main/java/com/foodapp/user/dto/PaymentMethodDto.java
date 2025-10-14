package com.foodapp.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodDto {
    private String id;
    
    @NotBlank(message = "Payment method type is required")
    private String type; // CREDIT_CARD, DEBIT_CARD, DIGITAL_WALLET, BANK_ACCOUNT
    
    private String cardNumber; // Masked for security
    private String cardHolderName;
    private String expiryMonth;
    private String expiryYear;
    private String lastFourDigits;
    private String brand; // VISA, MASTERCARD, AMEX, etc.
    
    @NotNull(message = "Default flag is required")
    private Boolean isDefault;
    
    @NotNull(message = "Active flag is required") 
    private Boolean isActive;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    
    private String billingAddressId;
}