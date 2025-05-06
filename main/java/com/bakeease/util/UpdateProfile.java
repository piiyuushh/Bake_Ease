package com.bakeease.util;

import com.bakeease.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.io.File;

public class UpdateProfile {

    private static final UserService userService = new UserService();

    public static boolean updateUser(HttpServletRequest request) throws Exception {
        String currentUsername = (String) SessionUtil.getAttribute(request, "username");

        if (currentUsername == null) {
            return false;
        }

        String newUsername = request.getParameter("username");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        Part imagePart = request.getPart("profileImage");

        String uploadPath = request.getServletContext().getRealPath("/") + File.separator + "resources" +
                File.separator + "assets" + File.separator + "customer-images";

        boolean success = userService.updateUserProfile(newUsername, email, phone, password, imagePart, currentUsername, uploadPath);

        if (success) {
            SessionUtil.setAttribute(request, "username", newUsername);
        }

        return success;
    }
}