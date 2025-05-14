package com.bakeease.model;

/**
 * Represents a product in the BakeEase system.
 * Stores details such as product name, description, price, category, and sales data.
 */
public class ProductModel {
    private int id;
    private String name;
    private String description;
    private double price;
    private String category;
    private int totalSales;

    /**
     * Default constructor for creating an empty product instance.
     */
    public ProductModel() {}

    /**
     * Parameterized constructor for creating a product with all properties.
     *
     * @param id          the product ID
     * @param name        the name of the product
     * @param description the product description
     * @param price       the price of the product
     * @param category    the category to which the product belongs
     * @param totalSales  the total number of sales for the product
     */
    public ProductModel(int id, String name, String description, double price, String category, int totalSales) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.totalSales = totalSales;
    }

    /**
     * Gets the product ID.
     * @return the product ID
     */
    public int getId() { return id; }

    /**
     * Sets the product ID.
     * @param id the new product ID
     */
    public void setId(int id) { this.id = id; }

    /**
     * Gets the product name.
     * @return the product name
     */
    public String getName() { return name; }

    /**
     * Sets the product name.
     * @param name the new product name
     */
    public void setName(String name) { this.name = name; }

    /**
     * Gets the product description.
     * @return the product description
     */
    public String getDescription() { return description; }

    /**
     * Sets the product description.
     * @param description the new product description
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * Gets the product price.
     * @return the product price
     */
    public double getPrice() { return price; }

    /**
     * Sets the product price.
     * @param price the new product price
     */
    public void setPrice(double price) { this.price = price; }

    /**
     * Gets the product category.
     * @return the product category
     */
    public String getCategory() { return category; }

    /**
     * Sets the product category.
     * @param category the new product category
     */
    public void setCategory(String category) { this.category = category; }

    /**
     * Gets the total sales of the product.
     * @return the total number of product sales
     */
    public int getTotalSales() { return totalSales; }

    /**
     * Sets the total sales of the product.
     * @param totalSales the new total sales number
     */
    public void setTotalSales(int totalSales) { this.totalSales = totalSales; }

    /**
     * Returns a string representation of the product.
     * @return a string containing product details
     */
    @Override
    public String toString() {
        return "ProductModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", totalSales=" + totalSales +
                '}';
    }
}