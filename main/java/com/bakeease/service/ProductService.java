package com.bakeease.service;

import com.bakeease.model.ProductModel;
import com.bakeease.config.DbConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The ProductService class provides methods for interacting with the products in the database.
 * It includes functionality for retrieving, searching, adding, updating, deleting products,
 * and determining the best-selling product.
 */
public class ProductService {

    /**
     * Retrieves all products from the database.
     *
     * @return a list of all products available in the database
     */
    public List<ProductModel> getAllProducts() {
        List<ProductModel> products = new ArrayList<>();
        String query = "SELECT * FROM products";

        try (Connection conn = DbConfig.getDbConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                ProductModel product = extractProductFromResultSet(rs);
                products.add(product);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return products;
    }

    /**
     * Searches for products based on the given search term.
     * The search term is matched against product name, description, and category.
     *
     * @param searchTerm the search term to use for matching product attributes
     * @return a list of products that match the search term
     */
    public List<ProductModel> searchProducts(String searchTerm) {
        List<ProductModel> products = new ArrayList<>();
        String query = "SELECT * FROM products WHERE LOWER(name) LIKE ? OR LOWER(description) LIKE ? OR LOWER(category) LIKE ?";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            String keyword = "%" + searchTerm.toLowerCase() + "%";
            pstmt.setString(1, keyword);
            pstmt.setString(2, keyword);
            pstmt.setString(3, keyword);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ProductModel product = extractProductFromResultSet(rs);
                    products.add(product);
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return products;
    }

    /**
     * Adds a new product to the database.
     *
     * @param product the product to add to the database
     */
    public void addProduct(ProductModel product) {
        String query = "INSERT INTO products (name, description, price, category, total_sales) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getDescription());
            pstmt.setDouble(3, product.getPrice());
            pstmt.setString(4, product.getCategory());
            pstmt.setInt(5, product.getTotalSales());
            pstmt.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates an existing product in the database.
     *
     * @param product the product to update
     */
    public void updateProduct(ProductModel product) {
        String query = "UPDATE products SET name = ?, description = ?, price = ?, category = ?, total_sales = ? WHERE id = ?";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getDescription());
            pstmt.setDouble(3, product.getPrice());
            pstmt.setString(4, product.getCategory());
            pstmt.setInt(5, product.getTotalSales());
            pstmt.setInt(6, product.getId());
            pstmt.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a product from the database by its ID.
     *
     * @param productId the ID of the product to delete
     */
    public void deleteProduct(int productId) {
        String query = "DELETE FROM products WHERE id = ?";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, productId);
            pstmt.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the name of the best-selling product based on total sales.
     *
     * @return the name of the best-selling product, or {@code null} if no products exist
     */
    public String getBestSellingProduct() {
        String query = "SELECT name FROM products ORDER BY total_sales DESC LIMIT 1";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getString("name");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * A reusable method to extract product data from a {@link ResultSet}.
     * This method is used to convert a row of data from the database into a {@link ProductModel} object.
     *
     * @param rs the ResultSet containing the product data
     * @return a ProductModel object populated with the data from the ResultSet
     * @throws SQLException if there is an issue accessing the data from the ResultSet
     */
    private ProductModel extractProductFromResultSet(ResultSet rs) throws SQLException {
        ProductModel product = new ProductModel();
        product.setId(rs.getInt("id"));
        product.setName(rs.getString("name"));
        product.setDescription(rs.getString("description"));
        product.setPrice(rs.getDouble("price"));
        product.setCategory(rs.getString("category"));
        product.setTotalSales(rs.getInt("total_sales"));
        return product;
    }
}