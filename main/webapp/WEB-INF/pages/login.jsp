<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - Bake Ease</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/assets/favicon.png" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css" />
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;600&display=swap" rel="stylesheet">
    <style>
        .error-card {
            background-color: #ffe5e5;
            color: #b30000;
            border: 1px solid #ff9999;
            border-radius: 8px;
            padding: 3px 3px;
            margin-bottom: 20px;
            text-align: center;
            font-weight: 500;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
        }
    </style>
</head>
<body>
    <div class="page-content">
        <div class="auth-container">
            <h2>Welcome Back</h2>

            <!-- Error message in card-style block -->
            <%
                String error = (String) request.getAttribute("error");
                if (error != null) {
            %>
                <div class="error-card">
                    <p><%= error %></p>
                </div>
            <%
                }
            %>

            <!-- Login form -->
            <form class="auth-form active" action="login" method="post">
                <div class="form-group">
                    <label for="login-username">Username</label>
                    <input type="text" id="login-username" name="username" required>
                </div>
                <div class="form-group">
                    <label for="login-password">Password</label>
                    <input type="password" id="login-password" name="password" required>
                </div>
                <button type="submit" class="submit-btn">Login</button>
            </form>

            <!-- Navigation -->
            <p style="text-align: center; margin-top: 15px;">
                Don't have an account? <a href="${pageContext.request.contextPath}/register" style="color: #af734e;">Register here</a>
            </p>
            <a href="${pageContext.request.contextPath}/home">
                <button type="button" class="submit-btn" style="margin-top: 10px;">Back to Home</button>
            </a>
        </div>
    </div>
</body>
</html>