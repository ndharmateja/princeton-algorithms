package q4_three_sum;

import edu.princeton.cs.algs4.Quick;

public class ThreeSum {
    /**
     * Counts the number of pairs of numbers in range [l, h]
     * that add up to target
     * 
     * @param a      - array of distinct ints in ascending order
     * @param l      - lower index
     * @param h      - higher index
     * @param target
     * @return
     */
    private static int countTwoSum(Integer[] a, int l, int h, int target) {
        int count = 0;
        while (l < h) {
            // If sum of left and right values < target
            // it means we have to increment left pointer
            // to increase the sum
            if (a[l] + a[h] < target) {
                l++;
            }
            // If sum of left and right values > target
            // it means we have to decrement left pointer
            // to reduce the sum
            else if (a[l] + a[h] > target) {
                h--;
            }
            // If sum of left and right = target
            // we increment count
            // and increment l and decrement h
            // since all values are distinct
            else {
                count++;
                l++;
                h--;
            }
        }

        return count;
    }

    /**
     * Counts number of triplets in the array that add up to zero
     * 
     * @param a - array of distinct ints
     * @return
     */
    public static int count(Integer[] a) {
        // Sort the array in ascending order
        Quick.sort(a);

        int n = a.length;
        int count = 0;
        for (int i = 0; i < a.length; i++) {
            // For every i, count the number of pairs in [i+1, n-1]
            // that add up to -a[i]
            // and increment count with that number
            count += countTwoSum(a, i + 1, n - 1, -a[i]);
        }

        return count;
    }

    public static int countBruteForce(Integer[] a) {
        int count = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                for (int k = j + 1; k < a.length; k++) {
                    if (a[i] + a[j] + a[k] == 0)
                        count++;
                }
            }
        }
        return count;
    }
}
