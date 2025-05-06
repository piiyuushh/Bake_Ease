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

public class UserService {

    // Get user by username
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

    // Update user profile
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
}