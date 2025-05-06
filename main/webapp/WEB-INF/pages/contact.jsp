<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Contact Us</title>
<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/assets/favicon.png" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/contact.css" />
</head>
<body>
	<jsp:include page="header.jsp" />
    <div class="contact-container">
        <div class="contact-left">
            <h2>Contact Us</h2>
            <p>Our team is ready to serve you your favourite baked items at your footsteps. Let us know for any other future services.
            Thank You!</p>
        </div>
        <div class="contact-right">
            <h2>Contact Us</h2>
            <form action="submitContact.jsp" method="post">
                <input type="text" name="name" placeholder="Enter your name" required>
                <input type="email" name="email" placeholder="Enter your email" required>
                <textarea name="message" placeholder="Enter your message" required></textarea>
                <button type="submit">Submit</button>
            </form>
        </div>
    </div>
    	<jsp:include page="footer.jsp" />
</body>
</html>