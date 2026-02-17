package com.valkyrie.api.model;

public class Authenticator {
    private String username;
    private String password;
    private String role;

    public String getUsername() {
        return username;
    }
    public Authenticator setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }
    public Authenticator setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getRole() {
        return role;
    }
    public Authenticator setAuthenticator(String role) {
        this.role = role;
        return this;
    }
}
