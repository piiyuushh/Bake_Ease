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
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profile - BakeEase</title>
    <link rel="stylesheet" href="<%= contextPath %>/css/profile.css">
        <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/assets/favicon.png" />
    
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>

<div class="profile-container">
    <%-- Error Message --%>
    <% if (errorMessage != null) { %>
        <div class="alert-message error">
            <span><%= errorMessage %></span>
            <button class="close-btn">&times;</button>
        </div>
    <% } %>

    <div class="profile-header">
        <h1>My Profile</h1>
        <p>Account Control Center</p>
    </div>

    <% if (user != null) { %>
    <div class="profile-content">
        <%-- Profile Card --%>
        <div class="profile-card">
            <div class="profile-image-container">
                <img src="<%= imagePath %>" alt="Profile Image" class="profile-image">
                <div class="image-overlay">
                    <label for="profileImage" class="upload-btn">Change Photo</label>
                </div>
            </div>
            
            <div class="profile-info">
                <h2><%= user.getUsername() %></h2>
                <p class="role-badge"><%= user.getRole() %></p>
                
            </div>
        </div>

        <%-- Edit Form --%>
        <div class="edit-form">
            <form action="<%= contextPath %>/profile" method="post" enctype="multipart/form-data">
                <input type="file" id="profileImage" name="profileImage" accept="image/*" style="display: none;">
                
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" id="username" name="username" value="<%= user.getUsername() %>" required>
                </div>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="email">Email Address</label>
                        <input type="email" id="email" name="email" value="<%= user.getEmail() %>" required>
                    </div>
                    <div class="form-group">
                        <label for="phone">Phone Number</label>
                        <input type="text" id="phone" name="phone" value="<%= user.getPhone() %>" required>
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="password">Change Password</label>
                    <input type="password" id="password" name="password" placeholder="Enter new password" required>
                    <small class="hint">* Please enter the current password if you don't want to update it before updating your profile. *</small>
                </div>
                
                <div class="form-actions">
                    <button type="submit" class="save-btn">Save Changes</button>
                    <a href="<%= contextPath %>/home" class="cancel-btn">Back to Home</a>
                </div>
            </form>
        </div>
    </div>
    <% } else { %>
        <div class="not-found">
            <img src="<%= contextPath %>/resources/assets/icons/user-not-found.svg" alt="User not found">
            <h2>User not found</h2>
            <p>We couldn't find your profile information</p>
            <a href="<%= contextPath %>/home" class="home-link">Return to Homepage</a>
        </div>
    <% } %>
</div>

<%-- Success Toast --%>
<% if (successMessage != null) { %>
    <div class="toast success">
        <div class="toast-content">
            <span class="toast-message"><%= successMessage %></span>
        </div>
        <button class="toast-close">&times;</button>
    </div>
<% } %>

<script>
    // Close buttons functionality
    document.addEventListener('DOMContentLoaded', function() {
        // Close alert message
        const closeButtons = document.querySelectorAll('.close-btn');
        closeButtons.forEach(btn => {
            btn.addEventListener('click', function() {
                this.parentElement.style.opacity = '0';
                setTimeout(() => this.parentElement.remove(), 300);
            });
        });
        
        // Toast notification
        const toast = document.querySelector('.toast');
        if (toast) {
            setTimeout(() => toast.classList.add('show'), 100);
            
            // Auto-close after 5 seconds
            setTimeout(() => {
                toast.classList.remove('show');
                setTimeout(() => toast.remove(), 300);
            }, 5000);
            
            // Manual close
            const closeBtn = toast.querySelector('.toast-close');
            closeBtn.addEventListener('click', () => {
                toast.classList.remove('show');
                setTimeout(() => toast.remove(), 300);
            });
        }
        
        // Preview image when selected
        const fileInput = document.getElementById('profileImage');
        const profileImage = document.querySelector('.profile-image');
        
        if (fileInput && profileImage) {
            fileInput.addEventListener('change', function(e) {
                if (e.target.files && e.target.files[0]) {
                    const reader = new FileReader();
                    reader.onload = function(event) {
                        profileImage.src = event.target.result;
                    };
                    reader.readAsDataURL(e.target.files[0]);
                }
            });
        }
    });
</script>

</body>
</html>