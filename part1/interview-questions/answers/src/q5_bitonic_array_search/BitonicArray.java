package q5_bitonic_array_search;

import q5_bitonic_array_search.binary_search.BinarySearch;
import q5_bitonic_array_search.binary_search.BinarySearchAscending;
import q5_bitonic_array_search.binary_search.BinarySearchDescending;
import q5_bitonic_array_search.binary_search.BinarySearchMaximumIndex;

public class BitonicArray {
    public static int search(int[] array, int target) {
        return method1(array, target);
    }

    /**
     * ~ 3 log n solution
     * 
     * @param array  - array of n distinct ints
     * @param target
     * @return
     */
    public static int method1(int[] array, int target) {
        int n = array.length;

        // Find the index of the peak
        BinarySearch bsMax = new BinarySearchMaximumIndex();
        int maxIndex = bsMax.search(array, 0, n - 1, -1);

        // Search the increasing part => in [0, maxIndex]
        BinarySearch bsAsc = new BinarySearchAscending();
        int ascIndex = bsAsc.search(array, 0, maxIndex, target);
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
