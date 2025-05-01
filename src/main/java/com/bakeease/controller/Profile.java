package com.bakeease.controller;

import com.bakeease.model.UserModel;
import com.bakeease.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/profile")
public class Profile extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the session, ensuring it exists and contains the username
        HttpSession session = request.getSession(false);

        // Redirect to login if user is not authenticated
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Fetch user from the database using the session's username
        String username = (String) session.getAttribute("username");
        UserModel user = userService.getUserByUsername(username);

        // If user is found, set it as a request attribute and forward to profile page
        if (user != null) {
            request.setAttribute("user", user);
            request.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(request, response);
        } else {
            // If the user is not found, send an error message and forward to error page
            request.setAttribute("error", "User not found.");
            request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);  // Use the GET handler for both GET and POST methods
    }
}