package src;

import java.util.Scanner;

/**
 * Command Line Interface for the Inventory Management System
 * Handles user interaction and menu display
 */
public class CLI {

    private InventoryManager manager;
    private Scanner scanner;

    /**
     * Constructor initializes the inventory manager and scanner
     */
    public CLI() {
        manager = new InventoryManager();
        scanner = new Scanner(System.in);
    }

    /**
     * Main method - kept for backward compatibility
     */
    public static void main(String[] args) {
        CLI app = new CLI();
        app.showMenu();
    }

    /**
     * Shows the main menu and handles user choices
     */
    public void showMenu() {
        int choice;
        do {
            displayMenu();
            choice = getValidIntInput("Enter your choice: ", 0, 5);

            switch (choice) {
                case 1:
                    createItem();
                    break;
                case 2:
                    updateItem();
                    break;
                case 3:
                    deleteItem();
                    break;
                case 4:
                    readItem();
                    break;
                case 5:
                    manager.viewAllItems();
                    break;
                case 0:
                    System.out.println("Exiting the program...");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 0);
    }

    /**
     * Displays the menu options
     */
    private void displayMenu() {
        System.out.println("\n--- Inventory Management System ---");
        System.out.println("1. Create Item");
        System.out.println("2. Update Item");
        System.out.println("3. Delete Item");
        System.out.println("4. Read Item");
        System.out.println("5. View All Items");
        System.out.println("0. Exit");
    }

    /**
     * Gets valid integer input within a range
     * @param prompt The prompt to display
     * @param min The minimum valid value
     * @param max The maximum valid value
     * @return The validated input
     */
    private int getValidIntInput(String prompt, int min, int max) {
        int input = -1;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print(prompt);
                String inputStr = scanner.nextLine().trim();
                input = Integer.parseInt(inputStr);

                if (input >= min && input <= max) {
                    validInput = true;
                } else {
                    System.out.println("Please enter a number between " + min + " and " + max);
                }
            }
            //eg "abc"
            catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        return input;
    }

    /**
     * Gets valid double input
     * @param prompt The prompt to display
     * @return The validated input
     */
    private double getValidDoubleInput(String prompt) {
        double input = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print(prompt);
                String inputStr = scanner.nextLine().trim();
                input = Double.parseDouble(inputStr);
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        return input;
    }

    /**
     * Gets valid non-empty string input
     * @param prompt The prompt to display
     * @return The validated input
     */
    private String getValidStringInput(String prompt) {
        return getValidStringInput(prompt, "Input");
    }

    /**
     * Gets valid non-empty string input that is not a number
     * @param prompt The prompt to display
     * @param fieldType The type of field (name, category, supplier)
     * @return The validated input
     */
    private String getValidStringInput(String prompt, String fieldType) {
        String input = "";
        boolean validInput = false;

        while (!validInput) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println(fieldType + " cannot be empty. Please enter a valid value.");
                continue;
            }

