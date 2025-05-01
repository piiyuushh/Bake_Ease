<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.bakeease.model.UserModel" %>

<%
    UserModel user = (UserModel) request.getAttribute("user");
    String contextPath = request.getContextPath();
    String errorMessage = (String) request.getAttribute("error");
    String successMessage = (String) request.getAttribute("success");

    String imageFileName = (user != null && user.getImage_path() != null && !user.getImage_path().isEmpty())
        ? user.getImage_path()
        : "default-profile.png";

    String imagePath = contextPath + "/resources/assets/customer-images/" + imageFileName;
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Profile - BakeEase</title>
    <link rel="stylesheet" href="<%= contextPath %>/css/profile.css">
</head>
<body>

<div class="container">

    <%-- Alert Messages --%>
    <% if (errorMessage != null) { %>
        <div class="alert error"><%= errorMessage %></div>
    <% } else if (successMessage != null) { %>
        <div class="alert success"><%= successMessage %></div>
    <% } %>

    <%-- Profile Content --%>
    <% if (user != null) { %>
    
        <%-- Left: Profile Card --%>
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

        <%-- Right: Update Form --%>
        <div class="form-section">
            <h2>Update Profile</h2>

            <form action="<%= contextPath %>/updateprofile" method="post" enctype="multipart/form-data">
                
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" value="<%= user.getUsername() %>" required>

                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="<%= user.getEmail() %>" required>

                <label for="phone">Phone Number:</label>
                <input type="text" id="phone" name="phone" value="<%= user.getPhone() %>" required>

                <label for="password">Password:</label>
                <input type="password" id="password" name="password" placeholder="Enter new password">

                <label for="profileImage">Profile Image:</label>
                <input type="file" id="profileImage" name="profileImage">

                <button type="submit" class="edit-btn">Update</button>
                <a href="<%= contextPath %>/home" class="home-btn">Go to Home</a>
            </form>
        </div>

    <% } else { %>
        <p>User not found.</p>
    <% } %>

</div>

</body>
</html>