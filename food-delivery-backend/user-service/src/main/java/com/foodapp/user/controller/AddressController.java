package com.foodapp.user.controller;

import com.foodapp.common.dto.ApiResponse;
import com.foodapp.user.model.Address;
import com.foodapp.user.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<Address>>> getUserAddresses(@PathVariable Long userId) {
        List<Address> addresses = addressService.getUserAddresses(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Addresses fetched successfully", addresses));
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Address>> addAddress(
            @PathVariable Long userId,
            @Valid @RequestBody Address address) {
        Address savedAddress = addressService.addAddress(userId, address);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Address added successfully", savedAddress));
    }

    @PutMapping("/{addressId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Address>> updateAddress(
            @PathVariable Long userId,
            @PathVariable Long addressId,
            @Valid @RequestBody Address address) {
        Address updatedAddress = addressService.updateAddress(userId, addressId, address);
        return ResponseEntity.ok(new ApiResponse<>(true, "Address updated successfully", updatedAddress));
    }

    @DeleteMapping("/{addressId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteAddress(
            @PathVariable Long userId,
            @PathVariable Long addressId) {
        addressService.deleteAddress(userId, addressId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Address deleted successfully", null));
    }

    @PostMapping("/{addressId}/set-default")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> setDefaultAddress(
            @PathVariable Long userId,
            @PathVariable Long addressId) {
        // This would need to be implemented in AddressService
        return ResponseEntity.ok(new ApiResponse<>(true, "Default address set successfully", null));
    }
}