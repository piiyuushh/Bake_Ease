<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Profile - Bake Ease</title>
    <link rel="icon" type="image/png" href="resources/assets/favicon.png" />
    <link rel="stylesheet" type="text/css" href="css/profile.css" />
</head>
<body>
    <div class="container">
        <!-- Left Section: Profile Info -->
        <div class="profile-card">
            <img src="resources/assets/default-profile.png" alt="Profile Image" class="profile-img">
            <h2>Piyush Karn</h2>
            <p>Customer</p>
            <div class="details">
                <p><strong>Username:</strong> piyush@1</p>
                <p><strong>Email:</strong> piyush@example.com</p>
                <p><strong>Phone:</strong> 9800000000</p>
            </div>
            <button class="view-btn">View Public Profile</button>
        </div>

        <!-- Right Section: Update Form -->
        <div class="form-section">
            <h2>Update Profile</h2>
            <form method="post" action="UpdateProfileServlet" enctype="multipart/form-data">
                <label>Username:</label>
                <input type="text" name="username" placeholder="Enter username">

                <label>Email:</label>
                <input type="email" name="email" placeholder="Enter email">

                <label>Phone Number:</label>
                <input type="text" name="phone" placeholder="Enter phone number">

                <label>Password:</label>
                <input type="password" name="password" placeholder="Enter password">

                <label>Profile Image:</label>
                <input type="file" name="profileImage">

                <button type="submit" class="edit-btn">Update</button>
                <a href="${pageContext.request.contextPath}/home" class="home-btn">Go to Home</a>
            </form>
        </div>
    </div>
</body>
</html>