<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.bakeease.model.ProductModel" %>
<%@ page import="java.util.List" %>

<%
    // Ensure productList is accessible for entire page
    List<ProductModel> productList = (List<ProductModel>) request.getAttribute("products");
    String activeTab = (String) request.getAttribute("activeTab");
    if (activeTab == null) activeTab = "home";
    String successMessage = (String) request.getAttribute("successMessage");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/assets/favicon.png" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin.css" />
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>

<div class="sidebar">
    <div class="sidebar-title">Admin</div>
    <button class="tab-btn" onclick="showTab('home')">Home</button>
    <button class="tab-btn" onclick="showTab('dashboard')">Dashboard</button>
    <button class="tab-btn" onclick="showTab('products')">Products</button>
</div>

<div class="main-content">

    <% if (successMessage != null) { %>
        <div class="alert success"><%= successMessage %></div>
    <% } %>


    <!-- Dashboard Tab -->
    <div id="dashboard" class="tab-content <%= "dashboard".equals(activeTab) ? "active" : "" %>">
        <h2 class="dashboard-title">Manage Products</h2>

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

        <% if (productList != null && !productList.isEmpty()) { %>
        <h3>Current Product Listings</h3>
        <table class="product-table">
            <thead>
                <tr>
                    <th>ID</th><th>Name</th><th>Description</th><th>Price</th><th>Category</th><th>Total Sales</th><th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% for (ProductModel product : productList) { %>
                    <tr>
                        <form action="${pageContext.request.contextPath}/admin" method="post">
                            <input type="hidden" name="id" value="<%= product.getId() %>">
                            <td><%= product.getId() %></td>
                            <td><input type="text" name="name" value="<%= product.getName() %>" required></td>
                            <td><input type="text" name="description" value="<%= product.getDescription() %>" required></td>
                            <td><input type="number" step="10" name="price" value="<%= product.getPrice() %>" required></td>
                            <td>
                                <select name="category" required>
                                    <option value="" disabled>Select Category</option>
                                    <option value="Beverages" <%= "Beverages".equals(product.getCategory()) ? "selected" : "" %>>Beverages</option>
                                    <option value="Cakes and Pastries" <%= "Cakes and Pastries".equals(product.getCategory()) ? "selected" : "" %>>Cakes and Pastries</option>
                                    <option value="Baked" <%= "Baked".equals(product.getCategory()) ? "selected" : "" %>>Baked</option>
                                </select>
                            </td>
                            <td><input type="number" step="10" name="total_sales" value="<%= product.getTotalSales() %>" required></td>
                            <td>
                                <button type="submit" name="action" value="update">Update</button>
                                <button type="submit" name="action" value="delete" onclick="return confirm('Are you sure you want to delete this product?')">Delete</button>
                            </td>
                        </form>
                    </tr>
                <% } %>
            </tbody>
        </table>

        <% } else { %>
            <p>No products available.</p>
        <% } %>
    </div>
    
    
    <!-- Home Tab -->
    <div id="home" class="tab-content <%= "home".equals(activeTab) ? "active" : "" %>">
        <h2 class="dashboard-title">Product Overview</h2>

        <% if (productList != null && !productList.isEmpty()) { %>
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
                        <td><%= product.getCategory() %></td>
                        <td><%= product.getTotalSales() %></td>
                    </tr>
                <% } %>
            </tbody>
        </table>

        <div class="dashboard-chart">
            <canvas id="productSalesChart" width="400" height="200"></canvas>
        </div>
        <% } else { %>
            <p>No products available.</p>
        <% } %>
    </div>

    <!-- Products Tab -->
    <div id="products" class="tab-content <%= "products".equals(activeTab) ? "active" : "" %>">
        <h2>Add Products</h2>

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

        <% if (productList != null && !productList.isEmpty()) { %>
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
                        <td><%= product.getCategory() %></td>
                        <td><%= product.getTotalSales() %></td>
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
    function showTab(tabName) {
        var tabs = document.getElementsByClassName('tab-content');
        for (var i = 0; i < tabs.length; i++) {
            tabs[i].classList.remove('active');
        }
        document.getElementById(tabName).classList.add('active');
    }

    window.onload = function () {
        const activeTab = "<%= activeTab %>";
        showTab(activeTab);

        const names = [<% for (ProductModel p : productList) { %>"<%= p.getName() %>",<% } %>];
        const sales = [<% for (ProductModel p : productList) { %><%= p.getTotalSales() %>,<% } %>];

        if (activeTab === 'home') {
            renderChart('productSalesChart', names, sales);
        } else if (activeTab === 'home') {
            renderChart('homeProductSalesChart', names, sales);
        }
    };

    function renderChart(canvasId, labels, data) {
        const ctx = document.getElementById(canvasId).getContext('2d');
        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Total Sales',
                    data: data,
                    backgroundColor: 'rgba(175, 115, 78, 0.7)',
                    borderColor: 'rgba(175, 115, 78, 1)',
                    borderWidth: 1,
                    borderRadius: 5
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    title: {
                        display: true,
                        text: 'Product Sales Overview'
                    }
                },
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    }
</script>

</body>
</html>

