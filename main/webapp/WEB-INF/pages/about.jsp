<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>About Us | Bake Ease</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/assets/favicon.png" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/about.css" />
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;600;700&family=Playfair+Display:wght@400;600&display=swap" rel="stylesheet">
</head>
<body>

    <jsp:include page="header.jsp" />

    <main class="page-content">
        <div class="about-container">
            
            <section class="about-section">
                <h1>Our Story</h1>
                <div class="about-content">
                    <p>At Bake Ease, we're more than just a bakery — we're a family-run business with a love for bringing smiles through the power of freshly baked goods. Our mission is to deliver delicious, handcrafted products that warm hearts and homes alike.</p>
                    <p>Rooted in tradition and passion, we value quality, consistency, and connection with our community. Every recipe we bake is a labor of love, and every customer, a part of our story.</p>
                </div>
            </section>

            <section class="team-section">
                <h2>Meet Our Team</h2>
                <p class="team-description">Our team at Bake Ease combines culinary expertise, creative flair, and warm hospitality to deliver delightful experiences.</p>

                <div class="team-flex">
                    <div class="team-card">
                        <img src="${pageContext.request.contextPath}/resources/assets/piyush.png" alt="Piyush Karn" class="team-photo" />
                        <div class="team-member-name">Piyush Karn</div>
                        <div class="team-member-role">CEO</div>
                        <a href="https://www.piyushkarn.com.np/" target="_blank" class="portfolio-btn">View Portfolio</a>
                    </div>

                    <div class="team-card">
                        <img src="${pageContext.request.contextPath}/resources/assets/aashutosh.png" alt="Aashutosh Dhakal" class="team-photo" />
                        <div class="team-member-name">Aashutosh Dhakal</div>
                        <div class="team-member-role">Operations Manager</div>
                        <a href="https://aashutoshdhakal.com.np/" target="_blank" class="portfolio-btn">View Portfolio</a>
                    </div>

                    <div class="team-card">
                        <img src="${pageContext.request.contextPath}/resources/assets/arpan.png" alt="Arpan Nepal" class="team-photo" />
                        <div class="team-member-name">Arpan Nepal</div>
                        <div class="team-member-role">Food Testing Manager</div>
                        <a href="https://arpannepal.vercel.app/" target="_blank" class="portfolio-btn">View Portfolio</a>
                    </div>
                </div>
            </section>

        </div>
    </main>

    <jsp:include page="footer.jsp" />

</body>
</html>