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

import java.util.List;
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

    @Transactional
    public User registerUser(User user) {
        // Validate unique fields
        validateUniqueFields(user);
        
        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Set default values
        user.setIsEnabled(true);
        user.setIsEmailVerified(false);
        user.setIsPhoneVerified(false);
        user.setRegistrationDate(LocalDateTime.now());
        
        // Initialize preferences
        UserPreference preferences = new UserPreference();
        preferences.setUser(user);
        user.setPreferences(preferences);
        
        // Save user
        User savedUser = userRepository.save(user);
        
        // Send verification emails/SMS
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
        
        // Update fields
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
        
        // Verify old password
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Invalid old password");
        }
        
        // Update password
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        
        // Notify user
        emailService.sendPasswordChangeNotification(user.getEmail());
    }

    @Transactional
    public User updatePreferences(Long userId, UserPreference preferences) {
        User user = getUser(userId);
        preferences.setUser(user);
        user.setPreferences(preferences);
        return userRepository.save(user);
    }

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
        String token = generateVerificationToken(user);
        emailService.sendVerificationEmail(user.getEmail(), token);
    }

    private void sendVerificationSms(User user) {
        String code = generateVerificationCode();
        smsService.sendVerificationSms(user.getPhone(), code);
    }

    private String generateVerificationToken(User user) {
        // Implement token generation logic
        return "token"; // Placeholder
    }

    private String generateVerificationCode() {
        // Implement verification code generation logic
        return "123456"; // Placeholder
    }
<<<<<<< HEAD

    public List<OrderResponse> getUserOrders(Long userId) {
        User user = getUser(userId); // Verify user exists
        return orderClient.getUserOrders(user.getId().toString());
    }

    public List<OrderResponse> getUserActiveOrders(Long userId) {
        User user = getUser(userId); // Verify user exists
        return orderClient.getUserActiveOrders(user.getId().toString());
    }

    public UserOrderStats getUserOrderStats(Long userId) {
        User user = getUser(userId); // Verify user exists
        return orderClient.getUserOrderStats(user.getId().toString());
    }

    public List<RestaurantResponse> getFavoriteRestaurants(Long userId) {
        User user = getUser(userId); // Verify user exists
        return restaurantClient.getUserFavoriteRestaurants(user.getId().toString());
    }

    public List<RestaurantResponse> getRecentRestaurants(Long userId) {
        User user = getUser(userId); // Verify user exists
        return restaurantClient.getUserRecentRestaurants(user.getId().toString());
=======
    
    public User getUserProfile(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    public List<String> getOrderHistory(Long userId) {
        // TODO: Implement order history retrieval
        return List.of();
    }
    
    public List<String> getFavoriteRestaurants(Long userId) {
        // TODO: Implement favorite restaurants retrieval
        return List.of();
    }
    
    public void addFavoriteRestaurant(Long userId, Long restaurantId) {
        // TODO: Implement add favorite restaurant
    }
    
    public void removeFavoriteRestaurant(Long userId, Long restaurantId) {
        // TODO: Implement remove favorite restaurant
>>>>>>> version1.4
    }
}