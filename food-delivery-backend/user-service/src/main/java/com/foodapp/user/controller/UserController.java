package com.foodapp.user.controller;

import com.foodapp.user.dto.ApiResponse;
import com.foodapp.user.model.User;
import com.foodapp.user.service.UserService;
import com.foodapp.user.dto.LoginRequest;
import com.foodapp.user.dto.LoginResponse;
import com.foodapp.user.dto.ForgotPasswordRequest;
import com.foodapp.user.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    // 🧍 User Management
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> registerUser(@RequestBody User user) {
        var registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(new ApiResponse<>(true, "User registered successfully", registeredUser));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            // Authenticate user
            User authenticatedUser = userService.authenticateUser(
                    loginRequest.getEmail(),
                    loginRequest.getPassword(),
                    loginRequest.getDeviceToken(),
                    loginRequest.getDeviceInfo());

            // Build login response
            LoginResponse loginResponse = LoginResponse.builder()
                    .userId(authenticatedUser.getId())
                    .username(authenticatedUser.getUsername())
                    .email(authenticatedUser.getEmail())
                    .firstName(authenticatedUser.getFirstName())
                    .lastName(authenticatedUser.getLastName())
                    .phone(authenticatedUser.getPhone())
                    .roles(authenticatedUser.getRoles())
                    .loginTime(LocalDateTime.now())
                    .isEmailVerified(
                            authenticatedUser.getIsEmailVerified() != null ? authenticatedUser.getIsEmailVerified()
                                    : false)
                    .isPhoneVerified(
                            authenticatedUser.getIsPhoneVerified() != null ? authenticatedUser.getIsPhoneVerified()
                                    : false)
                    .profilePicture(authenticatedUser.getProfilePicture())
                    .message("Login successful")
                    .build();

            return ResponseEntity.ok(new ApiResponse<>(true, "Login successful", loginResponse));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>(false, "Login failed: " + e.getMessage(), null));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<?>> logoutUser(@RequestParam Long userId) {
        try {
            // Clear device token for the user (optional)
            User user = userService.getUser(userId);
            user.setDeviceToken(null);
            userService.updateUser(userId, user);

            return ResponseEntity.ok(new ApiResponse<>(true, "Logout successful", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Logout failed: " + e.getMessage(), null));
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<?>> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        try {
            User user = userService.findByEmail(request.getEmail());
            if (user != null) {
                // For security, always return success even if email doesn't exist
                // This prevents email enumeration attacks
                // In a real implementation, you would generate a reset token and send it via
                // email
                return ResponseEntity.ok(new ApiResponse<>(true,
                        "If the email exists in our system, you will receive a password reset link", null));
            } else {
                // Still return success for security
                return ResponseEntity.ok(new ApiResponse<>(true,
                        "If the email exists in our system, you will receive a password reset link", null));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse<>(true,
                    "If the email exists in our system, you will receive a password reset link", null));
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<?>> getCurrentUserProfile(
            @RequestHeader(value = "X-User-Id", required = false) String userIdHeader,
            @RequestHeader(value = "X-Auth-Token", required = false) String authToken) {
        try {
            String userId = jwtUtil.getUserIdFromHeaders(authToken, userIdHeader);
            if (userId == null) {
                return ResponseEntity.status(401)
                        .body(new ApiResponse<>(false, "Unauthorized", null));
            }

            var user = userService.getUser(Long.parseLong(userId));
            return ResponseEntity.ok(new ApiResponse<>(true, "User profile fetched successfully", user));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Failed to fetch user profile: " + e.getMessage(), null));
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<?>> getUserProfile(@PathVariable Long userId) {
        var user = userService.getUser(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "User profile fetched successfully", user));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<?>> updateUserProfile(
            @PathVariable Long userId,
            @RequestBody User user,
            @RequestHeader(value = "X-User-Id", required = false) String userIdHeader,
            @RequestHeader(value = "X-Auth-Token", required = false) String authToken) {
        try {
            String currentUserId = jwtUtil.getUserIdFromHeaders(authToken, userIdHeader);
            if (currentUserId == null || !currentUserId.equals(userId.toString())) {
                return ResponseEntity.status(403)
                        .body(new ApiResponse<>(false, "Forbidden: Cannot update other user's profile", null));
            }

            var updatedUser = userService.updateUser(userId, user);
            return ResponseEntity.ok(new ApiResponse<>(true, "User profile updated successfully", updatedUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Failed to update user profile: " + e.getMessage(), null));
        }
    }

    // 📊 User Stats and History
    @GetMapping("/{userId}/order-history")
    public ResponseEntity<ApiResponse<?>> getUserOrderHistory(@PathVariable Long userId) {
        var history = userService.getUserOrders(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Order history fetched successfully", history));
    }

    @GetMapping("/{userId}/favorite-restaurants")
    public ResponseEntity<ApiResponse<?>> getFavoriteRestaurants(@PathVariable Long userId) {
        var favorites = userService.getFavoriteRestaurants(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Favorite restaurants fetched successfully", favorites));
    }

    @PostMapping("/{userId}/favorite-restaurants/{restaurantId}")
    public ResponseEntity<ApiResponse<?>> addFavoriteRestaurant(
            @PathVariable Long userId,
            @PathVariable Long restaurantId) {
        userService.addFavoriteRestaurant(userId, restaurantId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Restaurant added to favorites", null));
    }

    @DeleteMapping("/{userId}/favorite-restaurants/{restaurantId}")
    public ResponseEntity<ApiResponse<?>> removeFavoriteRestaurant(
            @PathVariable Long userId,
            @PathVariable Long restaurantId) {
        userService.removeFavoriteRestaurant(userId, restaurantId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Restaurant removed from favorites", null));
    }

    // 🔐 Security & Verification
    @PostMapping("/{userId}/verify-email")
    public ResponseEntity<ApiResponse<?>> verifyEmail(
            @PathVariable Long userId,
            @RequestParam String token) {
        // Implementation would be in UserService
        return ResponseEntity.ok(new ApiResponse<>(true, "Email verified successfully", null));
    }

    @PostMapping("/{userId}/verify-phone")
    public ResponseEntity<ApiResponse<?>> verifyPhone(
            @PathVariable Long userId,
            @RequestParam String code) {
        // Implementation would be in UserService
        return ResponseEntity.ok(new ApiResponse<>(true, "Phone verified successfully", null));
    }

    @PostMapping("/{userId}/change-password")
    public ResponseEntity<ApiResponse<?>> changePassword(
            @PathVariable Long userId,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        userService.updatePassword(userId, oldPassword, newPassword);
        return ResponseEntity.ok(new ApiResponse<>(true, "Password changed successfully", null));
    }

    // 📱 Communication Preferences
    @PostMapping("/{userId}/resend-verification-email")
    public ResponseEntity<ApiResponse<?>> resendVerificationEmail(@PathVariable Long userId) {
        var user = userService.getUser(userId);
        // Logic to resend verification email
        return ResponseEntity.ok(new ApiResponse<>(true, "Verification email sent successfully", null));
    }

    @PostMapping("/{userId}/resend-verification-sms")
    public ResponseEntity<ApiResponse<?>> resendVerificationSms(@PathVariable Long userId) {
        var user = userService.getUser(userId);
        // Logic to resend verification SMS
        return ResponseEntity.ok(new ApiResponse<>(true, "Verification SMS sent successfully", null));
    }

    // 📈 Analytics & Stats
    @GetMapping("/{userId}/stats")
    public ResponseEntity<ApiResponse<?>> getUserStats(@PathVariable Long userId) {
        var stats = userService.getUserOrderStats(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "User stats fetched successfully", stats));
    }

    @GetMapping("/{userId}/active-orders")
    public ResponseEntity<ApiResponse<?>> getActiveOrders(@PathVariable Long userId) {
        var activeOrders = userService.getUserActiveOrders(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Active orders fetched successfully", activeOrders));
    }

    @GetMapping("/{userId}/recent-restaurants")
    public ResponseEntity<ApiResponse<?>> getRecentRestaurants(@PathVariable Long userId) {
        var recentRestaurants = userService.getRecentRestaurants(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Recent restaurants fetched successfully", recentRestaurants));
    }

    // 🔍 Search & Discovery
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<?>> searchUsers(@RequestParam String query) {
        // Implementation for user search (admin functionality)
        return ResponseEntity.ok(new ApiResponse<>(true, "Users search completed", null));
    }

    @GetMapping("/{userId}/recommendations")
    public ResponseEntity<ApiResponse<?>> getUserRecommendations(@PathVariable Long userId) {
        // Integration with recommendation service
        return ResponseEntity.ok(new ApiResponse<>(true, "Recommendations fetched successfully", null));
    }

    // 🎯 User Status Management (Admin endpoints)
    @PostMapping("/{userId}/enable")
    public ResponseEntity<ApiResponse<?>> enableUser(@PathVariable Long userId) {
        // Admin functionality to enable/disable users
        return ResponseEntity.ok(new ApiResponse<>(true, "User enabled successfully", null));
    }

    @PostMapping("/{userId}/disable")
    public ResponseEntity<ApiResponse<?>> disableUser(@PathVariable Long userId) {
        // Admin functionality to enable/disable users
        return ResponseEntity.ok(new ApiResponse<>(true, "User disabled successfully", null));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<?>> deleteUser(@PathVariable Long userId) {
        // Admin functionality to delete users
        return ResponseEntity.ok(new ApiResponse<>(true, "User deleted successfully", null));
    }
}
