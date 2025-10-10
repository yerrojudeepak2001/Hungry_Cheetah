package com.foodapp.auth.dto;

import lombok.Data;
import java.util.List;

@Data
public class RolePermissions {
    private String role;
    private List<String> permissions;
}