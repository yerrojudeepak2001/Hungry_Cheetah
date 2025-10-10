package com.foodapp.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {

    @Override
    public void initialize(ValidPhoneNumber constraintAnnotation) {
        // No initialization needed
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return false;
        }
        
        // Simple phone number validation - can be enhanced based on requirements
        String cleanNumber = phoneNumber.replaceAll("[\\s\\-\\(\\)]", "");
        return cleanNumber.matches("^\\+?[1-9]\\d{1,14}$");
    }
}