package com.bakeease.service;

import com.bakeease.model.UserModel;
import com.bakeease.config.DbConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * The RegisterService class provides methods for user registration.
 * It handles adding new users to the database and checking for duplicate usernames or emails.
 */
public class RegisterService {

    /**
     * Adds a new user to the database.
     * 
     * @param user the UserModel object containing the user details to be added
     * @return {@code true} if the user is successfully added, {@code false} otherwise
     */
    public boolean addUser(UserModel user) {
        boolean isInserted = false;

        String sql = "INSERT INTO users (username, email, phone, password, role, image_path) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getRole());
            ps.setString(6, user.getImage_path());

            int rows = ps.executeUpdate();
            isInserted = rows > 0;

            System.out.println("Rows inserted: " + rows);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return isInserted;
    }

    /**
     * Checks if the given username or email already exists in the database.
     * 
     * @param username the username to check for duplication
     * @param email the email to check for duplication
     * @return {@code true} if the username or email already exists, {@code false} otherwise
     */
    public boolean isDuplicateUsernameOrEmail(String username, String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ? OR email = ?";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}