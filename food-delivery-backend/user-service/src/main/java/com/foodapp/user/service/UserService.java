package com.foodapp.user.service;

import com.foodapp.user.model.User;
import com.foodapp.user.model.UserPreference;
import com.foodapp.user.repository.UserRepository;
import com.foodapp.user.client.OrderClient;
import com.foodapp.user.client.RestaurantClient;
import com.foodapp.user.dto.OrderResponse;
import com.foodapp.user.dto.RestaurantResponse;
import com.foodapp.user.dto.UserOrderStats;
import com.foodapp.common.exception.ResourceNotFoundException;
import com.foodapp.common.exception.DuplicateResourceException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final SmsService smsService;
    private final OrderClient orderClient;
    private final RestaurantClient restaurantClient;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       EmailService emailService,
                       SmsService smsService,
                       OrderClient orderClient,
                       RestaurantClient restaurantClient) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.smsService = smsService;
        this.orderClient = orderClient;
        this.restaurantClient = restaurantClient;
    }

    // ------------------- User Management -------------------
    @Transactional
    public User registerUser(User user) {
        // Generate username if not provided
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            user.setUsername(generateUsername(user.getEmail()));
        }
        
        validateUniqueFields(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setIsEnabled(true);
        user.setIsEmailVerified(false);
        user.setIsPhoneVerified(false);
        user.setRegistrationDate(LocalDateTime.now());

        UserPreference preferences = new UserPreference();
        preferences.setUser(user);
        user.setPreferences(preferences);

        User savedUser = userRepository.save(user);

        sendVerificationEmails(savedUser);
        sendVerificationSms(savedUser);

        return savedUser;
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    @Transactional
    public User updateUser(Long userId, User userDetails) {
        User user = getUser(userId);
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setPhone(userDetails.getPhone());
        user.setDateOfBirth(userDetails.getDateOfBirth());
        user.setGender(userDetails.getGender());
        user.setLanguage(userDetails.getLanguage());
        user.setTimezone(userDetails.getTimezone());
        return userRepository.save(user);
    }

    @Transactional
    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        User user = getUser(userId);

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Invalid old password");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        // Assuming you have added sendPasswordChangeNotification to EmailService
        emailService.sendPasswordResetEmail(user.getEmail(), "Your password was changed");
    }

    @Transactional
    public User authenticateUser(String email, String password, String deviceToken, String deviceInfo) {
        // Find user by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        // Check if account is enabled and not locked
        if (!user.getIsEnabled()) {
            throw new IllegalArgumentException("Account is disabled");
        }
        
        if (!user.getAccountNonLocked()) {
            throw new IllegalArgumentException("Account is locked");
        }

        // Verify password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        // Update last login time and device token
        user.setLastLogin(LocalDateTime.now());
        if (deviceToken != null && !deviceToken.trim().isEmpty()) {
            user.setDeviceToken(deviceToken);
        }
        
        User savedUser = userRepository.save(user);

        // Send login alert email (optional)
        try {
            emailService.sendLoginAlertEmail(user.getEmail(), deviceInfo, LocalDateTime.now());
        } catch (Exception e) {
            // Log the error but don't fail the login
            System.err.println("Failed to send login alert email: " + e.getMessage());
        }

        return savedUser;
    }

    // Helper method for finding user by email (useful for login and forgot password)
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Transactional
    public void updateLastLogin(Long userId) {
        User user = getUser(userId);
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
    }

    @Transactional 
    public void clearDeviceToken(Long userId) {
        User user = getUser(userId);
        user.setDeviceToken(null);
        userRepository.save(user);
    }

    // ------------------- Utilities -------------------
    private void validateUniqueFields(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateResourceException("Email already registered");
        }
        if (userRepository.existsByPhone(user.getPhone())) {
            throw new DuplicateResourceException("Phone number already registered");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new DuplicateResourceException("Username already taken");
        }
    }

    private void sendVerificationEmails(User user) {
        String token = "token"; // Replace with token generation
        emailService.sendVerificationEmail(user.getEmail(), token);
    }

    private void sendVerificationSms(User user) {
        String code = "123456"; // Replace with code generation
        smsService.sendVerificationSms(user.getPhone(), code);
    }

    private String generateUsername(String email) {
        // Extract the part before @ and add a random number
        String baseUsername = email.substring(0, email.indexOf("@")).toLowerCase();
        // Remove any non-alphanumeric characters
        baseUsername = baseUsername.replaceAll("[^a-zA-Z0-9]", "");
        
        // Add timestamp to make it unique
        long timestamp = System.currentTimeMillis() % 10000; // Last 4 digits of current time
        return baseUsername + timestamp;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }

    // ------------------- External Integrations -------------------
    public List<OrderResponse> getUserOrders(Long userId) {
        User user = getUser(userId);
        return orderClient.getUserOrders(user.getId().toString());
    }

    public List<OrderResponse> getUserActiveOrders(Long userId) {
        User user = getUser(userId);
        return orderClient.getUserActiveOrders(user.getId().toString());
    }

    public UserOrderStats getUserOrderStats(Long userId) {
        User user = getUser(userId);
        return orderClient.getUserOrderStats(user.getId().toString());
    }

    public List<RestaurantResponse> getFavoriteRestaurants(Long userId) {
        User user = getUser(userId);
        return restaurantClient.getUserFavoriteRestaurants(user.getId().toString());
    }

    public List<RestaurantResponse> getRecentRestaurants(Long userId) {
        User user = getUser(userId);
        return restaurantClient.getUserRecentRestaurants(user.getId().toString());
    }

    // ------------------- Favorite Restaurants -------------------
    @Transactional
    public void addFavoriteRestaurant(Long userId, Long restaurantId) {
        User user = getUser(userId);
        restaurantClient.addRestaurantToFavorites(user.getId().toString(), restaurantId.toString());
    }

    @Transactional
    public void removeFavoriteRestaurant(Long userId, Long restaurantId) {
        User user = getUser(userId);
        restaurantClient.removeRestaurantFromFavorites(user.getId().toString(), restaurantId.toString());
    }
    
    public User getUserProfile(Long userId) {
        return getUser(userId);
    }
    
    public List<OrderResponse> getOrderHistory(Long userId) {
        return getUserOrders(userId);
    }
}
