package q14_inversions;

public class Inversions {
    private static <T extends Comparable<T>> int countCrossInversions(T[] arr, T[] aux, int lo, int mid, int hi) {
        // Copy elements from arr to aux
        for (int i = lo; i <= hi; i++) {
            aux[i] = arr[i];
        }

        // Every time we copy an element from the left subarray (say x)
        // all the elements already copied from the right subarray (# = j - (mid + 1))
        // will be inversions wrt x
        // so we can increment the number of inversions by 'j - (mid + 1)'
        int numCrossInversions = 0;
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            // If i reaches the end
            // the remaining elements are only from the second half
            // which are already there
            if (i > mid)
                break;

            // if j reaches the end
            else if (j > hi) {
                numCrossInversions += (j - (mid + 1));
                arr[k] = aux[i++];
            }

            // if aux[i] > aux[j]
            else if (aux[i].compareTo(aux[j]) > 0)
                arr[k] = aux[j++];

            // if aux[i] <= aux[j]
            else {
                numCrossInversions += (j - (mid + 1));
                arr[k] = aux[i++];
            }
        }

        return numCrossInversions;
    }

    private static <T extends Comparable<T>> int countInversions(T[] arr, T[] aux, int lo, int hi) {
        // For 1 sized arrays return 0, because 0 inversions
        if (hi <= lo)
            return 0;

        // Initialize inversions
        int inversions = 0;

        // Sort left and right halves
        int mid = lo + (hi - lo) / 2;
        inversions += countInversions(arr, aux, lo, mid);
        inversions += countInversions(arr, aux, mid + 1, hi);

        // If already sorted, then no cross inversions
        // if arr[mid] <= arr[mid + 1]
        if (arr[mid].compareTo(arr[mid + 1]) <= 0)
            return inversions;

        // Count cross inversions between left and right halves
        inversions += countCrossInversions(arr, aux, lo, mid, hi);

        // Return the total number of inversions
        return inversions;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T>> int countInversions(T[] arr) {
        int n = arr.length;

        // Create arrCopy to not modify 'arr'
        T[] arrCopy = (T[]) new Comparable[n];
        for (int i = 0; i < arrCopy.length; i++) {
            arrCopy[i] = arr[i];
        }

        // aux is the auxillary array required for merge sort
        T[] aux = (T[]) new Comparable[n];

        // Count inversions and return
        return countInversions(arrCopy, aux, 0, n - 1);
    }
}
