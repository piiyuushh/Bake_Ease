package com.bakeease.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class Home
 * Handles requests for the home page.
 * 
 * @author 
 * Piyush Karn
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/home", "/"})
public class Home extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles GET requests and forwards to home.jsp.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/pages/home.jsp").forward(request, response);
    }

    /**
     * Handles POST requests by retrieving form data and forwarding to home.jsp.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Example: Processing form inputs
        String username = request.getParameter("username");
        String message = request.getParameter("message");

        // Setting attributes to send back to JSP
        request.setAttribute("username", username);
        request.setAttribute("message", message);

        // Forwarding to home.jsp with attributes
        request.getRequestDispatcher("WEB-INF/pages/home.jsp").forward(request, response);
    }
}
