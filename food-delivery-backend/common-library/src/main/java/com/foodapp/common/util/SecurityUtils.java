package com.foodapp.common.util;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

public class SecurityUtils {
    private static final SecureRandom secureRandom = new SecureRandom();
    
    public static String generateSecureToken() {
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
    
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }
    
    public static String generateTemporaryPassword() {
        String upperChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerChars = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String specialChars = "!@#$%^&*()_-+=<>?";
        
        String allChars = upperChars + lowerChars + numbers + specialChars;
        StringBuilder password = new StringBuilder();
        
        // Ensure at least one character from each category
        password.append(upperChars.charAt(secureRandom.nextInt(upperChars.length())));
        password.append(lowerChars.charAt(secureRandom.nextInt(lowerChars.length())));
        password.append(numbers.charAt(secureRandom.nextInt(numbers.length())));
        password.append(specialChars.charAt(secureRandom.nextInt(specialChars.length())));
        
        // Add remaining characters
        for (int i = 0; i < 8; i++) {
            password.append(allChars.charAt(secureRandom.nextInt(allChars.length())));
        }
        
        // Shuffle the password
        char[] passwordArray = password.toString().toCharArray();
        for (int i = passwordArray.length - 1; i > 0; i--) {
            int index = secureRandom.nextInt(i + 1);
            char temp = passwordArray[index];
            passwordArray[index] = passwordArray[i];
            passwordArray[i] = temp;
        }
        
        return new String(passwordArray);
    }
}