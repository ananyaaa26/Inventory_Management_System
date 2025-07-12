# Inventory Management System Documentation

## Overview
The Inventory Management System is a Java-based console application designed to manage inventory items. It provides a Command Line Interface (CLI) for users to perform CRUD operations (Create, Read, Update, Delete) on inventory items, store data in CSV files categorized by item type, and display items in a sorted format. The system is modular, with separate classes for user interaction, data management, file operations, and data structures, ensuring maintainability and scalability.

### Key Features
- **CRUD Operations**: Create, read, update, and delete inventory items with unique IDs.
- **Persistent Storage**: Store items in CSV files, organized by category, for data persistence.
- **Sorted Display**: View all items sorted by category and name in a formatted table.
- **Custom Data Structures**: Uses a Binary Search Tree (BST) for efficient item storage and retrieval, with a custom ArrayList for sorting during display.
- **Error Handling**: Robust input validation and error handling for user inputs and file operations.

### System Workflow
1. **Startup**: The `Main` class initializes the system and starts the CLI.
2. **User Interaction**: The `CLI` class displays a menu, accepts user inputs, and delegates tasks to the `InventoryManager`.
3. **Data Management**: The `InventoryManager` handles CRUD operations, interacting with the `FileManager` for data storage and retrieval.
4. **File Operations**: The `FileManager` reads/writes items to CSV files using a `BinarySearchTree` for in-memory storage.
5. **Data Structures**: The `BinarySearchTree` stores items ordered by `itemId` for efficient searches, while `CustomArrayList` and `SortingAlgorithms` support sorting for display.
6. **Data Model**: The `InventoryItem` class defines the structure of each item (ID, name, category, quantity, price, supplier).

### Why This Design?
- **Modularity**: Each class has a single responsibility (e.g., `CLI` for UI, `FileManager` for I/O), making the code easier to maintain and extend.
- **Persistence**: CSV files provide a simple, human-readable storage solution, with one file per category to organize data.
- **Custom Data Structures**: Using custom implementations (`BinarySearchTree`, `CustomArrayList`) allows full control over functionality and avoids reliance on Java’s standard library, which is useful for learning or specific constraints.
- **Error Handling**: Extensive validation ensures robustness, especially for user inputs and file operations.

## Class Descriptions and Method Details

### 1. Main.java
**Purpose**: Serves as the entry point for the application, initializing the system and starting the CLI.

**Why This Way?**
- Keeps the entry point simple, delegating all logic to other classes.
- Allows for future initialization tasks (e.g., loading dummy data) without cluttering the main method.

**Methods**:
- **`public static void main(String[] args)`**
  - **Description**: Initializes the system and starts the CLI.
  - **Workflow**: Prints a startup message, creates a `CLI` instance, and calls `showMenu` to begin user interaction.
  - **Why?**: Provides a clear entry point, keeping initialization separate from UI logic.

### 2. CLI.java
**Purpose**: Manages the command-line interface, displaying the menu, handling user inputs, and delegating tasks to `InventoryManager`.

**Why This Way?**
- Centralizes user interaction, keeping UI logic separate from business logic.
- Uses a `Scanner` for input, with robust validation to prevent crashes from invalid inputs.
- Menu-driven interface is intuitive for console applications.

**Methods**:
- **`public CLI()`**
  - **Description**: Constructor that initializes the `InventoryManager` and `Scanner`.
  - **Why?**: Ensures the CLI has access to the manager for operations and a scanner for input.

- **`public static void main(String[] args)`**
  - **Description**: Backward-compatible entry point that creates a `CLI` instance and starts the menu.
  - **Why?**: Retained for compatibility with older versions; delegates to `Main` in the current design.

- **`public void showMenu()`**
  - **Description**: Displays the main menu and processes user choices in a loop until exit (choice 0).
  - **Workflow**: Calls `displayMenu`, gets validated input via `getValidIntInput`, and uses a switch statement to call methods like `createItem`, `updateItem`, etc.
  - **Why?**: Loop ensures continuous interaction until the user exits; switch statement maps choices to actions clearly.

- **`private void displayMenu()`**
  - **Description**: Prints the menu options (1: Create, 2: Update, 3: Delete, 4: Read, 5: View All, 0: Exit).
  - **Why?**: Separates menu display for reusability and clarity.

- **`private int getValidIntInput(String prompt, int min, int max)`**
  - **Description**: Gets an integer input within a specified range, handling invalid inputs.
  - **Workflow**: Prompts the user, parses input, catches `NumberFormatException`, and ensures the value is within `[min, max]`.
  - **Why?**: Robust validation prevents crashes and ensures valid menu choices or item IDs.

