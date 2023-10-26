package q12_dutch_flag;

import edu.princeton.cs.algs4.StdRandom;
import test_utils.TestUtils;

public class TestDutchFlag {
    private static final char[] ALL_COLORS = new char[] { DutchFlag.RED, DutchFlag.WHITE, DutchFlag.BLUE };

    private static char[] createSameColor(int size, char color) {
        char[] colors = new char[size];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = color;
        }
        return colors;
    }

    private static char getRandomColor() {
        int index = StdRandom.discrete(new double[] { 1.0 / 3, 1.0 / 3, 1.0 / 3 });
        return ALL_COLORS[index];
    }

    private static char[] createRandomColors(int size) {
        char[] colors = new char[size];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = getRandomColor();
        }
        return colors;
    }

    private static void testOneFlag(DutchFlag flag, int n) {
        flag.arrange();
        TestUtils.doAssertion(flag.validate(), "Flag not arranged properly: " + flag);
        TestUtils.doAssertion(flag.getNumColorCalls() <= n,
                "Num calls to color(), expected <= " + n + ", actual = " + flag.getNumColorCalls());
        TestUtils.doAssertion(flag.getNumSwapCalls() <= n,
                "Num calls to swap(), expected <= " + n + ", actual = " + flag.getNumSwapCalls());
    }

    private static void testAllSameColor() {
        // For each color
        for (char color : ALL_COLORS) {
            // For each size 1, 2, 4, 8, 16, .. , 1024
            for (int n = 1; n < 1025; n = n * 2) {
                DutchFlag flag = new DutchFlag(createSameColor(n, color));
                testOneFlag(flag, n);
            }
        }
    }

    private static void testRandom() {
        // For each size 1, 2, 4, 8, 16, .. , 1024
        for (int n = 1; n < 1025; n = n * 2) {
            // Test each size for 50000 times for a random permutation
            for (int i = 0; i < 50000; i++) {
                DutchFlag flag = new DutchFlag(createRandomColors(n));
                testOneFlag(flag, n);
            }
        }
    }

    public static void main(String[] args) {
        testAllSameColor();
        testRandom();
        System.out.println("All tests passed");
    }
}
