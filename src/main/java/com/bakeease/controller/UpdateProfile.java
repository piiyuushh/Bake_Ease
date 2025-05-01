package com.bakeease.controller;

import com.bakeease.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/updateprofile")
@MultipartConfig
public class UpdateProfile extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Use POST method to update profile.");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get form fields
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String currentUsername = (String) session.getAttribute("username");
        Part filePart = request.getPart("profileImage");

        // Resolve real upload directory path
        String uploadPath = request.getServletContext().getRealPath("/resources/assets/customer-images");

        try {
            UserService userService = new UserService();
            boolean isUpdated = userService.updateUserProfile(username, email, phone, password, filePart, currentUsername, uploadPath);

            if (isUpdated) {
                session.setAttribute("username", username); // Update session if username changed
                response.sendRedirect("profile"); // Redirect to profile page after updating
            } else {
                response.sendRedirect("error.jsp"); // Redirect to error page if update failed
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}