- **`private double getValidDoubleInput(String prompt)`**
  - **Description**: Gets a valid double input for prices.
  - **Workflow**: Prompts, parses input, catches `NumberFormatException`, and retries until valid.
  - **Why?**: Ensures prices are numeric, critical for `createItem` and `updateItem`.

- **`private String getValidStringInput(String prompt)`**
  - **Description**: Gets a non-empty string input for names, categories, and suppliers.
  - **Workflow**: Prompts, checks for non-empty input, and retries if empty.
  - **Why?**: Prevents invalid or empty strings, ensuring data integrity.

- **`public void createItem()`**
  - **Description**: Creates a new `InventoryItem` based on user input.
  - **Workflow**: Gets validated inputs (ID, name, category, quantity, price, supplier), checks for duplicate IDs, creates an `InventoryItem`, and calls `manager.createItem`.
  - **Why?**: Centralizes item creation logic with validation to prevent errors (e.g., negative prices, duplicate IDs).

- **`public void readItem()`**
  - **Description**: Displays details of an item by ID.
  - **Workflow**: Gets a valid ID, calls `manager.readItem`, and prints item details or a “not found” message.
  - **Why?**: Provides a simple way to view item details, reusing `InventoryManager` for data access.

- **`public void updateItem()`**
  - **Description**: Updates an existing item’s details.
  - **Workflow**: Gets an ID, checks if the item exists, prompts for new values (allowing empty inputs to keep current values), validates inputs, creates an updated `InventoryItem`, and calls `manager.updateItem`.
  - **Why?**: Allows partial updates (e.g., change only quantity) with validation to prevent invalid data.

- **`public void deleteItem()`**
  - **Description**: Deletes an item by ID after user confirmation.
  - **Workflow**: Gets an ID, checks if the item exists, displays item details, prompts for confirmation (y/n), and calls `manager.deleteItem` if confirmed.
  - **Why?**: Confirmation prevents accidental deletions; delegates deletion to `InventoryManager`.

### 3. InventoryItem.java
**Purpose**: Represents an inventory item with attributes (ID, name, category, quantity, price, supplier) and provides getters/setters.

**Why This Way?**
- Encapsulates item data in a single class, ensuring data consistency.
- Implements `Serializable` for potential future file serialization (though currently unused).
- Simple getters/setters allow controlled access to fields.

**Methods**:
- **`public InventoryItem(int itemId, String name, String category, int quantity, double price, String supplier)`**
  - **Description**: Constructor that initializes all fields.
  - **Why?**: Ensures every item has all required attributes at creation.

- **Getters and Setters (`getItemId`, `setItemId`, `getName`, `setName`, etc.)**
  - **Description**: Provide access to each field (e.g., `getItemId` returns the ID, `setName` updates the name).
  - **Why?**: Encapsulation ensures fields are accessed/modified safely, supporting data integrity.

- **`public String toString()`**
  - **Description**: Returns a string representation of the item (e.g., `InventoryItem{itemId='1', name='Laptop', ...}`).
  - **Why?**: Useful for debugging and displaying item details in `updateItem` or `deleteItem`.

### 4. InventoryManager.java
**Purpose**: Manages business logic for CRUD operations and displaying all items, acting as an intermediary between `CLI` and `FileManager`.

**Why This Way?**
- Separates business logic from UI (`CLI`) and storage (`FileManager`), adhering to single-responsibility principle.
- Uses `BinarySearchTree` (via `FileManager`) for efficient item retrieval and `CustomArrayList` for sorting during display.
- Simplifies `CLI` by handling complex operations like sorting and validation.

**Methods**:
- **`public void createItem(InventoryItem item)`**
  - **Description**: Saves a new item to file via `FileManager`.
  - **Workflow**: Calls `FileManager.writeItemToFile(item)`.
  - **Why?**: Delegates storage to `FileManager`, keeping the method simple.

- **`public InventoryItem readItem(int id)`**
  - **Description**: Retrieves an item by ID.
  - **Workflow**: Calls `FileManager.readItemById(id)` and returns the result.
  - **Why?**: Provides a clean interface for item lookup, leveraging `FileManager`’s efficiency.

- **`public boolean updateItem(int id, InventoryItem updatedItem)`**
  - **Description**: Updates an existing item.
  - **Workflow**: Checks if the item exists via `readItem`, sets the updated item’s ID, calls `FileManager.writeItemToFile`, and returns success status.
  - **Why?**: Ensures ID consistency and delegates storage, with a boolean to indicate success.

