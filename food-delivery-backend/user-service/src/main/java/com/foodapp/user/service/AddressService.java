package com.foodapp.user.service;

import com.foodapp.user.model.Address;
import com.foodapp.user.model.User;
import com.foodapp.user.repository.AddressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
}