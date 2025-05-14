package com.bakeease.service;

import com.bakeease.config.DbConfig;
import com.bakeease.util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * The LoginService class handles the authentication logic for users
 * in the BakeEase application. It verifies user credentials against
 * encrypted passwords stored in the database and returns the user's role
 * if authentication is successful.
 */
public class LoginService {

    /**
     * Authenticates a user by verifying the provided username and password
     * against the stored encrypted password in the database.
     *
     * @param username      the username entered by the user
     * @param inputPassword the plain text password entered by the user
     * @return the user's role (e.g., "customer", "admin") if authentication is successful,
     *         otherwise returns {@code null}
     */
    public String authenticate(String username, String inputPassword) {
        String sql = "SELECT password, role FROM users WHERE username = ?";
        
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set the username parameter for the prepared SQL query
            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Retrieve encrypted password and user role from database
                    String encryptedPassword = rs.getString("password");
                    String role = rs.getString("role");

                    // Decrypt the password using the PasswordUtil with username as key
                    String decryptedPassword = PasswordUtil.decrypt(encryptedPassword, username);

                    // Check if the decrypted password matches the input password
                    if (decryptedPassword != null && decryptedPassword.equals(inputPassword)) {
                        return role; // Authentication successful, return user role
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print exception for debugging (can be logged in real apps)
        }

        return null; // Authentication failed
    }
}