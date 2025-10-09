package com.foodapp.common.dto;

import lombok.Data;
import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String phoneNumber;
    private List<AddressDTO> addresses;
    private Integer loyaltyPoints;
}