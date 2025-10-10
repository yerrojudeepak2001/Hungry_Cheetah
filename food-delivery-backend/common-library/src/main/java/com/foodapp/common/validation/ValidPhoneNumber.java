package com.foodapp.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * Validation annotation to ensure that a phone number is in a valid format.
 * Valid formats include:
 * - Optional '+' prefix
 * - 10-15 digits
 * - No special characters or spaces (will be stripped during validation)
 *
 * Examples of valid phone numbers:
 * - +919876543210
 * - 919876543210
 * - +442071234567
 */
@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPhoneNumber {
    String message() default "Invalid phone number format. Must be 10-15 digits with optional '+' prefix";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}