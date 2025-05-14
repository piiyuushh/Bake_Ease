package com.bakeease.controller;

import com.bakeease.model.ProductModel;
import com.bakeease.model.UserModel;
import com.bakeease.service.ProductService;
import com.bakeease.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

/**
 * The Admin servlet handles admin panel functionalities such as displaying dashboard metrics,
 * viewing products, and managing product data (add, update, delete).
 * 
 * Only authenticated users with a valid session can access this servlet.
 */
@WebServlet("/admin")
public class Admin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Services for interacting with product and user data
    private final ProductService productService = new ProductService();
    private final UserService userService = new UserService();

    /**
     * Default constructor calling the HttpServlet superclass constructor.
     */
    public Admin() {
        super();
    }

    /**
     * Handles GET requests to display the admin dashboard or specific pages
     * like 'addProduct.jsp' or 'viewProducts.jsp' based on the "action" parameter.
     * It also checks for a valid session and loads user-specific and product data.
     *
     * @param request the HttpServletRequest object that contains the request the client made to the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws ServletException if an error occurs during request forwarding
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check session for authentication
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Retrieve logged-in user details
        String username = (String) session.getAttribute("username");
        UserModel user = userService.getUserByUsername(username);
        if (user != null) {
            request.setAttribute("user", user);
        }

        // Handle different actions: show addProduct or viewProducts page
        String action = request.getParameter("action");

        if ("addProduct".equals(action)) {
            request.getRequestDispatcher("/WEB-INF/pages/addProduct.jsp").forward(request, response);
        } else if ("viewProducts".equals(action)) {
            List<ProductModel> products = productService.getAllProducts();
            request.setAttribute("products", products);
            request.getRequestDispatcher("/WEB-INF/pages/viewProducts.jsp").forward(request, response);
        } else {
            // Load default admin dashboard data
            List<ProductModel> products = productService.getAllProducts();

            double totalSales = products.stream().mapToDouble(ProductModel::getTotalSales).sum();
            int items = products.size();
            int categories = 3; // Hardcoded; can be dynamically determined if needed
            int customers = userService.countCustomers();
            int orders = products.size(); // Assuming each product counts as one order
            String getBestSellingProductName = productService.getBestSellingProduct();

            // Set attributes for admin dashboard display
            request.setAttribute("totalSales", totalSales);
            request.setAttribute("items", items);
            request.setAttribute("categories", categories);
            request.setAttribute("customers", customers);
            request.setAttribute("orders", orders);
            request.setAttribute("products", products);
            request.setAttribute("bestSellingProduct", getBestSellingProductName);

            request.getRequestDispatcher("/WEB-INF/pages/admin.jsp").forward(request, response);
        }
    }

    /**
     * Handles POST requests for performing product management actions such as
     * adding, updating, or deleting a product based on the "action" parameter.
     * Sets success or error messages accordingly.
     *
     * @param request the HttpServletRequest object that contains the request the client made to the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws ServletException if an error occurs during request forwarding
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            // Add a new product
            if ("add".equals(action)) {
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                double price = Double.parseDouble(request.getParameter("price"));
                String category = request.getParameter("category");
                int totalSales = Integer.parseInt(request.getParameter("total_sales"));

                ProductModel product = new ProductModel();
                product.setName(name);
                product.setDescription(description);
                product.setPrice(price);
                product.setCategory(category);
                product.setTotalSales(totalSales);

                productService.addProduct(product);
                request.setAttribute("successMessage", "Product added successfully!");

            // Update an existing product
            } else if ("update".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                double price = Double.parseDouble(request.getParameter("price"));
                String category = request.getParameter("category");
                int totalSales = Integer.parseInt(request.getParameter("total_sales"));

                ProductModel product = new ProductModel();
                product.setId(id);
                product.setName(name);
                product.setDescription(description);
                product.setPrice(price);
                product.setCategory(category);
                product.setTotalSales(totalSales);

                productService.updateProduct(product);
                request.setAttribute("successMessage", "Product updated successfully!");

            // Delete a product
            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                productService.deleteProduct(id);
                request.setAttribute("successMessage", "Product deleted successfully!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("successMessage", "An error occurred while processing the request.");
        }

        // After performing action, reload the admin dashboard
        doGet(request, response);
    }
}