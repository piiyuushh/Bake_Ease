package com.bakeease.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Utility class for managing session attributes in the application.
 * This class provides methods to set, get, remove, and invalidate session attributes.
 */
public class SessionUtil {

    /**
     * Sets an attribute in the session with the specified key and value.
     * If the session doesn't exist, a new session will be created.
     *
     * @param request the {@link HttpServletRequest} containing the session
     * @param key the key under which the attribute will be stored
     * @param value the value to store in the session
     */
    public static void setAttribute(HttpServletRequest request, String key, Object value) {
        HttpSession session = request.getSession();
        session.setAttribute(key, value);
    }

    /**
     * Retrieves the attribute stored in the session under the specified key.
     * If the session does not exist or the attribute is not found, {@code null} is returned.
     *
     * @param request the {@link HttpServletRequest} containing the session
     * @param key the key of the attribute to retrieve
     * @return the attribute value, or {@code null} if not found
     */
    public static Object getAttribute(HttpServletRequest request, String key) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return session.getAttribute(key);
        }
        return null;
    }

    /**
     * Removes an attribute from the session.
     * If the session does not exist or the attribute is not found, no action is taken.
     *
     * @param request the {@link HttpServletRequest} containing the session
     * @param key the key of the attribute to remove
     */
    public static void removeAttribute(HttpServletRequest request, String key) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(key);
        }
    }

    /**
     * Invalidates the session, removing all attributes and marking the session as invalid.
     * If the session does not exist, no action is taken.
     *
     * @param request the {@link HttpServletRequest} containing the session
     */
    public static void invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}