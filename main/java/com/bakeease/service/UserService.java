package com.bakeease.service;

import com.bakeease.config.DbConfig;
import com.bakeease.model.UserModel;
import com.bakeease.util.PasswordUtil;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The UserService class provides methods for handling user-related operations,
 * such as fetching a user by username, updating the user profile, and counting the number of customers.
 */
public class UserService {

    /**
     * Fetches a user from the database by their username.
     *
     * @param username the username of the user to fetch
     * @return a {@link UserModel} object representing the user, or {@code null} if no user was found
     */
    public UserModel getUserByUsername(String username) {
        UserModel user = null;
        String query = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new UserModel(
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("image_path")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    /**
     * Updates a user's profile information, including username, email, phone, password, and profile image.
     *
     * @param newUsername  the new username to set
     * @param email        the new email to set
     * @param phone        the new phone number to set
     * @param password     the new password to set (can be {@code null} if no change)
     * @param imagePart    the uploaded profile image (can be {@code null} if no change)
     * @param currentUsername the current username of the user to update
     * @param uploadPath   the directory path where profile images are saved
     * @return {@code true} if the profile is successfully updated, {@code false} otherwise
     * @throws SQLException          if a database error occurs
     * @throws IOException           if an I/O error occurs during file upload
     * @throws ClassNotFoundException if the database driver is not found
     */
    public boolean updateUserProfile(String newUsername, String email, String phone, String password, Part imagePart, String currentUsername, String uploadPath)
            throws SQLException, IOException, ClassNotFoundException {

        String encryptedPassword = null;
        if (password != null && !password.trim().isEmpty()) {
            encryptedPassword = PasswordUtil.encrypt(newUsername, password);
        }

        String fileName = null;
        if (imagePart != null && imagePart.getSize() > 0) {
            fileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
            File uploadsDir = new File(uploadPath);
            if (!uploadsDir.exists()) uploadsDir.mkdirs();

            File file = new File(uploadsDir, fileName);
            try (InputStream input = imagePart.getInputStream()) {
                Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }

        try (Connection conn = DbConfig.getDbConnection()) {
            StringBuilder sql = new StringBuilder("UPDATE users SET username = ?, email = ?, phone = ?");
            if (encryptedPassword != null) sql.append(", password = ?");
            if (fileName != null) sql.append(", image_path = ?");
            sql.append(" WHERE username = ?");

            PreparedStatement stmt = conn.prepareStatement(sql.toString());

            int index = 1;
            stmt.setString(index++, newUsername);
            stmt.setString(index++, email);
            stmt.setString(index++, phone);
            if (encryptedPassword != null) stmt.setString(index++, encryptedPassword);
            if (fileName != null) stmt.setString(index++, fileName);
            stmt.setString(index, currentUsername);

            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Counts the total number of users with the role "customer".
     *
     * @return the number of customers, or 0 if there was an error
     */
    public int countCustomers() {
        String query = "SELECT COUNT(*) AS customer_count FROM users WHERE role = 'customer'";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("customer_count");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return 0;
    }
}