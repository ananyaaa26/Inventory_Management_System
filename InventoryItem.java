package src;

import java.io.Serializable;

/**
 * Represents an item in the inventory
 * Contains all the attributes and methods for an inventory item
 */
public class InventoryItem implements Serializable {
    private static final long serialVersionUID = 1L;

    // Attributes of the item
    private int itemId;
    private String name;
    private String category;
    private int quantity;
    private double price;
    private String supplier;

    /**
     * Constructor to create a new inventory item
     * @param itemId Unique identifier for the item
     * @param name Name of the item
     * @param category Category the item belongs to
     * @param quantity Quantity in stock
     * @param price Price of the item
     * @param supplier Supplier of the item
     */
    public InventoryItem(int itemId, String name, String category, int quantity, double price, String supplier) {
        this.itemId = itemId;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
        this.supplier = supplier;
    }

    // Getter and Setter methods for each attribute
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    /**
     * Returns a string representation of the inventory item
     */
    @Override
    public String toString() {
        return "InventoryItem{" +
                "itemId='" + itemId + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", supplier='" + supplier + '\'' +
                '}';
    }
}
