<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.bakeease.model.ProductModel" %>

<%
    String contextPath = request.getContextPath();
    List<ProductModel> products = (List<ProductModel>) request.getAttribute("products");
    String keyword = request.getParameter("search") != null ? request.getParameter("search").trim().toLowerCase() : "";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Products - Bake Ease</title>
    <link rel="icon" type="image/png" href="<%= contextPath %>/resources/assets/favicon.png" />
    <link rel="stylesheet" type="text/css" href="<%= contextPath %>/css/product.css" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<%@ include file="header.jsp" %>

<div class="product-page">
    <h1>Our Products</h1>

    <!-- Search Bar -->
    <form method="get" action="product" class="search-form">
        <input type="text" name="search" placeholder="Search by Name or Category" value="<%= keyword %>"/>
        <button type="submit">Search</button>
    </form>

    <div class="product-grid">
        <%
        boolean found = false;
        if (products != null && !products.isEmpty()) {
            for (ProductModel product : products) {
                boolean match = keyword.isEmpty() ||
                                product.getName().toLowerCase().contains(keyword) ||
                				product.getCategory().toLowerCase().contains(keyword);
                if (match) {
                    found = true;
        %>
        <div class="product-card">
            <div class="product-header">
                <h3><%= product.getName() %></h3>
                <span class="price">Rs. <%= product.getPrice() %></span>
            </div>
            <p class="category">[<%= product.getCategory() %>]</p>
            <p class="desc"><%= product.getDescription() %></p>
            <p class="total-sales">Sold: <%= product.getTotalSales() %> units</p>
            <div class="card-footer">
                <button class="order-btn">Order Now</button>
            </div>
        </div>
        <%      }
            }
        }
        if (!found) { %>
            <p>No products found.</p>
        <% } %>
    </div>
</div>

<%@ include file="footer.jsp" %>
</body>
</html>