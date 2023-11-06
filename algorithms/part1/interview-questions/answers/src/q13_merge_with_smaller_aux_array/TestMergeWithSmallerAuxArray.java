package q13_merge_with_smaller_aux_array;

import test_utils.TestUtils;

public class TestMergeWithSmallerAuxArray {
    public static void main(String[] args) {
        Integer[][] inputs = {
                { 6, 7, 8, 9, 10, 1, 2, 3, 4, 5 },
                { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 },
                { 1, 2, 3, 9, 10, 4, 5, 6, 7, 8 },
                { 1, 2, 3, 7, 8, 4, 5, 6, 9, 10 },
                { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 },
                { 1, 2 },
                { 2, 1 }
        };
        Integer[][] expectedOutputs = {
                { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 },
                { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 },
                { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 },
                { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 },
                { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 },
                { 1, 2 },
                { 1, 2 }
        };

        for (int i = 0; i < inputs.length; i++) {
            Integer[] input = inputs[i];
            Integer[] aux = new Integer[input.length / 2];
            Integer[] expectedOutput = expectedOutputs[i];
            MergeWithSmallerAuxArray.merge(input, aux);
            TestUtils.doAssertion(TestUtils.arrayEquals(input, expectedOutput));
        }

        System.out.println("Testing complete");
    }

}
