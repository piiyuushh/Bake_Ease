<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.bakeease.model.UserModel" %>

<%
    UserModel user = (UserModel) request.getAttribute("user");
    String contextPath = request.getContextPath();

    // Get the error message if any
    String errorMessage = (String) request.getAttribute("error");

    String imageFileName = (user != null && user.getImage_path() != null && !user.getImage_path().isEmpty())
        ? user.getImage_path()
        : "default-profile.png"; // fallback image

    String imagePath = contextPath + "/resources/assets/customer-images/" + imageFileName;
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Profile - Bake Ease</title>
    <link rel="icon" type="image/png" href="<%= contextPath %>/resources/assets/favicon.png" />
    <link rel="stylesheet" type="text/css" href="<%= contextPath %>/css/profile.css" />
</head>
<body>
    <div class="container">
        <%-- Display error message if any --%>
        <%
            if (errorMessage != null) {
        %>
            <div class="error-message">
                <p><%= errorMessage %></p>
            </div>
        <%
            }
        %>

        <%-- Left Section: Profile Info --%>
        <% if (user != null) { %>
        <div class="profile-card">
            <img src="<%= imagePath %>" alt="Profile Image" class="profile-img">
            <h2><%= user.getUsername() %></h2>
            <p><%= user.getRole() %></p>
            <div class="details">
                <p><strong>Username:</strong> <%= user.getUsername() %></p>
                <p><strong>Email:</strong> <%= user.getEmail() %></p>
                <p><strong>Phone:</strong> <%= user.getPhone() %></p>
            </div>
            <button class="view-btn">View Public Profile</button>
        </div>
        <% } else { %>
        <div class="profile-card">
            <p>User not found. Please contact support.</p>
        </div>
        <% } %>

        <%-- Right Section: Update Form --%>
        <% if (user != null) { %>
        <div class="form-section">
            <h2>Update Profile</h2>
			<form action="${pageContext.request.contextPath}/updateprofile" method="post" enctype="multipart/form-data">                <label>Username:</label>
                <input type="text" name="username" value="<%= user.getUsername() %>" placeholder="Enter username">

                <label>Email:</label>
                <input type="email" name="email" value="<%= user.getEmail() %>" placeholder="Enter email">

                <label>Phone Number:</label>
                <input type="text" name="phone" value="<%= user.getPhone() %>" placeholder="Enter phone number">

                <label>Password:</label>
                <input type="password" name="password" placeholder="Enter new password">

                <label>Profile Image:</label>
                <input type="file" name="profileImage">

                <button type="submit" class="edit-btn">Update</button>
                <a href="<%= contextPath %>/home" class="home-btn">Go to Home</a>
            </form>
        </div>
        <% } %>
    </div>
</body>
</html>