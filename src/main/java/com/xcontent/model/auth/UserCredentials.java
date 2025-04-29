package com.xcontent.model.auth;

public class UserCredentials {
    private String username;
    private String email;
    private String password;

    // Constructors
    public UserCredentials(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getters and setters
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
} 