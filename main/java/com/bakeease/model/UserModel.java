package com.bakeease.model;

/**
 * UserModel represents a user record that matches the database schema.
 */
public class UserModel {
    private String username;
    private String email;
    private String phone;
    private String password;
    private String role;
    private String image_path; // exact match with the DB column

    // Constructor with all fields
    public UserModel(String username, String email, String phone, String password, String role, String image_path) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
        this.image_path = image_path;
    }

    // Constructor without image_path and role (default role can be set later)
    public UserModel(String username, String email, String phone, String password) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = "customer"; // default role
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
}