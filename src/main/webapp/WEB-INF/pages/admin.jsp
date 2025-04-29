<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/assets/favicon.png" />
	<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/admin.css" />
</head>
<body>

<div class="sidebar">
    <div class="sidebar-title">Admin</div>
    <button class="tab-btn" onclick="showTab('dashboard')">Dashboard</button>
    <button class="tab-btn" onclick="showTab('orders')">Orders List</button>
    <button class="tab-btn" onclick="showTab('students')">Products</button>
    <button class="tab-btn" onclick="showTab('update')">Update Product</button>
</div>

<div class="main-content">

    <!-- Dashboard Tab -->
    <div id="dashboard" class="tab-content active">
        <div class="cards-container">
            <div class="card">Total Sales<br><span>$12,340</span></div>
            <div class="card">Items<br><span>120</span></div>
            <div class="card">Categories<br><span>10</span></div>
            <div class="card">Customers<br><span>450</span></div>
            <div class="card">Orders<br><span>230</span></div>
        </div>

        <div class="welcome-box">
            <h2>Welcome, Admins!</h2>
            <p>Manage your system efficiently and easily.</p>
        </div>

        <div class="recent-table">
    <h3>Recently Added Bakery Items</h3>
    <table>
        <thead>
            <tr>
                <th>Item ID</th>
                <th>Item Name</th>
                <th>Category</th>
                <th>Price</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>101</td>
                <td>Chocolate Truffle Cake</td>
                <td>Cake</td>
                <td>Rs. 1200</td>
            </tr>
            <tr>
                <td>102</td>
                <td>Butter Croissant</td>
                <td>Pastry</td>
                <td>Rs. 250</td>
            </tr>
            <tr>
                <td>103</td>
                <td>Blueberry Muffin</td>
                <td>Muffin</td>
                <td>Rs. 180</td>
            </tr>
            <tr>
                <td>104</td>
                <td>Red Velvet Cupcake</td>
                <td>Cupcake</td>
                <td>Rs. 300</td>
            </tr>
            <tr>
                <td>105</td>
                <td>Whole Wheat Bread</td>
                <td>Bread</td>
                <td>Rs. 150</td>
            </tr>
        </tbody>
    </table>
</div>
    </div>

    <!-- Other Tabs -->
    <div id="orders" class="tab-content">
        <h2>Order List Page</h2>
        <p>Here you can create new orders.</p>
    </div>

    <div id="students" class="tab-content">
        <h2>Students List Page</h2>
        <p>Here you can view and delete students.</p>
    </div>

    <div id="update" class="tab-content">
        <h2>Update Student Page</h2>
        <p>Here you can update student information.</p>
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
</script>

</body>
</html>