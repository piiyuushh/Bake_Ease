package com.bakeease.controller;

import com.bakeease.model.UserModel;
import com.bakeease.service.UserService;
import com.bakeease.util.UpdateProfile;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * Servlet implementation class Profile.
 *
 * This servlet handles viewing and updating the user's profile.
 * - GET: Loads the profile data of the logged-in user.
 * - POST: Updates the user's profile using the UpdateProfile utility.
 *
 * URL Pattern: /profile
 *
 * Author: Piyush Karn
 */
@WebServlet("/profile")
@MultipartConfig
public class Profile extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final UserService userService = new UserService();

    /**
     * Handles HTTP GET requests to display the user's profile.
     *
     * @param request  the HttpServletRequest containing client request
     * @param response the HttpServletResponse containing servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an input or output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Check if the user is logged in
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

    /**
     * Handles HTTP POST requests to update the user's profile.
     *
     * Uses a utility class (UpdateProfile) to process the form submission and update user data.
     * After processing, re-displays the profile with updated information or error messages.
     *
     * @param request  the HttpServletRequest containing multipart form data
     * @param response the HttpServletResponse for redirection or forwarding
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an input or output error occurs
     */
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

        // Reload profile info after update
        doGet(request, response);
    }
}