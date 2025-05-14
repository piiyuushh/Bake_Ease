package com.bakeease.model;

/**
 * UserModel represents a user record in the BakeEase application.
 * It maps directly to a row in the users table in the database.
 * Stores user credentials, role, and profile image path.
 */
public class UserModel {
    private String username;
    private String email;
    private String phone;
    private String password;
    private String role;
    private String image_path; // exact match with the DB column

    /**
     * Constructs a new UserModel with all fields.
     *
     * @param username    the username of the user
     * @param email       the email address of the user
     * @param phone       the phone number of the user
     * @param password    the encrypted password
     * @param role        the role assigned to the user (e.g., "customer", "admin")
     * @param image_path  the relative path to the user's profile image
     */
    public UserModel(String username, String email, String phone, String password, String role, String image_path) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
        this.image_path = image_path;
    }

    /**
     * Constructs a new UserModel with default role and without image path.
     * The role is set to "customer" by default.
     *
     * @param username  the username of the user
     * @param email     the email address of the user
     * @param phone     the phone number of the user
     * @param password  the encrypted password
     */
    public UserModel(String username, String email, String phone, String password) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = "customer"; // default role
    }

    /**
     * Gets the username.
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the email address.
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address.
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the phone number.
     * @return the phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number.
     * @param phone the phone number to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the encrypted password.
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the encrypted password.
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the user's role.
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the user's role.
     * @param role the role to set (e.g., "customer", "admin")
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Gets the image path of the user's profile picture.
     * @return the image path
     */
    public String getImage_path() {
        return image_path;
    }

    /**
     * Sets the image path of the user's profile picture.
     * @param image_path the image path to set
     */
    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
}