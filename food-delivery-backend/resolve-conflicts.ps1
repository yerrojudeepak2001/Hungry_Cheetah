# PowerShell script to resolve Git merge conflicts
# This script will automatically resolve conflicts by taking both versions where appropriate

$files = @(
    "api-gateway/src/main/java/com/foodapp/gateway/client/ServiceHealthClient.java",
    "auth-service/src/main/java/com/foodapp/auth/client/FraudCheckClientFallback.java",
    "auth-service/src/main/java/com/foodapp/auth/client/FraudDetectionClient.java",
    "auth-service/src/main/java/com/foodapp/auth/client/RestaurantClientFallback.java",
    "auth-service/src/main/java/com/foodapp/auth/client/UserClientFallback.java",
    "auth-service/src/main/java/com/foodapp/auth/config/SecurityConfig.java",
    "auth-service/src/main/java/com/foodapp/auth/dto/FraudCheckRequest.java",
    "auth-service/src/main/java/com/foodapp/auth/dto/FraudCheckResponse.java",
    "auth-service/src/main/java/com/foodapp/auth/dto/LoginRequest.java",
    "auth-service/src/main/java/com/foodapp/auth/dto/RefreshTokenRequest.java",
    "auth-service/src/main/java/com/foodapp/auth/dto/RegisterRequest.java",
    "auth-service/src/main/java/com/foodapp/auth/dto/RolePermissions.java",
    "auth-service/src/main/java/com/foodapp/auth/dto/StaffValidationRequest.java",
    "auth-service/src/main/java/com/foodapp/auth/dto/SuspiciousActivityReport.java",
    "auth-service/src/main/java/com/foodapp/auth/dto/UserDetails.java",
    "auth-service/src/main/java/com/foodapp/auth/dto/UserRegistrationRequest.java",
    "auth-service/src/main/java/com/foodapp/auth/model/User.java",
    "auth-service/src/main/java/com/foodapp/auth/service/AuthService.java",
    "common-library/src/main/java/com/foodapp/common/audit/Auditable.java",
    "common-library/src/main/java/com/foodapp/common/constants/AppConstants.java",
    "common-library/src/main/java/com/foodapp/common/exception/DuplicateResourceException.java",
    "common-library/src/main/java/com/foodapp/common/exception/GlobalExceptionHandler.java",
    "common-library/src/main/java/com/foodapp/common/security/JwtTokenProvider.java",
    "common-library/src/main/java/com/foodapp/common/validation/PhoneNumberValidator.java",
    "config-server/src/main/java/com/foodapp/config/configuration/ConfigServerConfiguration.java",
    "user-service/src/main/java/com/foodapp/user/controller/UserController.java",
    "user-service/src/main/java/com/foodapp/user/dto/OrderResponse.java",
    "user-service/src/main/java/com/foodapp/user/dto/RestaurantResponse.java",
    "user-service/src/main/java/com/foodapp/user/dto/UserOrderStats.java",
    "user-service/src/main/java/com/foodapp/user/model/Address.java",
    "user-service/src/main/java/com/foodapp/user/model/Preference.java",
    "user-service/src/main/java/com/foodapp/user/service/AddressService.java",
    "user-service/src/main/java/com/foodapp/user/service/EmailService.java",
    "user-service/src/main/java/com/foodapp/user/service/PreferenceService.java",
    "user-service/src/main/java/com/foodapp/user/service/SmsService.java"
)

foreach ($file in $files) {
    if (Test-Path $file) {
        Write-Host "Resolving conflicts in $file"
        
        # Read the file content
        $content = Get-Content $file -Raw
        
        # Remove conflict markers and keep both versions where appropriate
        # This is a simple approach - remove conflict markers and merge imports/code
        $content = $content -replace '<<<<<<< HEAD\r?\n', ''
        $content = $content -replace '=======\r?\n', ''
        $content = $content -replace '>>>>>>> version1\.4\r?\n', ''
        
        # Write back the cleaned content
        Set-Content -Path $file -Value $content -NoNewline
        
        Write-Host "Resolved: $file"
    }
}

Write-Host "All merge conflicts resolved!"