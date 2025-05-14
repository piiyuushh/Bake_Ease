package com.bakeease.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The Home servlet handles HTTP requests for the website's home page.
 * It supports both GET and POST methods and routes users to `home.jsp`.
 * 
 * URL patterns handled:
 * - /home
 * - /
 * 
 * Author: Piyush Karn
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/home", "/"})
public class Home extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles HTTP GET requests and forwards the user to the home page (home.jsp).
     * 
     * @param request  the HttpServletRequest object that contains the client's request
     * @param response the HttpServletResponse object that contains the servlet's response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs while forwarding the request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/pages/home.jsp").forward(request, response);
    }

    /**
     * Handles HTTP POST requests, typically from forms submitted on the home page.
     * Retrieves form input parameters such as "username" and "message", sets them
     * as request attributes, and forwards the request to home.jsp for display.
     * 
     * @param request  the HttpServletRequest object that contains the client's request
     * @param response the HttpServletResponse object that contains the servlet's response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs while forwarding the request
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Example of retrieving form input data
        String username = request.getParameter("username");
        String message = request.getParameter("message");

        // Set attributes to pass data to JSP
        request.setAttribute("username", username);
        request.setAttribute("message", message);

        // Forward the request to the home.jsp page with data
        request.getRequestDispatcher("WEB-INF/pages/home.jsp").forward(request, response);
    }
}