package com.osc.backend.dto;

import com.osc.backend.entity.User;

public class UserRequest {

    private String username;
    private String password;
    private User.Role role;
    private Long scopeEntityId;

    public UserRequest() {}

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public User.Role getRole() { return role; }
    public void setRole(User.Role role) { this.role = role; }

    public Long getScopeEntityId() { return scopeEntityId; }
    public void setScopeEntityId(Long scopeEntityId) { this.scopeEntityId = scopeEntityId; }
}
