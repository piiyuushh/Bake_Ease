<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home - Bake Ease</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/assets/favicon.png" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/home.css" />
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;600;700&display=swap" rel="stylesheet">
</head>
<body>
    <jsp:include page="header.jsp" />

    <main>
        <!-- Hero Banner Section -->
        <section class="hero-banner" style="background: linear-gradient(rgba(0,0,0,0.5), rgba(0,0,0,0.5)), url('${pageContext.request.contextPath}/resources/assets/banner.png') no-repeat center center/cover;">
	    <div class="hero-content">
	        <h1>Freshly Baked Items</h1>
	        <p>Experience the joy of handcrafted pastries and desserts from our bakery.</p>
	        <a href="#" class="hero-btn">Order Now</a>
	    </div>
	</section>

        <!-- Featured Products Section -->
        <section class="featured-section">
            <h2>Featured Products</h2>
            <div class="product-grid">
                <div class="product-card">
                    <img src="${pageContext.request.contextPath}/resources/products-images/african cake.png" alt="African Cake">
                    <h3>African Cake</h3>
                    <p>Rich, moist, and full of cocoa goodness.</p>
                </div>
                <div class="product-card">
                    <img src="${pageContext.request.contextPath}/resources/products-images/birthday cake.png" alt="Birthday Cake">
                    <h3>Birthday Cake</h3>
                    <p>Perfect crust with a soft, chewy interior.</p>
                </div>
                <div class="product-card">
                    <img src="${pageContext.request.contextPath}/resources/products-images/creampied doughnuts.png" alt="Doughnuts">
                    <h3>Doughnuts</h3>
                    <p>Crispy edges, soft center, sweet perfection.</p>
                </div>
                <div class="product-card">
                    <img src="${pageContext.request.contextPath}/resources/products-images/creampied doughnuts.png" alt="Doughnuts">
                    <h3>Doughnuts</h3>
                    <p>Crispy edges, soft center, sweet perfection.</p>
                </div>
            </div>
        </section>
    </main>

    <jsp:include page="footer.jsp" />
</body>
</html>