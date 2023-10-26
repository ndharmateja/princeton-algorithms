package q5_bitonic_array_search.binary_search;

public abstract class BinarySearch {
    protected abstract boolean shouldGoLeft(int[] array, int mid, int target);

    protected abstract boolean shouldGoRight(int[] array, int mid, int target);

    public int search(int[] array, int lo, int hi, int target) {
        if (lo > hi)
            return -1;
        int mid = (lo + hi) / 2;
        if (shouldGoLeft(array, mid, target))
            return search(array, lo, mid - 1, target);
        if (shouldGoRight(array, mid, target))
            return search(array, mid + 1, hi, target);

        // We return the mid index
        // if shouldGoLeft() and shouldGoRight() return false
        return mid;
    }
}
