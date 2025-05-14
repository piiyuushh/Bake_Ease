package com.bakeease.controller;

import com.bakeease.model.UserModel;
import com.bakeease.service.RegisterService;
import com.bakeease.util.PasswordUtil;
import com.bakeease.util.Validationutil;
import com.bakeease.util.ImageUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;

/**
 * Servlet implementation class Register.
 *
 * Handles user registration by validating user input, checking for duplicates,
 * encrypting passwords, uploading profile images, and persisting new users to the database.
 *
 * URL Pattern: /register
 *
 * Author: Piyush Karn
 */
@WebServlet("/register")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,       // 1MB buffer before writing to disk
    maxFileSize = 5 * 1024 * 1024,         // Max file size: 5MB
    maxRequestSize = 10 * 1024 * 1024      // Max request size: 10MB
)
public class Register extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final RegisterService registerService = new RegisterService();
    private final ImageUtil imageUtil = new ImageUtil();

    /**
     * Handles GET requests to display the registration page.
     *
     * @param request  the HttpServletRequest object that contains the request
     * @param response the HttpServletResponse object that contains the response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
    }

    /**
     * Handles POST requests to register a new user.
     * This includes:
     * - Input validation
     * - Duplicate username/email check
     * - Password encryption
     * - Image file validation and upload
     * - User creation and database persistence
     *
     * @param request  the HttpServletRequest object that contains the form data
     * @param response the HttpServletResponse object that contains the response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // Extract form fields
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String phone = request.getParameter("phone");
        Part image = request.getPart("image");

        boolean isValid = true;

        // Input validations
        if (Validationutil.isNullOrEmpty(username)) {
            request.setAttribute("usernameError", "Username is required.");
            isValid = false;
        } else if (!username.matches("^[a-zA-Z][a-zA-Z0-9_]{2,19}$")) {
            request.setAttribute("usernameError", "Username must start with a letter and be 3-20 characters long.");
            isValid = false;
        }

        if (Validationutil.isNullOrEmpty(email)) {
            request.setAttribute("emailError", "Email is required.");
            isValid = false;
        } else if (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            request.setAttribute("emailError", "Please enter a valid email address.");
            isValid = false;
        }

        if (Validationutil.isNullOrEmpty(password)) {
            request.setAttribute("passwordError", "Password is required.");
            isValid = false;
        } else if (password.length() < 6) {
            request.setAttribute("passwordError", "Password must be at least 6 characters long.");
            isValid = false;
        }

        if (Validationutil.isNullOrEmpty(confirmPassword)) {
            request.setAttribute("confirmPasswordError", "Confirm Password is required.");
            isValid = false;
        } else if (!password.equals(confirmPassword)) {
            request.setAttribute("confirmPasswordError", "Password and Confirm Password do not match.");
            isValid = false;
        }

        if (Validationutil.isNullOrEmpty(phone)) {
            request.setAttribute("phoneError", "Phone number is required.");
            isValid = false;
        } else if (!phone.matches("^98[0-9]{8}$")) {
            request.setAttribute("phoneError", "Phone number must start with '98' and be 10 digits.");
            isValid = false;
        }

        String fileName = null;
        if (image == null || image.getSize() == 0) {
            request.setAttribute("imageError", "Image field is empty. Please upload an image.");
            isValid = false;
        } else if (!Validationutil.isValidImageExtension(image)) {
            request.setAttribute("imageError", "Only upload png, jpg and jpeg image.");
            isValid = false;
        } else {
            fileName = imageUtil.getImageNameFromPart(image);
            boolean uploadSuccess = imageUtil.uploadImage(image, getServletContext().getRealPath(""), "/customer-images");
            if (!uploadSuccess) {
                request.setAttribute("imageError", "Oops! Couldn’t upload the image. Give it another shot.");
                isValid = false;
            }
        }

        // Check for duplicate username or email
        if (isValid && registerService.isDuplicateUsernameOrEmail(username, email)) {
            request.setAttribute("duplicateError", "Duplicate entries found. Please choose another username and email.");
            isValid = false;
        }

        // If validation fails, reload form with entered values
        if (!isValid) {
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.setAttribute("phone", phone);
            request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
            return;
        }

        // Encrypt password before saving
        String encryptedPassword = PasswordUtil.encrypt(username, password);
        if (encryptedPassword == null) {
            request.setAttribute("error", "Error encrypting password. Try again.");
            request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
            return;
        }

        // Create and register new user
        String role = "customer";
        UserModel user = new UserModel(username, email, phone, encryptedPassword, role, fileName);

        boolean isRegistered = registerService.addUser(user);
        if (isRegistered) {
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            request.setAttribute("error", "Registration failed. Try again.");
            request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
        }
    }
}