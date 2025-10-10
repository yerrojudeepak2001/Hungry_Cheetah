package com.foodapp.auth.dto;

<<<<<<< HEAD
import java.util.Set;

public class RolePermissions {
    private String role;
    private Set<String> permissions;

    // Getters and setters
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }
=======
import lombok.Data;
import java.util.List;

@Data
public class RolePermissions {
    private String role;
    private List<String> permissions;
>>>>>>> version1.4
}