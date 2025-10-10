package com.foodapp.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    
    private Long id;

    @NotBlank(message = "Street address is required")
    @Size(max = 100, message = "Street address must not exceed 100 characters")
    private String streetAddress;

    @Size(max = 50, message = "Apartment/Suite number must not exceed 50 characters")
    private String apartmentNumber;

    @NotBlank(message = "City is required")
    @Size(max = 50, message = "City name must not exceed 50 characters")
    private String city;

    @NotBlank(message = "State is required")
    @Size(max = 50, message = "State name must not exceed 50 characters")
    private String state;

    @NotBlank(message = "Country is required")
    @Size(max = 50, message = "Country name must not exceed 50 characters")
    private String country;

    @NotBlank(message = "Postal code is required")
    @Pattern(regexp = "^[0-9]{6}$", message = "Invalid postal code format")
    private String postalCode;

    @Size(max = 100, message = "Landmark must not exceed 100 characters")
    private String landmark;

    @Pattern(regexp = "^\\+?[1-9]\\d{9,14}$", message = "Invalid phone number format")
    private String addressPhone;

    @Size(max = 200, message = "Delivery instructions must not exceed 200 characters")
    private String deliveryInstructions;

    private Double latitude;
    
    private Double longitude;

    private Boolean isDefault;

    private String addressType; // HOME, WORK, OTHER

    private Boolean isActive;
}
