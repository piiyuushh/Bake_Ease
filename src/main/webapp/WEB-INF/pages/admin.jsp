<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.bakeease.model.ProductModel" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.HashSet" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/assets/favicon.png" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin.css" />
</head>
<body>

<div class="sidebar">
    <div class="sidebar-title">Admin</div>
    <button class="tab-btn" onclick="showTab('dashboard')">Dashboard</button>
    <button class="tab-btn" onclick="showTab('products')">Products</button>
</div>

<div class="main-content">
    <% String successMessage = (String) request.getAttribute("successMessage"); %>
    <% if (successMessage != null) { %>
        <div class="alert success"><%= successMessage %></div>
    <% } %>

    <!-- Dashboard Tab -->
    <div id="dashboard" class="tab-content active">
        <h2 class="dashboard-title">Admin Dashboard Overview</h2>

        <div class="dashboard-table">
            <div class="dashboard-cell">
                <div class="dashboard-label">Total Sales</div>
                <div class="dashboard-value">Rs. <%= request.getAttribute("totalSales") != null ? request.getAttribute("totalSales") : 0 %></div>
            </div>
            <div class="dashboard-cell">
                <div class="dashboard-label">Items</div>
                <div class="dashboard-value"><%= request.getAttribute("items") != null ? request.getAttribute("items") : 0 %></div>
            </div>
            <div class="dashboard-cell">
                <div class="dashboard-label">Categories</div>
                <div class="dashboard-value"><%= request.getAttribute("categories") != null ? request.getAttribute("categories") : 0 %></div>
            </div>
            <div class="dashboard-cell">
                <div class="dashboard-label">Customers</div>
                <div class="dashboard-value"><%= request.getAttribute("customers") != null ? request.getAttribute("customers") : 0 %></div>
            </div>
        </div>

        <h3>Current Product Listings</h3>
        <%
            List<ProductModel> productList = (List<ProductModel>) request.getAttribute("products");
            if (productList != null && !productList.isEmpty()) {
        %>
            <table class="product-table">
                <thead>
                    <tr>
                        <th>ID</th><th>Name</th><th>Description</th><th>Price</th><th>Category</th><th>Total Sales</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (ProductModel product : productList) { %>
                        <tr>
                            <td><%= product.getId() %></td>
                            <td><%= product.getName() %></td>
                            <td><%= product.getDescription() %></td>
                            <td>Rs. <%= product.getPrice() %></td>
                            <td><%= product.getCategory() %></td>  <!-- Display Category -->
                            <td><%= product.getTotalSales() %></td> <!-- Display Total Sales -->
                        </tr>
                    <% } %>
                </tbody>
            </table>
        <% } else { %>
            <p>No products available.</p>
        <% } %>
    </div>

    <!-- Products Tab -->
    <div id="products" class="tab-content">
        <h2>Manage Products</h2>

        <!-- Add Product Form -->
        <form action="${pageContext.request.contextPath}/admin" method="post" class="product-form">
            <input type="hidden" name="action" value="add">
            <input type="text" name="name" placeholder="Product Name" required>
            <input type="text" name="description" placeholder="Description" required>
            <input type="number" step="10" name="price" placeholder="Price" required>
            <input type="number" step="10" name="total_sales" placeholder="Total Sales" required>
            <select name="category" required>
                <option value="" disabled selected>Select Category</option>
                <option value="Beverages">Beverages</option>
                <option value="Cakes and Pastries">Cakes and Pastries</option>
                <option value="Baked">Baked</option>
            </select>
            <button type="submit">Add Product</button>
        </form>

        <!-- View Product List -->
        <%
            if (productList != null && !productList.isEmpty()) {
        %>
            <table class="product-table">
                <thead>
                    <tr>
                        <th>ID</th><th>Name</th><th>Description</th><th>Price</th><th>Category</th><th>Total Sales</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (ProductModel product : productList) { %>
                        <tr>
                            <td><%= product.getId() %></td>
                            <td><%= product.getName() %></td>
                            <td><%= product.getDescription() %></td>
                            <td>Rs. <%= product.getPrice() %></td>
                            <td><%= product.getCategory() %></td>  <!-- Display Category -->
                            <td><%= product.getTotalSales() %></td> <!-- Display Total Sales -->
                        </tr>
                    <% } %>
                </tbody>
            </table>
        <% } else { %>
            <p>No products available.</p>
        <% } %>
    </div>

    <div class="go-back-container">
        <a href="${pageContext.request.contextPath}/home">
            <button class="go-back-btn">Go Back to Home</button>
        </a>
    </div>
</div>

<script>
// Function to show active tab
function showTab(tabName) {
    var tabs = document.getElementsByClassName('tab-content');
    for (var i = 0; i < tabs.length; i++) {
        tabs[i].classList.remove('active');
    }
    document.getElementById(tabName).classList.add('active');
}

// Keep tab active after form submit
window.onload = function () {
    var activeTab = "<%= request.getAttribute("activeTab") != null ? request.getAttribute("activeTab") : "dashboard" %>";
    showTab(activeTab);
};
</script>

</body>
</html>