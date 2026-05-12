package com.foodapp.driver.client.fallback;

import com.foodapp.driver.client.UserClient;
import com.foodapp.driver.dto.*;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.ArrayList;

@Component
public class UserClientFallback implements UserClient {

    @Override
    public UserProfile getDriverProfile(String driverId) {
        // Return default profile when user service is unavailable
        UserProfile defaultProfile = new UserProfile();
        defaultProfile.setUserId(Long.parseLong(driverId));
        defaultProfile.setFirstName("Unknown");
        defaultProfile.setLastName("Driver");
        defaultProfile.setEmail("driver@unavailable.com");
        defaultProfile.setPhone("000-000-0000");
        defaultProfile.setVehicleType("Unknown");
        defaultProfile.setVerified(false);
        defaultProfile.setRating(0.0);
        return defaultProfile;
    }

    @Override
    public void updateDriverLocation(String driverId, LocationUpdate location) {
        // Do nothing when user service is unavailable
    }

    @Override
    public void updateDriverStatus(String driverId, DriverStatus status) {
        // Do nothing when user service is unavailable
    }

    @Override
    public List<DriverRating> getDriverRatings(String driverId) {
        // Return empty list when user service is unavailable
        return new ArrayList<>();
    }
}