- **`public boolean deleteItem(int id)`**
  - **Description**: Deletes an item by ID.
  - **Workflow**: Checks if the item exists, calls `FileManager.deleteItemFromFile` with the item’s category and ID, and returns success status.
  - **Why?**: Validates existence before deletion, ensuring safe operations.

- **`public void viewAllItems()`**
  - **Description**: Displays all items sorted by category and name in a formatted table.
  - **Workflow**:
    1. Gets all items from `FileManager.readAllItems` (returns a `BinarySearchTree`).
    2. Collects items into a `CustomArrayList` using `inOrderTraversal`.
    3. Sorts the list by category and name using `SortingAlgorithms.mergeSort`.
    4. Prints a formatted table with ID, name, category, quantity, price, and supplier.
    5. Truncates long strings for display using `truncateString`.
  - **Why?**:
    - **BST to CustomArrayList**: `BinarySearchTree` orders by `itemId`, but display requires category/name sorting, so items are collected into `CustomArrayList` for sorting.
    - **Merge Sort**: Stable and efficient (O(n log n)) for sorting, reusing existing `SortingAlgorithms`.
    - **Formatted Table**: Enhances readability for users.
    - **Truncation**: Prevents table misalignment due to long strings.

- **`private String truncateString(String str, int maxLength)`**
  - **Description**: Truncates a string to `maxLength`, appending "..." if needed.
  - **Why?**: Ensures consistent table formatting in `viewAllItems`.

### 5. FileManager.java
**Purpose**: Handles file I/O, reading/writing items to CSV files organized by category, using `BinarySearchTree` for in-memory storage.

**Why This Way?**
- Centralizes file operations, keeping `InventoryManager` focused on business logic.
- Uses CSV for simplicity and human-readability, with one file per category to organize data.
- `BinarySearchTree` provides efficient search and ordered traversal for file operations.

**Why CSV Files?**
- Simple, text-based format compatible with spreadsheets.
- Category-based files (e.g., `Electronics.csv`) reduce file size and improve organization.
- Headers ensure data clarity.

**Methods**:
- **`private static void ensureDirectoryExists()`**
  - **Description**: Creates the `inventory_data/` directory if it doesn’t exist.
  - **Why?**: Ensures the storage directory is available before file operations.

- **`private static String getFilePath(String category)`**
  - **Description**: Returns the file path for a category’s CSV file (e.g., `inventory_data/Electronics.csv`).
  - **Why?**: Centralizes path construction for consistency.

- **`public static void writeItemToFile(InventoryItem item)`**
  - **Description**: Writes or updates an item in its category’s CSV file.
  - **Workflow**:
    1. Ensures the directory exists.
    2. Reads existing items into a `BinarySearchTree`.
    3. Adds or updates the item in the BST.
    4. Writes all items back to the CSV file using `inOrderTraversal`, including a header.
  - **Why?**:
    - **BST**: Efficiently manages items in memory, avoiding duplicates.
    - **In-Order Traversal**: Writes items in `itemId` order for consistency.
    - **Rewrite Entire File**: Simplifies updates, as CSV doesn’t support in-place edits.

- **`public static boolean deleteItemFromFile(String category, int itemId)`**
  - **Description**: Deletes an item from its category’s CSV file.
  - **Workflow**:
    1. Checks if the file exists.
    2. Reads items into a `BinarySearchTree`.
    3. Removes the item by `itemId`.
    4. Rewrites the remaining items to the file.
  - **Why?**: Rewriting ensures the file reflects the updated BST; boolean indicates success.

- **`public static BinarySearchTree<InventoryItem> readItemsFromCategory(String category)`**
  - **Description**: Reads all items from a category’s CSV file into a `BinarySearchTree`.
  - **Workflow**:
    1. Ensures the directory exists.
    2. Creates a `BinarySearchTree` with `itemId` comparator.
    3. Reads the file, skips the header, parses each line via `parseCSVLine`, and adds items to the BST.
  - **Why?**: BST allows efficient insertion and lookup; returns empty BST if file doesn’t exist.

- **`public static BinarySearchTree<InventoryItem> readAllItems()`**
  - **Description**: Reads all items from all category CSV files.
  - **Workflow**:
    1. Lists all `.csv` files in `inventory_data/`.
    2. For each file, reads items via `readItemsFromCategory` and adds them to a `BinarySearchTree`.
  - **Why?**: Aggregates all items efficiently, leveraging BST for storage.

