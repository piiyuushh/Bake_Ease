package com.bakeease.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * Utility class for handling cookies in the application.
 * This class provides methods to add, get, and delete cookies.
 */
public class CookieUtil {

    /**
     * Adds a cookie to the response with the specified name, value, and maximum age.
     * The cookie is accessible across the entire web application.
     *
     * @param response the {@link HttpServletResponse} to which the cookie will be added
     * @param name the name of the cookie
     * @param value the value of the cookie
     * @param maxAge the maximum age of the cookie in seconds
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * Retrieves the cookie with the specified name from the request.
     * If the cookie exists, it returns the cookie, otherwise it returns {@code null}.
     *
     * @param request the {@link HttpServletRequest} from which the cookie will be retrieved
     * @param name the name of the cookie to retrieve
     * @return the {@link Cookie} object if found, or {@code null} if the cookie doesn't exist
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies())
                    .filter(cookie -> name.equals(cookie.getName()))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    /**
     * Deletes the cookie with the specified name by setting its value to {@code null} and max age to 0.
     * The cookie will be invalidated and removed from the client.
     *
     * @param response the {@link HttpServletResponse} to which the cookie deletion will be applied
     * @param name the name of the cookie to delete
     */
    public static void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}