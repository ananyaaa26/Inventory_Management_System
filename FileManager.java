package src;
import java.io.*;
import java.util.Scanner;
import src.datastructures.BinarySearchTree;
import src.datastructures.CustomArrayList;

/**
 * Handles file operations for the inventory system
 * Uses CSV format for storing inventory data
 */
public class FileManager {

    // Change the file path to use a single file
    private static final String CSV_DIRECTORY = "inventory_data/";
    private static final String CSV_FILE = "inventory_data.csv";

    /**
     * Ensures the directory for storing CSV files exists
     */
    private static void ensureDirectoryExists() {
        File directory = new File(CSV_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    // Replace getFilePath method to return the single file path
    private static String getFilePath() {
        return CSV_DIRECTORY + CSV_FILE;
    }

    // Update writeItemToFile method to use a single file
    public static void writeItemToFile(InventoryItem item) {
        ensureDirectoryExists();
        String filePath = getFilePath();

        // Read existing items
        BinarySearchTree existingItems = readAllItems();

        // Add or update item
        existingItems.add(item);

        // Write all items back to file
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            // Write header
            writer.println("itemId,name,category,quantity,price,supplier");

            // Write items using in-order traversal
            existingItems.inOrderTraversal(currentItem -> {
                writer.println(
                        currentItem.getItemId() + "," +
                                escapeCSV(currentItem.getName()) + "," +
                                escapeCSV(currentItem.getCategory()) + "," +
                                currentItem.getQuantity() + "," +
                                currentItem.getPrice() + "," +
                                escapeCSV(currentItem.getSupplier())
                );
            });
            System.out.println("Item saved successfully to " + filePath);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Update deleteItemFromFile method to use a single file and update IDs
    public static boolean deleteItemFromFile(int itemId) {
        ensureDirectoryExists();
        String filePath = getFilePath();
        File file = new File(filePath);

        if (!file.exists()) {
            return false;
        }

        BinarySearchTree items = readAllItems();
        boolean deleted = items.remove(itemId);

        if (deleted) {
            // Update IDs for items with higher IDs
            CustomArrayList itemList = new CustomArrayList();
            items.inOrderTraversal(itemList::add);

            // Clear the BST
            items = new BinarySearchTree();

            // Update IDs and add back to BST
            for (int i = 0; i < itemList.size(); i++) {
                InventoryItem item = itemList.get(i);
                if (item.getItemId() > itemId) {
                    item.setItemId(item.getItemId() - 1);
                }
                items.add(item);
            }

            try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
                // Write header
                writer.println("itemId,name,category,quantity,price,supplier");

                // Write remaining items
                items.inOrderTraversal(item -> {
                    writer.println(
                            item.getItemId() + "," +
                                    escapeCSV(item.getName()) + "," +
                                    escapeCSV(item.getCategory()) + "," +
                                    item.getQuantity() + "," +
                                    item.getPrice() + "," +
                                    escapeCSV(item.getSupplier())
                    );
                });
                System.out.println("Item deleted successfully from " + filePath);
                return true;
            } catch (IOException e) {
                System.out.println("Error updating file after deletion: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return false;
    }

    // Update readAllItems method to read from a single file
    public static BinarySearchTree readAllItems() {
        ensureDirectoryExists();
        String filePath = getFilePath();
        BinarySearchTree items = new BinarySearchTree();
        File file = new File(filePath);

        if (!file.exists()) {
            return items; // Return empty BST if file doesn't exist
        }

        try (Scanner scanner = new Scanner(file)) {
            // Skip header
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            // Read items
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                InventoryItem item = parseCSVLine(line);
                if (item != null) {
                    items.add(item);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        } catch (Exception e) {
            System.out.println("Error reading data: " + e.getMessage());
            e.printStackTrace();
        }

        return items;
    }

    /**
     * Reads a specific item by ID
     * @param itemId The ID of the item to find
     * @return The item if found, null otherwise
     */
    public static InventoryItem readItemById(int itemId) {
        BinarySearchTree allItems = readAllItems();
        return allItems.find(itemId);
    }

    /**
     * Checks if an item with the given name exists
     * @param name The name to check
     * @return The item if found, null otherwise
     */
    public static InventoryItem findItemByName(String name) {
        BinarySearchTree allItems = readAllItems();
        return allItems.findByName(name);
    }

    /**
     * Parses a CSV line into an InventoryItem
     * @param line The CSV line to parse
     * @return The parsed InventoryItem
     */
    private static InventoryItem parseCSVLine(String line) {
        try {
            String[] parts = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); // Split by comma, respecting quotes

            if (parts.length >= 6) {
                int itemId = Integer.parseInt(parts[0].trim());
                String name = unescapeCSV(parts[1].trim());
                String category = unescapeCSV(parts[2].trim());
                int quantity = Integer.parseInt(parts[3].trim());
                double price = Double.parseDouble(parts[4].trim());
                String supplier = unescapeCSV(parts[5].trim());

                return new InventoryItem(itemId, name, category, quantity, price, supplier);
            }
        } catch (NumberFormatException e) {
            System.out.println("Error parsing CSV line: " + e.getMessage());
        }

        return null;
    }

    /**
     * Escapes special characters for CSV format
     * @param value The string to escape
     * @return The escaped string
     */
    private static String escapeCSV(String value) {
        if (value == null) {
            return "";
        }

        // If the value contains comma, newline or double quote, wrap in quotes and escape internal quotes
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }

        return value;
    }

    /**
     * Unescapes CSV formatted string
     * @param value The string to unescape
     * @return The unescaped string
     */
    private static String unescapeCSV(String value) {
        if (value == null) {
            return "";
        }

        // If the value is wrapped in quotes, remove them and unescape internal quotes
        if (value.startsWith("\"") && value.endsWith("\"")) {
            return value.substring(1, value.length() - 1).replace("\"\"", "\"");
        }

        return value;
    }

    // Add method to get the next available ID
    public static int getNextAvailableId() {
        BinarySearchTree items = readAllItems();
        if (items.isEmpty()) {
            return 1; // Start with 1 if no items exist
        }

        // Find the highest ID
        //A final array is used here because Java's lambdas require that //
        // variables used inside them must be effectively final.
        final int[] highestId = {0};
        items.inOrderTraversal(item -> {
            if (item.getItemId() > highestId[0]) {
                highestId[0] = item.getItemId();
            }
        });

        return highestId[0] + 1;
    }
}
