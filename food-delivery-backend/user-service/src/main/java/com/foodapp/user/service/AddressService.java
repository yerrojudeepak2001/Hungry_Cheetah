package com.foodapp.user.service;

import com.foodapp.user.model.Address;
import com.foodapp.user.model.User;
import com.foodapp.user.repository.AddressRepository;
import com.foodapp.user.repository.UserRepository;
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
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        address.setUser(user); // assign User entity

        if (address.isDefault()) {
            addressRepository.resetDefaultAddress(userId);
        }

        return addressRepository.save(address);
    }

    @Transactional
    public Address updateAddress(Long userId, Long addressId, Address newAddress) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

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
            addressRepository.resetDefaultAddress(userId);
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
    public void deleteAddress(Long addressId, Long id) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found: " + addressId));
        addressRepository.delete(address);
    }
}
