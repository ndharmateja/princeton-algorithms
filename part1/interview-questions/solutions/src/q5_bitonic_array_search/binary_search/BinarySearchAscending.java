package q5_bitonic_array_search.binary_search;

public class BinarySearchAscending extends BinarySearch {

    @Override
    protected boolean shouldGoLeft(int[] array, int mid, int target) {
        // In ascending order
        // we should go left if array[mid] > target
        return array[mid] > target;
    }

    @Override
    protected boolean shouldGoRight(int[] array, int mid, int target) {
        // In ascending order
        // we should go right if array[mid] < target
        return array[mid] < target;
    }

}
