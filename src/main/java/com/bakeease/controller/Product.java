package com.bakeease.controller;

import com.bakeease.model.ProductModel;
import com.bakeease.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/product")
public class Product extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ProductService productService = new ProductService();

    /**
     * Handles GET requests for displaying all or searched products.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String query = request.getParameter("query");
        List<ProductModel> products;

        if (query != null && !query.trim().isEmpty()) {
            products = productService.searchProducts(query.trim());
        } else {
            products = productService.getAllProducts();
        }

        request.setAttribute("products", products);
        request.setAttribute("query", query); // Optional: used in JSP for pre-filling the search box
        request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
    }

    /**
     * Handles POST requests for product operations: add, update, delete.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                double price = Double.parseDouble(request.getParameter("price"));

                ProductModel newProduct = new ProductModel();
                newProduct.setName(name);
                newProduct.setDescription(description);
                newProduct.setPrice(price);

                productService.addProduct(newProduct);
            } else if ("update".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                double price = Double.parseDouble(request.getParameter("price"));

                ProductModel product = new ProductModel();
                product.setId(id);
                product.setName(name);
                product.setDescription(description);
                product.setPrice(price);

                productService.updateProduct(product);
            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                productService.deleteProduct(id);
            }

            // After operation, redirect back to product list
            response.sendRedirect("product");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "An error occurred while processing the product.");
        }
    }
}