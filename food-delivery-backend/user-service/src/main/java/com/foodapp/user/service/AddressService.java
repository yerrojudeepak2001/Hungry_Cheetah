package com.foodapp.user.service;

import com.foodapp.user.model.Address;
import com.foodapp.user.model.User;
import com.foodapp.user.repository.AddressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Transactional
    public Address addAddress(User user, Address address) {
        address.setUser(user);
        if (address.isDefault()) {
            addressRepository.resetDefaultAddress(user.getId());
        }
        return addressRepository.save(address);
    }
    
    @Transactional
    public Address addAddress(Long userId, Address address) {
        // TODO: This would typically fetch the user from database
        User user = new User();
        user.setId(userId);
        address.setUser(user);
        
        if (address.isDefault()) {
            addressRepository.resetDefaultAddress(userId);
        }
        return addressRepository.save(address);
    }

    @Transactional
    public Address updateAddress(Long addressId, Address newAddress) {
        Address existingAddress = addressRepository.findById(addressId)
            .orElseThrow(() -> new RuntimeException("Address not found: " + addressId));

        existingAddress.setStreetAddress(newAddress.getStreetAddress());
        existingAddress.setCity(newAddress.getCity());
        existingAddress.setState(newAddress.getState());
        existingAddress.setPostalCode(newAddress.getPostalCode());
        existingAddress.setCountry(newAddress.getCountry());
        existingAddress.setApartment(newAddress.getApartment());
        existingAddress.setLandmark(newAddress.getLandmark());
        existingAddress.setAddressType(newAddress.getAddressType());
        existingAddress.setLabel(newAddress.getLabel());

        if (newAddress.isDefault() && !existingAddress.isDefault()) {
            addressRepository.resetDefaultAddress(existingAddress.getUser().getId());
            existingAddress.setDefault(true);
        }

        return addressRepository.save(existingAddress);
    }

    public Address getAddress(Long addressId) {
        return addressRepository.findById(addressId)
            .orElseThrow(() -> new RuntimeException("Address not found: " + addressId));
    }

    public List<Address> getUserAddresses(Long userId) {
        return addressRepository.findByUserId(userId);
    }

    @Transactional
    public void deleteAddress(Long addressId) {
        Address address = addressRepository.findById(addressId)
            .orElseThrow(() -> new RuntimeException("Address not found: " + addressId));
        addressRepository.delete(address);
    }
    
    @Transactional
    public void deleteAddress(Long userId, Long addressId) {
        Address address = addressRepository.findById(addressId)
            .orElseThrow(() -> new RuntimeException("Address not found: " + addressId));
        
        // Verify the address belongs to the user
        if (!address.getUser().getId().equals(userId)) {
            throw new RuntimeException("Address does not belong to user: " + userId);
        }
        
        addressRepository.delete(address);
    }
    
    @Transactional
    public Address updateAddress(Long userId, Long addressId, Address newAddress) {
        Address existingAddress = addressRepository.findById(addressId)
            .orElseThrow(() -> new RuntimeException("Address not found: " + addressId));
        
        // Verify the address belongs to the user
        if (!existingAddress.getUser().getId().equals(userId)) {
            throw new RuntimeException("Address does not belong to user: " + userId);
        }
        
        // Update address fields
        existingAddress.setStreetAddress(newAddress.getStreetAddress());
        existingAddress.setCity(newAddress.getCity());
        existingAddress.setState(newAddress.getState());
        existingAddress.setPostalCode(newAddress.getPostalCode());
        existingAddress.setCountry(newAddress.getCountry());
        existingAddress.setApartment(newAddress.getApartment());
        existingAddress.setLandmark(newAddress.getLandmark());
        existingAddress.setAddressType(newAddress.getAddressType());
        existingAddress.setLabel(newAddress.getLabel());
        existingAddress.setLatitude(newAddress.getLatitude());
        existingAddress.setLongitude(newAddress.getLongitude());
        
        if (newAddress.isDefault()) {
            addressRepository.resetDefaultAddress(userId);
            existingAddress.setDefault(true);
        }
        
        return addressRepository.save(existingAddress);
    }
    
    @Transactional
    public Address setDefaultAddress(Long addressId, Long userId) {
        Address address = addressRepository.findById(addressId)
            .orElseThrow(() -> new RuntimeException("Address not found: " + addressId));
        
        // Reset all default addresses for the user
        addressRepository.resetDefaultAddress(userId);
        
        // Set the new default
        address.setDefault(true);
        return addressRepository.save(address);
    }
}