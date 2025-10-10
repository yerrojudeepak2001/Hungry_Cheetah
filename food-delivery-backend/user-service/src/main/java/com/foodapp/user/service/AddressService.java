package com.foodapp.user.service;

import com.foodapp.user.model.Address;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    
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
    }
}