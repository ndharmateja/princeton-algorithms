package q5_bitonic_array_search.binary_search;

public class BinarySearchDescending extends BinarySearch {

    @Override
    protected boolean shouldGoLeft(int[] array, int mid, int target) {
        // In descending order
        // we should go left when array[mid] < target
        // which is when we go right in ascending order binary search
        //
        // super - BinarySearch (which defaults to ascending order)
        // that is why super.shouldGoRight()
        return super.shouldGoRight(array, mid, target);
    }

    @Override
    protected boolean shouldGoRight(int[] array, int mid, int target) {
        // In descending order
        // we should go right when array[mid] > target
        // which is when we go left in ascending order binary search
        //
        // super - BinarySearch (which defaults to ascending order)
        // that is why super.shouldGoLeft()
        return super.shouldGoLeft(array, mid, target);
    }
}
