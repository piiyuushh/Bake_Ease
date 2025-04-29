package com.bakeease.controller;

import com.bakeease.service.LoginService;
import com.bakeease.util.CookieUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final LoginService loginService = new LoginService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String inputPassword = request.getParameter("password");

        String role = loginService.authenticate(username, inputPassword);

        if (role != null) {
            // Login successful
            HttpSession session = request.getSession();
            session.setAttribute("username", username);

            // Set role cookie
            CookieUtil.addCookie(response, "role", role, 60 * 60); // 1 hour validity

            if ("admin".equalsIgnoreCase(role)) {
                response.sendRedirect(request.getContextPath() + "/admin");
            } else {
                response.sendRedirect(request.getContextPath() + "/home");
            }
            return;
        }

        // Login failed
        request.setAttribute("error", "Invalid username or password.");
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
    }
}