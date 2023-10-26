package q12_dutch_flag;

import java.util.Arrays;

public class DutchFlag {
    public static final char RED = 'r';
    public static final char WHITE = 'w';
    public static final char BLUE = 'b';

    private char[] colors;
    private int numSwapCalls = 0;
    private int numColorCalls = 0;

    public DutchFlag(char[] colors) {
        this.colors = colors;
    }

    private void swap(int i, int j) {
        numSwapCalls++;
        char temp = colors[i];
        colors[i] = colors[j];
        colors[j] = temp;
    }

    private char color(int i) {
        numColorCalls++;
        return colors[i];
    }

    /**
     * Invariant maintained: [R, R, R, R, W, W, W, W, -, ... , B, B, B, B]
     * 
     * r -> index of the last red
     * b -> index of the first blue
     * 
     * interval [0, r] -> reds
     * interval [r + 1, i) -> whites
     * interval [b, n)
     * 
     */
    public void arrange() {
        int r = -1;
        int b = colors.length;

        // i -> current cell's index
        int i = 0;

        // Run as long as i < b
        // Once i reaches b => all colors are done processing
        while (i < b) {
            char currColor = color(i);
            // If current color is blue, swap it with the start of blue
            // subarray and we don't need to increment 'i' as the new color
            // which was swapped with blue will appear here
            // and we have to deal with it in the next iteration
            if (currColor == BLUE) {
                b--;
                swap(i, b);
                continue;
            }

            // If current color is red, swap it with the end of the red's
            // subarray
            if (currColor == RED) {
                r++;
                swap(i, r);
            }

            // If color is white, we don't need to do anything

            // We need to increment 'i' if color is red/white to process
            // the next color in the next iteration
            i++;
        }
    }

    @Override
    public String toString() {
        return "DutchFlag [colors=" + Arrays.toString(colors) + ", numSwapCalls=" + numSwapCalls + ", numColorCalls="
                + numColorCalls + "]";
    }

    public int getNumSwapCalls() {
        return numSwapCalls;
    }

    public int getNumColorCalls() {
        return numColorCalls;
    }

    public boolean validate() {
        for (int i = 0; i < colors.length - 1; i++) {
            // For a valid arrangement the only possible consecutive colors are
            // 1. equal
            // 2. R, W
            // 3. R, B
            // 4. W, B
            char currColor = colors[i];
            char nextColor = colors[i + 1];
            if (currColor == nextColor)
                continue;
            if (currColor == RED && nextColor == WHITE)
                continue;
            if (currColor == RED && nextColor == BLUE)
                continue;
            if (currColor == WHITE && nextColor == BLUE)
                continue;

            // For any other consecutive color pair, return false
            return false;
        }

        return true;
    }
}
