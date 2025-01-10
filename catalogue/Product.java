package catalogue;

import java.io.Serializable;

/**
 * Represents a product with details such as
 * product number, description, price, and stock quantity.
 */
public class Product implements Serializable {
    private static final long serialVersionUID = 20092506L;

    private String productNum;       // Unique product number
    private String description;      // Description of the product
    private double price;            // Price of the product
    private int quantity;            // Quantity of the product in stock

    /**
     * Constructor to initialize a product with all its details.
     *
     * @param productNum  Unique product number
     * @param description Description of the product
     * @param price       Price of the product
     * @param quantity    Quantity of the product in stock
     */
    public Product(String productNum, String description, double price, int quantity) {
        this.productNum = productNum;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters

    /**
     * Get the product number.
     *
     * @return Product number as a string.
     */
    public String getProductNum() {
        return productNum;
    }

    /**
     * Get the product description.
     *
     * @return Product description as a string.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the price of the product.
     *
     * @return Price as a double.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Get the quantity of the product.
     *
     * @return Quantity as an integer.
     */
    public int getQuantity() {
        return quantity;
    }

    // Setters

    /**
     * Set a new product number.
     *
     * @param productNum New product number as a string.
     */
    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    /**
     * Set a new product description.
     *
     * @param description New description of the product.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Set a new price for the product.
     *
     * @param price New price as a double.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Set a new quantity for the product.
     *
     * @param quantity New quantity as an integer.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Provides a string representation of the product details.
     *
     * @return A formatted string containing product details.
     */
    @Override
    public String toString() {
        return String.format("ProductNum: %s, Description: %s, Price: %.2f, Quantity: %d",
                productNum, description, price, quantity);
    }
}

