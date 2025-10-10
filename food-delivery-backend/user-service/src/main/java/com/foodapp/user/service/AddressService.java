package com.foodapp.user.service;

import com.foodapp.user.model.Address;
<<<<<<< HEAD
import com.foodapp.user.model.User;
import com.foodapp.user.repository.AddressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
=======
import org.springframework.stereotype.Service;

>>>>>>> version1.4
import java.util.List;

@Service
public class AddressService {
<<<<<<< HEAD
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
=======
    
    public List<Address> getUserAddresses(Long userId) {
        // TODO: Implement address retrieval
        return List.of();
    }
    
    public Address saveAddress(Address address) {
        // TODO: Implement address saving
        return address;
    }
    
    public void deleteAddress(Long addressId) {
        // TODO: Implement address deletion
    }
    
    public void deleteAddress(Long userId, Long addressId) {
        // TODO: Implement address deletion with user validation 
    }
    
    public Address setDefaultAddress(Long addressId, String userId) {
        // TODO: Implement default address setting
        return new Address();
    }
    
    public Address addAddress(Long userId, Address address) {
        // TODO: Implement add address
        return address;
    }
    
    public Address updateAddress(Long userId, Long addressId, Address address) {
        // TODO: Implement update address
        return address;
>>>>>>> version1.4
    }
}