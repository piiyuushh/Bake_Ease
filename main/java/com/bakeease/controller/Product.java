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

/**
 * Servlet implementation class Product.
 *
 * This servlet handles both GET and POST requests for managing products.
 * - GET: Fetches and displays all products or those matching a search query.
 * - POST: Handles CRUD operations (add, update, delete) on products.
 * 
 * URL Pattern: /product
 * 
 * Author: Piyush Karn
 */
@WebServlet("/product")
public class Product extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ProductService productService = new ProductService();

    /**
     * Handles HTTP GET requests for listing all products or filtered search results.
     *
     * @param request  the HttpServletRequest object that contains client request
     * @param response the HttpServletResponse object that contains servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an input/output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String query = request.getParameter("query");
        List<ProductModel> products;

        // If query is present and not empty, perform a search
        if (query != null && !query.trim().isEmpty()) {
            products = productService.searchProducts(query.trim());
        } else {
            products = productService.getAllProducts();
        }

        // Set attributes for JSP
        request.setAttribute("products", products);
        request.setAttribute("query", query); // Optional: retain query text in search box

        // Forward to JSP page for rendering
        request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
    }

    /**
     * Handles HTTP POST requests for performing product operations: add, update, delete.
     *
     * @param request  the HttpServletRequest object that contains form data
     * @param response the HttpServletResponse object used to send redirect or error
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an input/output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {
                // Extract product details from form
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                double price = Double.parseDouble(request.getParameter("price"));

                // Create new product model
                ProductModel newProduct = new ProductModel();
                newProduct.setName(name);
                newProduct.setDescription(description);
                newProduct.setPrice(price);

                // Save product
                productService.addProduct(newProduct);

            } else if ("update".equals(action)) {
                // Get and update existing product
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
                // Delete product by ID
                int id = Integer.parseInt(request.getParameter("id"));
                productService.deleteProduct(id);
            }

            // Redirect to avoid form resubmission
            response.sendRedirect("product");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "An error occurred while processing the product.");
        }
    }
}