public class Inversions {
    // Return the number of inversions in the permutation a[].
    public static long count(int[] a) {
        long count = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[i] > a[j])
                    count++;
            }
        }
        return count;
    }

    /**
     * Return a permutation of length n with exactly k inversions.
     * 
     * Method:
     * 1. We will try to put the number 'n-1' at 0 (to create n-1 inversions)
     * and then try to put the number 'n-2' at 1 (to create n-2 inversions)
     * 2. We keep doing that until we reach a point where number of inversions
     * will become zero and we can insert 0, 1, 2, 3.. in order after the last
     * inserted number (which was created for inversions)
     * 3. Another case is num_inversions < n-k, then we have to insert
     * n-k in middle of remaining array
     * 
     * Eg: n = 10, k = 20
     * array: [ _, _, _, _, _, _, _, _, _, _ ]
     * k: 20
     * 
     * After putting n-1=9
     * array: [ 9, _, _, _, _, _, _, _, _, _]
     * k: 11
     * 
     * After putting n-2=8
     * array: [ 9, 8, _, _, _, _, _, _, _, _ ]
     * k: 3
     * 
     * Now k < n-3
     * so we insert 7 in middle so that we still get k extra inversions
     * array: [ 9, 8, _, _, _, _, 7, _, _, _ ]
     * k: 0
     * 
     * Now fill the remaining slots with 0, 1, 2, 3..
     * array: [ 9, 8, 0, 1, 2, 3, 7, 4, 5, 6 ]
     * 
     * @param n
     * @param k
     * @return
     */
    public static int[] generate(int n, long k) {
        // Base case
        int[] array = new int[n];
        if (n == 0)
            return array;

        int r = n - 1;
        int l = 0;
        while (true) {
            // k != 0 && k >= r
            if (k != 0 && k >= r) {
                k -= r;
                array[l++] = r--;
            }
            // The above 'if' condition will eventually be
            // reduced to one of the below cases
            // k == 0 || k < r
            else {
                // Index to insert 'r'
                // to add the remaining 'k' extra inversions
                int index = (n - 1) - (int) k;
                array[index] = r;

                // Fill remaining slots with 0, 1, 2, 3..
                int curr = 0;
                while (l < array.length) {
                    if (l == index) {
                        l++;
                        continue;
                    }
                    array[l++] = curr++;
                }
                break;
            }
        }

        return array;
    }

    private static void printArray(int[] array) {
        for (int x : array) {
            StdOut.print(x + " ");
        }
        StdOut.println();
    }

    // Takes an integer n and a long k as command-line arguments,
    // and prints a permutation of length n with exactly k inversions.
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        long k = Long.parseLong(args[1]);
        printArray(generate(n, k));
    }
}
