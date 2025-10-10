package com.foodapp.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {
    
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[1-9]\\d{9,14}$");
    
    @Override
    public void initialize(ValidPhoneNumber constraintAnnotation) {
        // No initialization needed
    }
    
    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        if (phoneNumber == null) {
            return true; // null values should be handled by @NotNull annotation
        }
        
        // Remove any whitespace and special characters
        String cleanPhoneNumber = phoneNumber.replaceAll("[\\s\\-\\(\\)]", "");
        
        return PHONE_PATTERN.matcher(cleanPhoneNumber).matches();
    }
}