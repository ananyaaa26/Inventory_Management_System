package src;

/**
 * Main entry point for the Inventory Management System
 * Initializes the system with dummy data and starts the CLI
 */

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Inventory Management System...");

        // Add dummy data
//        addDummyData();

        // Create and start the CLI
        CLI cli = new CLI();
        cli.showMenu();
    }

    // Update the addDummyData method to ensure all data meets validation requirements
    private static void addDummyData() {
        InventoryManager manager = new InventoryManager();

        // Electronics category
        manager.createItem(new InventoryItem(1, "Laptop", "Electronics", 10, 999.99, "Dell"));
        manager.createItem(new InventoryItem(2, "Smartphone", "Electronics", 15, 699.99, "Apple"));
        manager.createItem(new InventoryItem(3, "Headphones", "Electronics", 20, 149.99, "Sony"));
        manager.createItem(new InventoryItem(4, "Tablet", "Electronics", 8, 349.99, "Samsung"));
        manager.createItem(new InventoryItem(5, "Smart Watch", "Electronics", 12, 249.99, "Fitbit"));

        // Furniture category
        manager.createItem(new InventoryItem(6, "Sofa", "Furniture", 5, 799.99, "IKEA"));
        manager.createItem(new InventoryItem(7, "Dining Table", "Furniture", 3, 499.99, "Ashley"));
        manager.createItem(new InventoryItem(8, "Bookshelf", "Furniture", 7, 199.99, "Wayfair"));
        manager.createItem(new InventoryItem(9, "Office Chair", "Furniture", 10, 149.99, "Staples"));
        manager.createItem(new InventoryItem(10, "Bed Frame", "Furniture", 4, 299.99, "Sleep Country"));

        // Sports category
        manager.createItem(new InventoryItem(11, "Basketball", "Sports", 15, 29.99, "Nike"));
        manager.createItem(new InventoryItem(12, "Tennis Racket", "Sports", 8, 89.99, "Wilson"));
        manager.createItem(new InventoryItem(13, "Yoga Mat", "Sports", 20, 19.99, "Lululemon"));
        manager.createItem(new InventoryItem(14, "Dumbbells", "Sports", 12, 49.99, "Adidas"));
        manager.createItem(new InventoryItem(15, "Soccer Ball", "Sports", 18, 24.99, "Adidas"));

        // Clothing category
        manager.createItem(new InventoryItem(16, "T-Shirt", "Clothing", 30, 19.99, "H&M"));
        manager.createItem(new InventoryItem(17, "Jeans", "Clothing", 25, 49.99, "Levi's"));
        manager.createItem(new InventoryItem(18, "Sweater", "Clothing", 15, 39.99, "Gap"));
        manager.createItem(new InventoryItem(19, "Jacket", "Clothing", 10, 79.99, "North Face"));
        manager.createItem(new InventoryItem(20, "Socks", "Clothing", 40, 9.99, "Hanes"));

        // Household category
        manager.createItem(new InventoryItem(21, "Blender", "Household", 8, 69.99, "Cuisinart"));
        manager.createItem(new InventoryItem(22, "Toaster", "Household", 12, 39.99, "Black & Decker"));
        manager.createItem(new InventoryItem(23, "Coffee Maker", "Household", 10, 59.99, "Keurig"));
        manager.createItem(new InventoryItem(24, "Vacuum Cleaner", "Household", 6, 149.99, "Dyson"));
        manager.createItem(new InventoryItem(25, "Microwave", "Household", 5, 99.99, "Samsung"));

        System.out.println("Dummy data added successfully!");
    }
}
