<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Header</title>
<link rel="stylesheet" type="text/css"
    href="${pageContext.request.contextPath}/css/header.css" />
</head>
<body>
    <div class="top-bar">
        Bake Ease - Order your favourite baked items with ease at your doorsteps
    </div>
    <div class="navbar">
        <div class="nav-left">
            <a href="${pageContext.request.contextPath}/home">
                <img src="${pageContext.request.contextPath}/resources/assets/logo.png" alt="BakeEase Logo" class="logo-img" />
            </a>
        </div>
        <div class="nav-center">
            <a href="${pageContext.request.contextPath}/home">Home</a>
            <a href="${pageContext.request.contextPath}/product">Products</a>
            <a href="${pageContext.request.contextPath}/about">About Us</a>
            <a href="${pageContext.request.contextPath}/contact">Contact</a>
            <a href="${pageContext.request.contextPath}/profile">Profile</a>
        </div>
        <div class="nav-right">
            <div class="search-container">
                <input type="text" placeholder="Search Products" class="search-bar">
            </div>
            <a href="${pageContext.request.contextPath}/login" class="profile-link">
                <img src="${pageContext.request.contextPath}/resources/assets/profile.png" alt="Profile" class="profile-img" />
            </a>
        </div>
    </div>
</body>
</html>