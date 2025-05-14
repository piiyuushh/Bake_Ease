package com.bakeease.controller;

import com.bakeease.service.LoginService;
import com.bakeease.util.CookieUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * Servlet implementation class Login.
 * 
 * This servlet handles user login functionality using HTTP GET and POST methods.
 * - GET: Forwards to the login page.
 * - POST: Processes login form, authenticates the user, sets session and cookies,
 *         and redirects based on the user role.
 * 
 * URL Pattern: /login
 * 
 * Author: Piyush Karn
 */
@WebServlet("/login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final LoginService loginService = new LoginService();

    /**
     * Handles HTTP GET requests and forwards the request to the login JSP page.
     *
     * @param req  the HttpServletRequest object that contains the request the client made
     * @param resp the HttpServletResponse object that contains the response the servlet returns
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an input or output error occurs while forwarding
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
    }

    /**
     * Handles HTTP POST requests to process login form submission.
     * Authenticates the user and, on success, sets session and role cookie, 
     * then redirects based on user role. On failure, returns to the login page with error.
     *
     * @param request  the HttpServletRequest object that contains the client's request
     * @param response the HttpServletResponse object that contains the servlet's response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an input or output error is detected
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Ensure proper encoding for special characters
        request.setCharacterEncoding("UTF-8");

        // Get login form data
        String username = request.getParameter("username");
        String inputPassword = request.getParameter("password");

        // Authenticate using service
        String role = loginService.authenticate(username, inputPassword);

        if (role != null) {
            // Login successful: create session and store username
            HttpSession session = request.getSession();
            session.setAttribute("username", username);

            // Add role as cookie (expires in 1 hour)
            CookieUtil.addCookie(response, "role", role, 60 * 60);

            // Redirect user based on role
            if ("admin".equalsIgnoreCase(role)) {
                response.sendRedirect(request.getContextPath() + "/admin");
            } else {
                response.sendRedirect(request.getContextPath() + "/home");
            }
            return;
        }

        // Login failed: set error message and return to login page
        request.setAttribute("error", "Invalid username or password.");
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
    }
}