- **`public static InventoryItem readItemById(int itemId)`**
  - **Description**: Finds an item by ID across all categories.
  - **Workflow**: Calls `readAllItems` and searches the BST for the item.
  - **Why?**: Leverages BST’s efficient search (O(log n) average case).

- **`private static InventoryItem parseCSVLine(String line)`**
  - **Description**: Parses a CSV line into an `InventoryItem`.
  - **Workflow**: Splits the line by commas (respecting quotes), parses fields, and creates an item.
  - **Why?**: Handles CSV formatting robustly, with error handling for invalid data.

- **`private static String escapeCSV(String value)`**
  - **Description**: Escapes special characters (commas, quotes, newlines) for CSV.
  - **Why?**: Ensures valid CSV output, preventing parsing errors.

- **`private static String unescapeCSV(String value)`**
  - **Description**: Unescapes CSV strings, removing quotes and handling double quotes.
  - **Why?**: Correctly interprets CSV input, reversing `escapeCSV`.

### 6. BinarySearchTree.java
**Purpose**: Custom Binary Search Tree implementation for storing `InventoryItem` objects, ordered by `itemId`.

**Why BST?**
- **Efficient Search**: O(log n) average-case time for search, insert, and delete (assuming balanced), better than `CustomArrayList`’s O(n) for search.
- **Ordered Traversal**: In-order traversal provides items sorted by `itemId`, useful for consistent file output.
- **Scalability**: Suitable for larger datasets compared to a linked list or array-based structure.
- **Custom Implementation**: Aligns with the project’s use of custom data structures, avoiding standard Java collections.

**Why Not a Linked List?**
- A linked list has O(n) search time, which is inefficient for `readItemById`, a frequent operation.
- BST’s logarithmic performance and ordered traversal better suit the system’s needs.

**Why Not Self-Balancing BST?**
- A basic BST is simpler and sufficient for small to medium datasets.
- Self-balancing (e.g., AVL, Red-Black) adds complexity without significant benefits unless the dataset is large or insertions are highly skewed.

**Methods**:
- **`public BinarySearchTree(Comparator<T> comparator)`**
  - **Description**: Constructor that initializes the BST with a comparator (compares `itemId` for `InventoryItem`).
  - **Why?**: Allows flexible ordering; `itemId` ensures unique keys.

- **`public void add(T item)`**
  - **Description**: Adds an item to the BST, updating if the `itemId` exists.
  - **Workflow**: Recursively inserts based on `itemId`, increments size.
  - **Why?**: Efficient insertion (O(log n)), handles updates by replacing existing items.

- **`public boolean remove(Object key)`**
  - **Description**: Removes an item by `itemId`.
  - **Workflow**: Recursively finds and removes the node, handling cases (no children, one child, two children).
  - **Why?**: Maintains BST properties during deletion; boolean indicates success.

- **`public T find(Object key)`**
  - **Description**: Finds an item by `itemId`.
  - **Workflow**: Recursively searches based on `itemId`.
  - **Why?**: O(log n) search is critical for `readItemById`.

- **`public void inOrderTraversal(Consumer<T> consumer)`**
  - **Description**: Performs in-order traversal, applying the consumer to each item.
  - **Why?**: Provides items in `itemId` order, used for file writing and collecting items.

- **`public int size()`**
  - **Description**: Returns the number of items.
  - **Why?**: Tracks BST size for `viewAllItems` and empty checks.

- **`public boolean isEmpty()`**
  - **Description**: Checks if the BST is empty.
  - **Why?**: Used in `viewAllItems` to handle empty inventory.

- **`public void clear()`**
  - **Description**: Clears the BST.
  - **Why?**: Provided for completeness, though unused in the current system.

### 7. CustomArrayList.java
**Purpose**: Custom dynamic array implementation, used in `InventoryManager` for sorting items during `viewAllItems`.

**Why This Way?**
- Retained from the original codebase to support `SortingAlgorithms.mergeSort`.
- Used instead of Java’s `ArrayList` to align with the project’s custom data structure approach.
- Only used in `viewAllItems` to collect and sort items, as `BinarySearchTree` orders by `itemId`, not category/name.

**Methods**:
- **`public CustomArrayList()`**
  - **Description**: Constructor with default capacity (10).
  - **Why?**: Initializes an empty list for general use.

- **`public CustomArrayList(int initialCapacity)`**
  - **Description**: Constructor with specified capacity.
  - **Why?**: Allows optimization for known sizes, though unused here.

