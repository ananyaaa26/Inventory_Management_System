package src.datastructures;

import src.InventoryItem;
import java.util.Comparator;

/**
 * Custom sorting algorithms implementation
 * Used for sorting inventory items by different criteria
 */
public class SortingAlgorithms {

    /**
     * Merge sort implementation for CustomArrayList of InventoryItems
     * @param list The list to sort
     * @param comparator The comparator to use for comparison
     */
    public static void mergeSort(CustomArrayList list, Comparator<InventoryItem> comparator) {
        if (list.size() <= 1) {
            return;
        }

        // Create temporary array for merging
        InventoryItem[] temp = new InventoryItem[list.size()];

        // Call the recursive merge sort
        mergeSort(list, 0, list.size() - 1, temp, comparator);
    }

    private static void mergeSort(CustomArrayList list, int low, int high, InventoryItem[] temp, Comparator<InventoryItem> comparator) {
        if (low < high) {
            int mid = low + (high - low) / 2;

            // Sort first and second halves
            mergeSort(list, low, mid, temp, comparator);
            mergeSort(list, mid + 1, high, temp, comparator);

            // Merge the sorted halves
            merge(list, low, mid, high, temp, comparator);
        }
    }

    private static void merge(CustomArrayList list, int low, int mid, int high, InventoryItem[] temp, Comparator<InventoryItem> comparator) {
        // Copy data to temp arrays
        for (int i = low; i <= high; i++) {
            temp[i] = list.get(i);
        }

        int i = low;      // Initial index of first subarray
        int j = mid + 1;  // Initial index of second subarray
        int k = low;      // Initial index of merged subarray

        // Merge the temp arrays back into list
        while (i <= mid && j <= high) {
            if (comparator.compare(temp[i], temp[j]) <= 0) {
                list.set(k, temp[i]);
                i++;
            } else {
                list.set(k, temp[j]);
                j++;
            }
            k++;
        }

        // Copy the remaining elements of left subarray, if any
        while (i <= mid) {
            list.set(k, temp[i]);
            i++;
            k++;
        }

        // Copy the remaining elements of right subarray, if any
        while (j <= high) {
            list.set(k, temp[j]);
            j++;
            k++;
        }
    }
}
