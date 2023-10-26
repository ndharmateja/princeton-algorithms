package q5_bitonic_array_search;

import q5_bitonic_array_search.binary_search.BinarySearch;
import q5_bitonic_array_search.binary_search.BinarySearchDescending;
import q5_bitonic_array_search.binary_search.BinarySearchMaximumIndex;

public class BitonicArray {
    public static int search(int[] array, int target) {
        return method1(array, target);
    }

    /**
     * ~ 3 log n solution
     * 
     * @param array  - bitonic array of n distinct ints
     * @param target
     * @return
     */
    public static int method1(int[] array, int target) {
        int n = array.length;

        // Find the index of the peak
        // Peak can only exist in [1, n-2]
        BinarySearch bsMax = new BinarySearchMaximumIndex();
        int maxIndex = bsMax.search(array, 1, n - 2, -1);
        if (array[maxIndex] == target)
            return maxIndex;

        // Search the increasing part => in [0, maxIndex - 1]
        BinarySearch bsAsc = new BinarySearch();
        int ascIndex = bsAsc.search(array, 0, maxIndex - 1, target);
        if (ascIndex != -1)
            return ascIndex;

        // Search the decreasing part => in [maxIndex + 1, n - 1]
        BinarySearch bsDesc = new BinarySearchDescending();
        int descIndex = bsDesc.search(array, maxIndex + 1, n - 1, target);
        if (descIndex != 1)
            return descIndex;

        // If not found anywhere return -1
        return -1;
    }
}
