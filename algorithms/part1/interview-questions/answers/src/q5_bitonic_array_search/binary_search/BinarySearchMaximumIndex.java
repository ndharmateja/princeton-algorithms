package q5_bitonic_array_search.binary_search;

public class BinarySearchMaximumIndex extends BinarySearch {

    @Override
    protected boolean shouldGoLeft(int[] array, int mid, int target) {
        // While looking for maximum index in bitonic array
        // we go left when array[mid - 1], array[mid], array[mid + 1]
        // are in decreasing order (which means mid is in the decreasing
        // part of the bitonic array)
        return array[mid - 1] > array[mid] && array[mid] > array[mid + 1];
    }

    @Override
    protected boolean shouldGoRight(int[] array, int mid, int target) {
        // While looking for maximum index in bitonic array
        // we go right when array[mid - 1], array[mid], array[mid + 1]
        // are in incrasing order (which means mid is in the increasing
        // part of the bitonic array)
        return array[mid - 1] < array[mid] && array[mid] < array[mid + 1];
    }

}
