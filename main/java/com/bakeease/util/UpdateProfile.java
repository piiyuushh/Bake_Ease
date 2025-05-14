package com.bakeease.util;

import com.bakeease.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.io.File;

/**
 * Utility class for handling profile updates for users.
 * This class contains methods to update a user's profile information, including username, email, phone number, password, and profile image.
 */
public class UpdateProfile {

    private static final UserService userService = new UserService();

    /**
     * Updates the profile of the currently logged-in user.
     * The user's profile data (username, email, phone, password, and profile image) is updated in the database.
     * The current session's username is updated to the new username if the update is successful.
     *
     * @param request the {@link HttpServletRequest} containing the user's data to update
     * @return {@code true} if the profile update was successful, {@code false} otherwise
     * @throws Exception if an error occurs during the profile update process
     */
    public static boolean updateUser(HttpServletRequest request) throws Exception {
        // Retrieve the current logged-in user's username from the session
        String currentUsername = (String) SessionUtil.getAttribute(request, "username");

        // If the user is not logged in (username is null), return false
        if (currentUsername == null) {
            return false;
        }

        // Retrieve the updated profile data from the request
        String newUsername = request.getParameter("username");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        Part imagePart = request.getPart("profileImage");

        // Define the path to store the user's profile image
        String uploadPath = request.getServletContext().getRealPath("/") + File.separator + "resources" +
                File.separator + "assets" + File.separator + "customer-images";

        // Call the UserService to update the user's profile in the database
        boolean success = userService.updateUserProfile(newUsername, email, phone, password, imagePart, currentUsername, uploadPath);

        // If the update is successful, update the session with the new username
        if (success) {
            SessionUtil.setAttribute(request, "username", newUsername);
        }

        // Return whether the update was successful or not
        return success;
    }
}