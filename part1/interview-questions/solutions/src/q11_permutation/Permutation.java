package q11_permutation;

import edu.princeton.cs.algs4.Quick;
import edu.princeton.cs.algs4.StdRandom;
import test_utils.TestUtils;

public class Permutation {
    public static boolean arePermutations(Integer[] array1, Integer[] array2) {
        // If array lengths are different
        // they can't be permutations of each other
        if (array1.length != array2.length)
            return false;

        // Sort both arrays
        Quick.sort(array1);
        Quick.sort(array2);

        // If they are same after sorting
        // that means they are permutaions of each other
        return TestUtils.arrayEquals(array1, array2);
    }

    public static void main(String[] args) {
        Integer[] array1 = new Integer[1000];
        Integer[] array2 = new Integer[1000];
        for (int i = 0; i < array2.length; i++) {
            array1[i] = i;
            array2[i] = i;
        }

        StdRandom.shuffle(array1);
        StdRandom.shuffle(array2);

        System.out.println(Permutation.arePermutations(array1, array2));
    }
}
