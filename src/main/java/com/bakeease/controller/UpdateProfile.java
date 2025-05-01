package com.bakeease.controller;

import com.bakeease.service.UserService;
import com.bakeease.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;

@WebServlet("/updateprofile")
@MultipartConfig
public class UpdateProfile extends HttpServlet {

    private static final long serialVersionUID = 1L;
	private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String currentUsername = (String) SessionUtil.getAttribute(request, "username");

        if (currentUsername == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String newUsername = request.getParameter("username");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        Part imagePart = request.getPart("profileImage");

        String uploadPath = getServletContext().getRealPath("/") + File.separator + "resources" +
                File.separator + "assets" + File.separator + "customer-images";

        try {
            boolean success = userService.updateUserProfile(newUsername, email, phone, password, imagePart, currentUsername, uploadPath);

            if (success) {
                SessionUtil.setAttribute(request, "username", newUsername);
                request.setAttribute("success", "Profile updated successfully.");
            } else {
                request.setAttribute("error", "Profile update failed.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred: " + e.getMessage());
        }

        request.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(request, response);
    }
}