- **`public void add(T element)`**
  - **Description**: Adds an element, resizing if needed.
  - **Why?**: Used to collect items in `viewAllItems`.

- **`public T get(int index)`**
  - **Description**: Retrieves an element by index.
  - **Why?**: Used during sorting and display in `viewAllItems`.

- **`public T remove(int index)`**
  - **Description**: Removes an element by index.
  - **Why?**: Provided for completeness, unused in the current system.

- **`public boolean remove(T element)`**
  - **Description**: Removes an element by value.
  - **Why?**: Unused but part of the complete implementation.

- **`public void set(int index, T element)`**
  - **Description**: Updates an element at an index.
  - **Why?**: Used by `mergeSort` during sorting.

- **`public int size()`**
  - **Description**: Returns the number of elements.
  - **Why?**: Used for iteration and display in `viewAllItems`.

- **`public boolean isEmpty()`**
  - **Description**: Checks if the list is empty.
  - **Why?**: Unused but standard for collections.

- **`public void clear()`**
  - **Description**: Clears the list.
  - **Why?**: Unused but standard.

- **`public boolean contains(T element)`**
  - **Description**: Checks if an element exists.
  - **Why?**: Unused but part of the implementation.

- **`public int indexOf(T element)`**
  - **Description**: Finds an element’s index.
  - **Why?**: Supports `contains`, though unused.

- **`private void ensureCapacity(int minCapacity)`**
  - **Description**: Resizes the array if needed.
  - **Why?**: Ensures dynamic growth, critical for adding items.

### 8. SortingAlgorithms.java
**Purpose**: Provides a custom merge sort implementation for sorting `CustomArrayList` in `InventoryManager`.

**Why Merge Sort?**
- Stable sorting algorithm, preserving relative order of equal elements.
- O(n log n) time complexity, efficient for the system’s needs.
- Custom implementation aligns with the project’s approach.

**Methods**:
- **`public static <T> void mergeSort(CustomArrayList<T> list, Comparator<T> comparator)`**
  - **Description**: Sorts the list using merge sort.
  - **Workflow**: Recursively divides the list and merges sorted halves.
  - **Why?**: Used in `viewAllItems` to sort by category and name.

- **`private static <T> void mergeSort(CustomArrayList<T> list, int low, int high, Object[] temp, Comparator<T> comparator)`**
  - **Description**: Recursive helper for merge sort.
  - **Why?**: Implements the divide-and-conquer strategy.

- **`private static <T> void merge(CustomArrayList<T> list, int low, int mid, int high, Object[] temp, Comparator<T> comparator)`**
  - **Description**: Merges two sorted sublists.
  - **Why?**: Ensures stable merging, critical for sorting.

## Why Binary Search Tree Over Other Data Structures?
- **Vs. CustomArrayList**: `CustomArrayList` has O(n) search time, making `readItemById` slow. BST’s O(log n) search is more efficient.
- **Vs. Linked List**: A linked list also has O(n) search and traversal, unsuitable for frequent lookups. BST’s structure supports faster searches and ordered output.
- **Vs. Hash Table**: A hash table offers O(1) average-case lookup but doesn’t provide ordered traversal, which is needed for consistent file output.
- **Vs. Self-Balancing BST**: A basic BST is simpler and sufficient for small to medium datasets. Self-balancing adds complexity without significant benefits here.

## Learning Takeaways
1. **Modular Design**: Separating concerns (UI, business logic, storage, data structures) makes the code easier to understand and extend.
2. **Data Structure Choice**: Choosing the right data structure (BST) based on operation frequency (searches, traversals) is critical for performance.
3. **Error Handling**: Robust input validation and exception handling prevent crashes and ensure a good user experience.
4. **File I/O**: CSV files are a practical choice for simple persistence, but require careful handling of special characters.
5. **Custom Implementations**: Building custom data structures (BST, `CustomArrayList`) deepens understanding of algorithms and trade-offs.

## Future Improvements
- **Self-Balancing BST**: Implement AVL or Red-Black Tree for guaranteed O(log n) performance.
- **Database Integration**: Replace CSV with a database (e.g., SQLite) for better scalability.
- **GUI**: Add a graphical interface using JavaFX or Swing for a modern UI.
- **Search Filters**: Allow searching by category, name, or supplier in `viewAllItems`.
- **Unit Tests**: Add JUnit tests to ensure reliability.

This documentation provides a detailed understanding of the Inventory Management System, its components, and design decisions. By studying each class and method, you can learn how to structure a Java application, choose appropriate data structures, and handle file I/O effectively.
