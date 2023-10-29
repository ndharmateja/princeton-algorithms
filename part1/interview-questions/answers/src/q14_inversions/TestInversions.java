package q14_inversions;

import java.util.Arrays;

import edu.princeton.cs.algs4.StdRandom;
import test_utils.TestUtils;

public class TestInversions {
    private static <T extends Comparable<T>> int countInversionsBruteForce(T[] arr) {
        int numInversions = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i].compareTo(arr[j]) > 0)
                    numInversions++;
            }
        }
        return numInversions;
    }

    public static void main(String[] args) {
        for (int sz = 1; sz < 4097; sz *= 2) {
            Integer[] array = new Integer[sz];
            for (int i = 0; i < array.length; i++) {
                array[i] = i;
            }

            for (int trial = 0; trial < 100; trial++) {
                StdRandom.shuffle(array);
                int numInversionsBruteForce = countInversionsBruteForce(array);
                int numInversions = Inversions.countInversions(array);

                TestUtils.doAssertion(numInversions == numInversionsBruteForce,
                        "Inversions counted wrong for " + Arrays.toString(array) + ", expected: "
                                + numInversionsBruteForce + ", actual: " + numInversions);
            }
        }

        System.out.println("Testing complete");
    }
}
