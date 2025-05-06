package com.bakeease.filter;

import com.bakeease.util.CookieUtil;
import com.bakeease.util.SessionUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(asyncSupported = true, urlPatterns = "/*")
public class AuthenticationFilter implements Filter {

    private static final String[] PUBLIC_PATHS = {
        "/", "/home", "/about", "/login", "/register", "/contact", "/product"
    };

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();

        boolean isLoggedIn = SessionUtil.getAttribute(req, "username") != null;
        String role = CookieUtil.getCookie(req, "role") != null
                ? CookieUtil.getCookie(req, "role").getValue()
                : null;

        // Allow static resources (CSS, JS, images, fonts, etc.)
        if (uri.matches(".*(\\.css|\\.js|\\.png|\\.jpg|\\.jpeg|\\.gif|\\.svg|\\.woff|\\.woff2|\\.ttf|\\.eot)$")) {
            chain.doFilter(request, response);
            return;
        }

        // Allow public paths to everyone
        for (String path : PUBLIC_PATHS) {
            if (uri.equals(req.getContextPath() + path) || uri.equals(path)) {
                chain.doFilter(request, response);
                return;
            }
        }

        // Admin-only path
        if (uri.equals(req.getContextPath() + "/admin")) {
            if (isLoggedIn && "admin".equals(role)) {
                chain.doFilter(request, response);
            } else {
                res.sendRedirect(req.getContextPath() + "/login");
            }
            return;
        }

        // Profile page – allow both admin and customer
        if (uri.equals(req.getContextPath() + "/profile")) {
            if (isLoggedIn && ("admin".equals(role) || "customer".equals(role))) {
                chain.doFilter(request, response);
            } else {
                res.sendRedirect(req.getContextPath() + "/login");
            }
            return;
        }

        // Default case – block and redirect to login
        res.sendRedirect(req.getContextPath() + "/login");
    }

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {}
}