<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Footer</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css" />
</head>
<body>

<footer class="footer">
    <div class="footer-container">
        <div class="footer-brand">
            <h3>Bake Ease</h3>
            <p>Delighting your taste buds with fresh baked goods, every single day.</p>
        </div>

        <div class="footer-links">
            <h4>Quick Links</h4>
            <ul>
                <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
                <li><a href="${pageContext.request.contextPath}/about">About</a></li>
                <li><a href="${pageContext.request.contextPath}/contact">Contact</a></li>
            </ul>
        </div>

        <div class="footer-contact">
            <h4>Contact Us</h4>
            <p>Email: <a href="mailto:support@bakeease.com">support@bakeease.com</a></p>
            <p>Phone: <a href="tel:+9779898989898">+977-9898989898</a></p>
            <p>Location: Kathmandu, Nepal</p>
        </div>
    </div>

    <div class="footer-bottom">
        <p>&copy; <%= java.util.Calendar.getInstance().get(java.util.Calendar.YEAR) %> Bake Ease. All rights reserved.</p>
    </div>
</footer>

</body>
</html>