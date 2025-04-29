<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register - Bake Ease</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/assets/favicon.png" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css" />
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;600&display=swap" rel="stylesheet">
    <style>
        .error-card {
            background-color: #ffe5e5;
            color: #b30000;
            border: 1px solid #ff9999;
            border-radius: 8px;
            padding: 10px 14px;
            margin-top: 8px;
            font-size: 14px;
            font-weight: 500;
        }
    </style>
</head>
<body>
    <div class="page-content">
        <div class="auth-container">
            <h2>Create Account</h2>
			<% if (request.getAttribute("duplicateError") != null) { %>
			    <div class="error-card"><%= request.getAttribute("duplicateError") %></div>
			<% } %>
            <form class="auth-form active" action="${pageContext.request.contextPath}/register" method="post" enctype="multipart/form-data">

                <!-- Username -->
                <div class="form-group">
                    <label for="register-username">Username</label>
                    <input type="text" id="register-username" name="username" value="<%= request.getAttribute("username") != null ? request.getAttribute("username") : "" %>">
                    <% if (request.getAttribute("usernameError") != null) { %>
                        <div class="error-card"><%= request.getAttribute("usernameError") %></div>
                    <% } %>
                </div>

                <!-- Email -->
                <div class="form-group">
                    <label for="register-email">Email</label>
                    <input type="email" id="register-email" name="email" value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>">
                    <% if (request.getAttribute("emailError") != null) { %>
                        <div class="error-card"><%= request.getAttribute("emailError") %></div>
                    <% } %>
                </div>

                <!-- Password -->
                <div class="form-group">
                    <label for="register-password">Password</label>
                    <input type="password" id="register-password" name="password">
                    <% if (request.getAttribute("passwordError") != null) { %>
                        <div class="error-card"><%= request.getAttribute("passwordError") %></div>
                    <% } %>
                </div>

                <!-- Confirm Password -->
                <div class="form-group">
                    <label for="register-confirm-password">Confirm Password</label>
                    <input type="password" id="register-confirm-password" name="confirmPassword">
                    <% if (request.getAttribute("confirmPasswordError") != null) { %>
                        <div class="error-card"><%= request.getAttribute("confirmPasswordError") %></div>
                    <% } %>
                </div>

                <!-- Phone Number -->
                <div class="form-group">
                    <label for="register-phone">Phone Number</label>
                    <input type="text" id="register-phone" name="phone" value="<%= request.getAttribute("phone") != null ? request.getAttribute("phone") : "" %>">
                    <% if (request.getAttribute("phoneError") != null) { %>
                        <div class="error-card"><%= request.getAttribute("phoneError") %></div>
                    <% } %>
                </div>

                <!-- Profile Image -->
                <div class="form-group">
                    <label for="register-image">Profile Image</label>
                    <input type="file" id="register-image" name="image" accept="image/*">
                    <% if (request.getAttribute("imageError") != null) { %>
                        <div class="error-card"><%= request.getAttribute("imageError") %></div>
                    <% } %>
                </div>

                <button type="submit" class="submit-btn">Register</button>
            </form>

            <p style="text-align: center; margin-top: 15px;">
                Already have an account? <a href="${pageContext.request.contextPath}/login" style="color: #af734e;">Login here</a>
            </p>

            <form action="${pageContext.request.contextPath}/home" method="get" style="margin-top: 10px;">
                <button type="submit" class="submit-btn">Back to Home</button>
            </form>
        </div>
    </div>
</body>
</html>