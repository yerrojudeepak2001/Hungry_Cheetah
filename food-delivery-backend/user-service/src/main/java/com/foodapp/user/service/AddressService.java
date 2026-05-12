package com.foodapp.user.service;

import com.foodapp.user.model.Address;
import com.foodapp.user.model.User;
import com.foodapp.user.repository.AddressRepository;
import com.foodapp.user.repository.UserRepository;
import com.foodapp.user.exception.ResourceNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressService(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Address addAddress(Long userId, Address address) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        address.setUser(user);

        // If this is the first address or marked as default, reset other default
        // addresses
        if (address.isDefault() || addressRepository.findByUserId(userId).isEmpty()) {
            address.setDefault(true);
            addressRepository.resetDefaultAddress(userId);
        }

        return addressRepository.save(address);
    }

    @Transactional
    public Address updateAddress(Long userId, Long addressId, Address updatedAddress) {
        Address existingAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + addressId));

        // Verify that the address belongs to the specified user
        if (!existingAddress.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("User is not authorized to update this address");
        }

        // Update fields
        existingAddress.setStreetAddress(updatedAddress.getStreetAddress());
        existingAddress.setCity(updatedAddress.getCity());
        existingAddress.setState(updatedAddress.getState());
        existingAddress.setPostalCode(updatedAddress.getPostalCode());
        existingAddress.setCountry(updatedAddress.getCountry());
        existingAddress.setAddressType(updatedAddress.getAddressType());
        existingAddress.setLabel(updatedAddress.getLabel());
        existingAddress.setApartment(updatedAddress.getApartment());
        existingAddress.setLandmark(updatedAddress.getLandmark());
        existingAddress.setLatitude(updatedAddress.getLatitude());
        existingAddress.setLongitude(updatedAddress.getLongitude());

        // Handle default address logic
        if (updatedAddress.isDefault() && !existingAddress.isDefault()) {
            addressRepository.resetDefaultAddress(userId);
            existingAddress.setDefault(true);
        }

        return addressRepository.save(existingAddress);
    }

    @Transactional
    public void deleteAddress(Long userId, Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + addressId));

        // Verify that the address belongs to the specified user
        if (!address.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("User is not authorized to delete this address");
        }

        addressRepository.delete(address);
    }

    public List<Address> getUserAddresses(Long userId) {
        // Verify user exists
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }

        return addressRepository.findByUserId(userId);
    }
}
