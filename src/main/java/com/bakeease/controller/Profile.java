package com.bakeease.controller;

import com.bakeease.model.UserModel;
import com.bakeease.service.UserService;
import com.bakeease.util.UpdateProfile;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/profile")
@MultipartConfig
public class Profile extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String username = (String) session.getAttribute("username");
        UserModel user = userService.getUserByUsername(username);

        if (user != null) {
            request.setAttribute("user", user);
            request.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "User not found.");
            request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            boolean updated = UpdateProfile.updateUser(request);
            if (updated) {
                request.setAttribute("success", "Profile updated successfully.");
            } else {
                request.setAttribute("error", "Profile update failed or user not logged in.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred: " + e.getMessage());
        }

        // Refresh updated user info for display
        doGet(request, response);
    }
}