            // Check if the input is a number
            try {
                Double.parseDouble(input);
                System.out.println(fieldType + " cannot be a number. Please enter a valid value.");
                continue;
            } catch (NumberFormatException e) {
                // This is good - it's not a number
                validInput = true;
            }
        }

        return input;
    }

    /**
     * Creates a new inventory item
     */
    public void createItem() {
        // Get the item name first to check for duplicates
        String name = getValidStringInput("Enter Item Name: ", "Name");

        // Check if an item with this name already exists
        InventoryItem existingItem = manager.findItemByName(name);
        if (existingItem != null) {
            System.out.println("An item with the name '" + name + "' already exists (ID: " + existingItem.getItemId() + ").");
            System.out.println("You can only update this item. Would you like to update it? (y/n): ");
            String choice = scanner.nextLine().trim().toLowerCase();

            if (choice.equals("y") || choice.equals("yes")) {
                // Redirect to update flow
                updateItemById(existingItem.getItemId());
                return;
            } else {
                System.out.println("Item creation cancelled.");
                return;
            }
        }

        // Get the next available ID automatically
        int id = FileManager.getNextAvailableId();

        String category = getValidStringInput("Enter Category: ", "Category");
        int quantity = getValidIntInput("Enter Quantity: ", 0, Integer.MAX_VALUE);
        double price = getValidDoubleInput("Enter Price: ");

        // Validate price is positive
        while (price < 0) {
            System.out.println("Price cannot be negative. Please enter a valid price.");
            price = getValidDoubleInput("Enter Price: ");
        }

        String supplier = getValidStringInput("Enter Supplier: ", "Supplier");

        InventoryItem item = new InventoryItem(id, name, category, quantity, price, supplier);
        manager.createItem(item);

        System.out.println("Item created with ID: " + id + " and saved successfully!");
    }

    /**
     * Reads and displays an item by ID
     */
    public void readItem() {
        int id = getValidIntInput("Enter Item ID to view: ", 1, Integer.MAX_VALUE);
        InventoryItem item = manager.readItem(id);

        if (item != null) {
            System.out.println("\n----- Item Details -----");
            System.out.println("ID: " + item.getItemId());
            System.out.println("Name: " + item.getName());
            System.out.println("Category: " + item.getCategory());
            System.out.println("Quantity: " + item.getQuantity());
            System.out.println("Price: $" + item.getPrice());
            System.out.println("Supplier: " + item.getSupplier());
            System.out.println("------------------------");
        } else {
            System.out.println("Item not found.");
        }
    }

    /**
     * Updates an existing item
     */
    public void updateItem() {
        int id = getValidIntInput("Enter Item ID to update: ", 1, Integer.MAX_VALUE);
        updateItemById(id);
    }

    /**
     * Updates an item by its ID
     * @param id The ID of the item to update
     */
    private void updateItemById(int id) {
        InventoryItem existingItem = manager.readItem(id);

        if (existingItem != null) {
            System.out.println("Current item details: " + existingItem);
            System.out.println("Enter new details (press Enter to keep current value):");

            // Get new name with validation
            String name = existingItem.getName(); // Initialize with existing name
            boolean validName = false;
            while (!validName) {
                System.out.print("Enter New Name [" + existingItem.getName() + "]: ");
                String nameInput = scanner.nextLine().trim();

                if (nameInput.isEmpty()) {
                    validName = true; // Keep existing name
                } else {
                    // Check if it's a number
                    try {
                        Double.parseDouble(nameInput);
                        System.out.println("Name cannot be a number. Please enter a valid name.");
                        continue;
                    } catch (NumberFormatException e) {
                        // This is good - it's not a number
                    }

                    // Check if name already exists for a different item
                    InventoryItem itemWithSameName = manager.findItemByName(nameInput);
                    if (itemWithSameName != null && itemWithSameName.getItemId() != id) {
                        System.out.println("An item with this name already exists (ID: " + itemWithSameName.getItemId() + "). Please choose a different name.");
                        continue;
                    }

                    name = nameInput;
                    validName = true;
                }
            }

            // Get new category with validation
            String category = existingItem.getCategory(); // Initialize with existing category
            boolean validCategory = false;
            while (!validCategory) {
                System.out.print("Enter New Category [" + existingItem.getCategory() + "]: ");
                String categoryInput = scanner.nextLine().trim();

                if (categoryInput.isEmpty()) {
                    validCategory = true; // Keep existing category
                } else {
                    // Check if it's a number
                    try {
                        Double.parseDouble(categoryInput);
                        System.out.println("Category cannot be a number. Please enter a valid category.");
                        continue;
                    } catch (NumberFormatException e) {
                        // This is good - it's not a number
                        category = categoryInput;
                        validCategory = true;
                    }
                }
            }

            int quantity;
            try {
                System.out.print("Enter New Quantity [" + existingItem.getQuantity() + "]: ");
                String quantityInput = scanner.nextLine().trim();
                if (quantityInput.isEmpty()) {
                    quantity = existingItem.getQuantity();
                } else {
                    quantity = Integer.parseInt(quantityInput);
                    if (quantity < 0) {
                        System.out.println("Quantity cannot be negative. Keeping current value.");
                        quantity = existingItem.getQuantity();
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid quantity. Keeping current value.");
                quantity = existingItem.getQuantity();
            }

            double price;
            try {
                System.out.print("Enter New Price [" + existingItem.getPrice() + "]: ");
                String priceInput = scanner.nextLine().trim();
                if (priceInput.isEmpty()) {
                    price = existingItem.getPrice();
                } else {
                    price = Double.parseDouble(priceInput);
                    if (price < 0) {
                        System.out.println("Price cannot be negative. Keeping current value.");
                        price = existingItem.getPrice();
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid price. Keeping current value.");
                price = existingItem.getPrice();
            }

            // Get new supplier with validation
            String supplier = existingItem.getSupplier(); // Initialize with existing supplier
            boolean validSupplier = false;
            while (!validSupplier) {
                System.out.print("Enter New Supplier [" + existingItem.getSupplier() + "]: ");
                String supplierInput = scanner.nextLine().trim();

                if (supplierInput.isEmpty()) {
                    validSupplier = true; // Keep existing supplier
                } else {
                    // Check if it's a number
                    try {
                        Double.parseDouble(supplierInput);
                        System.out.println("Supplier cannot be a number. Please enter a valid supplier.");
                        continue;
                    } catch (NumberFormatException e) {
                        // This is good - it's not a number
                        supplier = supplierInput;
                        validSupplier = true;
                    }
                }
            }

            InventoryItem updatedItem = new InventoryItem(id, name, category, quantity, price, supplier);

            if (manager.updateItem(id, updatedItem)) {
                System.out.println("Item updated successfully.");
            } else {
                System.out.println("Failed to update item.");
            }
        } else {
            System.out.println("Item not found.");
        }
    }

    /**
     * Deletes an item by ID
     */
    public void deleteItem() {
        int id = getValidIntInput("Enter Item ID to delete: ", 1, Integer.MAX_VALUE);

        InventoryItem item = manager.readItem(id);
        if (item != null) {
            System.out.println("You are about to delete: " + item);
            System.out.println("WARNING: This action cannot be undone!");
            System.out.print("Are you sure? (y/n): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();

            if (confirmation.equals("y") || confirmation.equals("yes")) {
                if (manager.deleteItem(id)) {
                    System.out.println("Item deleted successfully.");
                } else {
                    System.out.println("Failed to delete item.");
                }
            } else {
                System.out.println("Deletion cancelled.");
            }
        } else {
            System.out.println("Item not found.");
        }
    }
}
