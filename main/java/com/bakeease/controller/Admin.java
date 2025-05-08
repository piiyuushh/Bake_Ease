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

@WebServlet("/admin")
public class Admin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ProductService productService = new ProductService();
    private final UserService userService = new UserService();

    public Admin() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Session and user authentication
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String username = (String) session.getAttribute("username");
        UserModel user = userService.getUserByUsername(username);
        if (user != null) {
            request.setAttribute("user", user);
        }

        String action = request.getParameter("action");

        if ("addProduct".equals(action)) {
            request.getRequestDispatcher("/WEB-INF/pages/addProduct.jsp").forward(request, response);
        } else if ("viewProducts".equals(action)) {
            List<ProductModel> products = productService.getAllProducts();
            request.setAttribute("products", products);
            request.getRequestDispatcher("/WEB-INF/pages/viewProducts.jsp").forward(request, response);
        } else {
            List<ProductModel> products = productService.getAllProducts();

            double totalSales = products.stream().mapToDouble(ProductModel::getTotalSales).sum();
            int items = products.size();
            int categories = 3;
            int customers = userService.countCustomers();
            int orders = products.size();
            String getBestSellingProductName = productService.getBestSellingProduct();

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
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
            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                productService.deleteProduct(id);
                request.setAttribute("successMessage", "Product deleted successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("successMessage", "An error occurred while processing the request.");
        }

        doGet(request, response);
    }
}
