package com.osc.backend.dto;

import com.osc.backend.entity.User;

public class UserResponse {

    private Long id;
    private String username;
    private User.Role role;
    private Long scopeEntityId;

    public UserResponse(Long id, String username, User.Role role, Long scopeEntityId) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.scopeEntityId = scopeEntityId;
    }

    public static UserResponse fromEntity(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                user.getScopeEntityId()
        );
    }

    // Getters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public User.Role getRole() { return role; }
    public Long getScopeEntityId() { return scopeEntityId; }
}
