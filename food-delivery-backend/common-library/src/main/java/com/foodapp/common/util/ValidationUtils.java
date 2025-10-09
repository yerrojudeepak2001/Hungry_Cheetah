package com.foodapp.common.util;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ValidationUtils {
    private static final String EMAIL_REGEX = 
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final String PHONE_REGEX = "^\\+?[1-9]\\d{1,14}$";
    private static final String PASSWORD_REGEX = 
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
    
    public static boolean isValidEmail(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }
    
    public static boolean isValidPhone(String phone) {
        return phone != null && phone.matches(PHONE_REGEX);
    }
    
    public static boolean isValidPassword(String password) {
        return password != null && password.matches(PASSWORD_REGEX);
    }
    
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    public static boolean isValidPrice(Double price) {
        return price != null && price >= 0.0;
    }
    
    public static boolean isValidQuantity(Integer quantity) {
        return quantity != null && quantity >= 0;
    }
    
    public static boolean isValidLatLong(Double latitude, Double longitude) {
        return latitude != null && longitude != null &&
               latitude >= -90 && latitude <= 90 &&
               longitude >= -180 && longitude <= 180;
    }
    
    public static <T> boolean isValidList(List<T> list) {
        return list != null && !list.isEmpty();
    }
    
    public static boolean containsOnlyAllowedValues(String value, String... allowedValues) {
        return value != null && Arrays.asList(allowedValues).contains(value);
    }
    
    public static List<String> sanitizeList(List<String> items) {
        return items.stream()
                   .filter(item -> !isNullOrEmpty(item))
                   .map(String::trim)
                   .collect(Collectors.toList());
    }
}