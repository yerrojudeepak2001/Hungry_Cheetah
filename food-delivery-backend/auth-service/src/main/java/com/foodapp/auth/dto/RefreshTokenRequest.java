package com.foodapp.auth.dto;

<<<<<<< HEAD
import jakarta.validation.constraints.NotBlank;

public class RefreshTokenRequest {
    @NotBlank(message = "Refresh token is required")
    private String refreshToken;

    // Getters and setters
    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
=======
import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String refreshToken;
>>>>>>> version1.4
}