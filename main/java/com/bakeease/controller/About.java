package com.bakeease.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The About servlet handles HTTP requests for the "/about" URL.
 * It is used to forward users to the "about.jsp" page located in the "WEB-INF/pages" directory.
 * 
 * This servlet responds to both GET and POST requests.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/about" })
public class About extends HttpServlet {
    
	private static final long serialVersionUID = 1L;
       
    /**
     * Default constructor.
     * Calls the superclass constructor (HttpServlet).
     */
    public About() {
        super();
        // Default constructor
    }

	/**
	 * Handles GET requests for the "/about" URL.
	 * Forwards the request to the "about.jsp" page.
	 * 
	 * @param req the HttpServletRequest object that contains the request the client made to the servlet
	 * @param resp the HttpServletResponse object that contains the response the servlet returns to the client
	 * @throws ServletException if the request could not be handled
	 * @throws IOException if an input or output error is detected when the servlet handles the request
	 */
    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("WEB-INF/pages/about.jsp").forward(req, resp);
	}

	/**
	 * Handles POST requests for the "/about" URL.
	 * Delegates handling to the doGet() method, treating POST requests the same as GET requests.
	 * 
	 * @param request the HttpServletRequest object that contains the request the client made to the servlet
	 * @param response the HttpServletResponse object that contains the response the servlet returns to the client
	 * @throws ServletException if the request could not be handled
	 * @throws IOException if an input or output error is detected when the servlet handles the request
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}