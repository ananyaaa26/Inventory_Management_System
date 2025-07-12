package src.datastructures;

import src.InventoryItem;
import java.io.Serializable;
import java.util.function.Consumer;

/**
 * Binary Search Tree implementation specifically for storing inventory items
 * Uses itemId as the key for ordering
 */
public class BinarySearchTree implements Serializable {
    private static final long serialVersionUID = 1L;

    private Node root;
    private int size;

    private class Node {
        InventoryItem data;
        Node left;
        Node right;

        Node(InventoryItem data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    /**
     * Constructor for InventoryItem BST
     */
    public BinarySearchTree() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Adds an item to the BST
     * @param item The item to add
     */
    public void add(InventoryItem item) {
        root = addRecursive(root, item);
        size++;
    }

    private Node addRecursive(Node current, InventoryItem item) {
        if (current == null) {
            return new Node(item);
        }

        int compareResult = Integer.compare(item.getItemId(), current.data.getItemId());
        if (compareResult < 0) {
            current.left = addRecursive(current.left, item);
        } else if (compareResult > 0) {
            current.right = addRecursive(current.right, item);
        } else {
            // Update existing item
            current.data = item;
            size--; // Adjust size since we're replacing
        }
        return current;
    }

    /**
     * Removes an item by its ID
     * @param itemId The ID of the item to remove
     * @return true if item was removed
     */
    public boolean remove(Object key) {
        int initialSize = size;
        int itemId;

        if (key instanceof Integer) {
            itemId = (Integer) key;
        } else if (key instanceof InventoryItem) {
            itemId = ((InventoryItem) key).getItemId();
        } else {
            return false;
        }

        root = removeRecursive(root, itemId);
        return size < initialSize;
    }

    private Node removeRecursive(Node current, int itemId) {
        if (current == null) {
            return null;
        }

        int compareResult = Integer.compare(itemId, current.data.getItemId());
        if (compareResult < 0) {
            current.left = removeRecursive(current.left, itemId);
        } else if (compareResult > 0) {
            current.right = removeRecursive(current.right, itemId);
        } else {
            size--;
            if (current.left == null) {
                return current.right;
            } else if (current.right == null) {
                return current.left;
            } else {
                Node successor = findMin(current.right);
                current.data = successor.data;
                current.right = removeRecursive(current.right, successor.data.getItemId());
            }
        }
        return current;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    /**
     * Finds an item by its ID
     * @param itemId The ID to search for
     * @return The item if found, null otherwise
     */
    public InventoryItem find(int itemId) {
        return findRecursive(root, itemId);
    }

    public InventoryItem find(Object key) {
        int itemId;

        if (key instanceof Integer) {
            itemId = (Integer) key;
        } else if (key instanceof InventoryItem) {
            itemId = ((InventoryItem) key).getItemId();
        } else {
            return null;
        }

        return findRecursive(root, itemId);
    }

    private InventoryItem findRecursive(Node current, int itemId) {
        if (current == null) {
            return null;
        }

        int compareResult = Integer.compare(itemId, current.data.getItemId());
        if (compareResult == 0) {
            return current.data;
        } else if (compareResult < 0) {
            return findRecursive(current.left, itemId);
        } else {
            return findRecursive(current.right, itemId);
        }
    }

    /**
     * Checks if an item with the given name exists
     * @param name The name to search for
     * @return The item if found, null otherwise
     */
    public InventoryItem findByName(String name) {
        return findByNameRecursive(root, name);
    }

    private InventoryItem findByNameRecursive(Node current, String name) {
        if (current == null) {
            return null;
        }

        // Check current node
        if (current.data.getName().equalsIgnoreCase(name)) {
            return current.data;
        }

        // Check left subtree
        InventoryItem leftResult = findByNameRecursive(current.left, name);
        if (leftResult != null) {
            return leftResult;
        }

        // Check right subtree
        return findByNameRecursive(current.right, name);
    }

    /**
     * Performs an in-order traversal, applying the consumer to each item
     * @param consumer The consumer to process each item
     */
    public void inOrderTraversal(Consumer<InventoryItem> consumer) {
        inOrderTraversalRecursive(root, consumer);
    }

    private void inOrderTraversalRecursive(Node current, Consumer<InventoryItem> consumer) {
        if (current != null) {
            inOrderTraversalRecursive(current.left, consumer);
            consumer.accept(current.data);
            inOrderTraversalRecursive(current.right, consumer);
        }
    }

    /**
     * Returns the number of items in the BST
     * @return The size
     */
    public int size() {
        return size;
    }

    /**
     * Checks if the BST is empty
     * @return true if empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the BST
     */
    public void clear() {
        root = null;
        size = 0;
    }
}
