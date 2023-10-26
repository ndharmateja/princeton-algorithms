package q4_three_sum;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import test_utils.TestUtils;

public class TestThreeSum {
    // Reference: https://codereview.stackexchange.com/a/156193
    public static Integer[] generateRandAndUniqSet(int desiredSize) {
        Integer[] arrayResult = new Integer[desiredSize];
        Set<Integer> uniq = new HashSet<>();
        Random rand = new Random();
        Integer counter = 0;
        while (counter < desiredSize) {
            Integer randValue = rand.nextInt(desiredSize * 3); /* a larger interval! */
            if (uniq.add(randValue)) {
                arrayResult[counter++] = randValue;
            }
        }
        return arrayResult;
    }

    private static void testThreeSum() {
        // for size n = 1, 2, 4, 8, .. , 1024
        for (int n = 1; n < 1025; n *= 2) {
            // Run 10 times for each size
            for (int i = 0; i < 10; i++) {
                Integer[] array = generateRandAndUniqSet(n);

                int actual = ThreeSum.count(array);
                int expected = ThreeSum.countBruteForce(array);

                TestUtils.doAssertion(actual == expected,
                        "Num calls to swap(), expected <= " + expected + ", actual = " + actual);
            }
        }
    }

    public static void main(String[] args) {
        testThreeSum();
        System.out.println("Tests passed");
    }
}
