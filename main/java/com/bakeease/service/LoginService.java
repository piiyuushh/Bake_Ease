package com.bakeease.service;

import com.bakeease.config.DbConfig;
import com.bakeease.util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginService {

    public String authenticate(String username, String inputPassword) {
        String sql = "SELECT password, role FROM users WHERE username = ?";
        
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String encryptedPassword = rs.getString("password");
                    String role = rs.getString("role");

                    String decryptedPassword = PasswordUtil.decrypt(encryptedPassword, username);

                    if (decryptedPassword != null && decryptedPassword.equals(inputPassword)) {
                        return role; // ✅ Return role directly if authentication successful
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // Authentication failed
    }
}