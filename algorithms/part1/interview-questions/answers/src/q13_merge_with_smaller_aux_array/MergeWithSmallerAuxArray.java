package q13_merge_with_smaller_aux_array;

public class MergeWithSmallerAuxArray {
    private static <T extends Comparable<T>> boolean less(T a, T b) {
        return a.compareTo(b) < 0;
    }

    public static <T extends Comparable<T>> void merge(T[] a, T[] aux) {
        assert a.length % 2 == 0;
        assert a.length / 2 == aux.length;

        // Copy first half of a to aux
        int n = aux.length;
        for (int i = 0; i < aux.length; i++) {
            aux[i] = a[i];
        }

        // Process is same as normal merge now
        // Till first half is processed, no intersection between
        // k and i and j, they will never meet
        // While second half is being processed
        // we can stop if i reaches the end of aux array
        int i = 0;
        int j = n;
        for (int k = 0; k < a.length; k++) {
            if (i >= n) {
                break;
            } else if (j >= 2 * n) {
                a[k] = aux[i++];
            } else if (less(a[j], aux[i])) {
                a[k] = a[j++];
            } else {
                a[k] = aux[i++];
            }
        }
    }
}
