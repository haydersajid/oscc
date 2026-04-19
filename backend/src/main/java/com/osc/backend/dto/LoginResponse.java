package com.osc.backend.dto;

public class LoginResponse {

    private String token;
    private String username;
    private String role;

    public LoginResponse() {}

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public static LoginResponseBuilder builder() { return new LoginResponseBuilder(); }

    public static class LoginResponseBuilder {
        private String token;
        private String username;
        private String role;

        public LoginResponseBuilder token(String token) { this.token = token; return this; }
        public LoginResponseBuilder username(String username) { this.username = username; return this; }
        public LoginResponseBuilder role(String role) { this.role = role; return this; }

        public LoginResponse build() {
            LoginResponse r = new LoginResponse();
            r.token = this.token; r.username = this.username; r.role = this.role;
            return r;
        }
    }
}
