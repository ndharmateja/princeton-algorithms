package q5_bitonic_array_search;

import java.util.Arrays;

import test_utils.TestUtils;

public class TestBitonicArraySearch {
    public static void main(String[] args) {
        int[][] arrays = new int[][] {
                { 1, 2, -3, -5 },
                { 1, 2, -3, -5 },
                { 1, 2, -3, -5 },
                { 1, 2, -3, -5 },
                { 1, 2, -3, -5 },
                { 1, 2, -3, -5 },
                { 1, 2, -3 },
                { 1, 2, -3 },
                { 1, 2, -3 },
                { 1, 2, -3 },
                { -5, -4, -3, 1, 2, 5, -15 },
                { -5, -4, -3, 1, 2, 5, -15 },
                { -5, -4, -3, 1, 2, 5, -15 },
                { -5, -4, -3, 1, 2, 5, -15 },
        };
        int[] targets = new int[] {
                1,
                2,
                -3,
                -5,
                -10,
                0,
                1,
                2,
                -3,
                -5,
                2,
                -15,
                -5,
                -7
        };
        int[] expectedOutputs = new int[] {
                0,
                1,
                2,
                3,
                -1,
                -1,
                0,
                1,
                2,
                -1,
                4,
                6,
                0,
                -1
        };

        for (int i = 0; i < arrays.length; i++) {
            int[] array = arrays[i];
            int target = targets[i];
            int expectedOutput = expectedOutputs[i];
            int actualOutput = BitonicArray.search(array, target);
            TestUtils.doAssertion(expectedOutput == actualOutput, "Array: " + Arrays.toString(array) + ", target: "
                    + target + ", expected: " + expectedOutput + ", actual: " + actualOutput);
        }
        System.out.println("Tests passed");
    }
}
