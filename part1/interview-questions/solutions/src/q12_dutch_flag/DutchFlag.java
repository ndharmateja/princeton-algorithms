package q12_dutch_flag;

import java.util.Arrays;

public class DutchFlag {
    public static final char BLUE = 'b';
    public static final char RED = 'r';
    public static final char WHITE = 'w';

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
     * Invariant maintained: [B, B, B, B, R, R, R, R, -, ... , W, W, W, W]
     * 
     * b -> index of the last blue
     * w -> index of the first white
     * 
     * interval [0, b] -> blues
     * interval [b + 1, i) -> reds
     * interval [w, n) -> whites
     * 
     */
    public void arrange() {
        int b = -1;
        int w = colors.length;

        // i -> current cell's index
        int i = 0;

        // Run as long as i < w
        // Once i reaches w => all colors are done processing
        while (i < w) {
            char currColor = color(i);
            // If current color is white, swap it with the start of white
            // subarray and we don't need to increment 'i' as the new color
            // which was swapped with blue will appear here
            // and we have to deal with it in the next iteration
            if (currColor == WHITE) {
                w--;
                swap(i, w);
                continue;
            }

            // If current color is blue, swap it with the end of the blue's
            // subarray
            if (currColor == BLUE) {
                b++;
                swap(i, b);
            }

            // If color is red, we don't need to do anything

            // We need to increment 'i' if color is blue/red to process
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
            // 2. B, R
            // 3. R, W
            // 4. B, W
            char currColor = colors[i];
            char nextColor = colors[i + 1];
            if (currColor == nextColor)
                continue;
            if (currColor == BLUE && nextColor == RED)
                continue;
            if (currColor == RED && nextColor == WHITE)
                continue;
            if (currColor == BLUE && nextColor == WHITE)
                continue;

            // For any other consecutive color pair, return false
            return false;
        }

        return true;
    }
}
