import edu.princeton.cs.algs4.Picture;
import java.awt.Color;
import java.util.Arrays;

public class SeamCarver {
    private Picture picture;
    private double[][] energyMatrix;
    private int[] verticalSeam;
    private int[] horizontalSeam;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null)
            throw new IllegalArgumentException("picture cannot be null");
        initialize(new Picture(picture));
    }

    private void initialize(Picture picture) {
        this.picture = new Picture(picture);
        this.verticalSeam = null;
        this.horizontalSeam = null;
        this.computeEnergies();
    }

    private void computeEnergies() {
        this.energyMatrix = new double[picture.width()][picture.height()];
        for (int x = 0; x < this.width(); x++) {
            for (int y = 0; y < this.height(); y++) {
                energyMatrix[x][y] = isBorderPixel(x, y) ? 1000 : computeEnergy(x, y);
            }
        }
    }

    private double computeEnergy(int x, int y) {
        double energy = 0;
        Color left = picture.get(x - 1, y);
        Color right = picture.get(x + 1, y);
        Color top = picture.get(x, y - 1);
        Color bottom = picture.get(x, y + 1);

        energy += Math.pow(left.getRed() - right.getRed(), 2);
        energy += Math.pow(left.getGreen() - right.getGreen(), 2);
        energy += Math.pow(left.getBlue() - right.getBlue(), 2);
        energy += Math.pow(top.getRed() - bottom.getRed(), 2);
        energy += Math.pow(top.getGreen() - bottom.getGreen(), 2);
        energy += Math.pow(top.getBlue() - bottom.getBlue(), 2);

        energy = Math.sqrt(energy);
        return energy;
    }

    private boolean isBorderPixel(int x, int y) {
        return x == 0 || x == this.width() - 1 || y == 0 || y == this.height() - 1;
    }

    // current picture
    public Picture picture() {
        return new Picture(this.picture);
    }

    // width of current picture
    public int width() {
        return this.picture.width();
    }

    // height of current picture
    public int height() {
        return this.picture.height();
    }

    private void validateX(int x) {
        if (x < 0 || x >= this.width())
            throw new IllegalArgumentException("invalid 'x' value");
    }

    private void validateY(int y) {
        if (y < 0 || y >= this.height())
            throw new IllegalArgumentException("invalid 'y' value");
    }

    private void validateSeam(int[] seam, int seamLengthReference, int seamValuesLimit) {
        if (seam == null)
            throw new IllegalArgumentException("'seam' cannot be null");
        if (seam.length != seamLengthReference)
            throw new IllegalArgumentException("length of the seam doesn't match the width/height");
        for (int val : seam)
            if (val < 0 || val >= seamValuesLimit)
                throw new IllegalArgumentException("seam value not within the width/height of the picture");
        for (int i = 1; i < seam.length; i++)
            if (Math.abs(seam[i] - seam[i - 1]) > 1)
                throw new IllegalArgumentException("consecutive seam values should differ by atmost 1");
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        validateX(x);
        validateY(y);
        return this.energyMatrix[x][y];
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        if (this.horizontalSeam != null)
            return this.horizontalSeam;

        int w = this.width();
        int h = this.height();
        if (h == 1)
            return new int[w];

        // Create the dp array and copy the first row (which is the first col
        // of the picture)
        double[][] dp = new double[w][h];
        for (int y = 0; y < h; y++)
            dp[0][y] = energyMatrix[0][y];

        // for each subsequent row (col in the picture)
        for (int x = 1; x < w; x++) {
            dp[x][0] = min(dp[x - 1][0], dp[x][0]) + energyMatrix[x][0];
            for (int y = 1; y < h - 1; y++)
                dp[x][y] = min(dp[x - 1][y], dp[x][y - 1], dp[x][y + 1]) + energyMatrix[x][y];
            dp[x][h - 1] = min(dp[x - 1][h - 1], dp[x][h - 2]) + energyMatrix[x][h - 1];
        }

        // Backtrack the path from the min dp value in the last col
        // of the picture to the first col
        this.horizontalSeam = new int[w];
        int x = w - 1;
        int minY = 0;
        double minValue = dp[x][0];
        for (int y = 1; y < h; y++) {
            if (dp[x][y] < minValue) {
                minY = y;
                minValue = dp[x][y];
            }
        }
        this.horizontalSeam[x] = minY;

        for (x = w - 2; x >= 0; x--) {
            int y = minY;
            minY = findMinIndexForHorizontalSeam(dp, x, y);
            this.horizontalSeam[x] = minY;
        }

        return Arrays.copyOf(this.horizontalSeam, w);
    }

    // Returns either y-1 or y or y+1
    // depending on the min value among dp[x][y-1], dp[x][y], dp[x][y+1]
    // respectively
    private int findMinIndexForHorizontalSeam(double[][] dp, int x, int y) {
        int h = this.height();

        if (y == 0)
            return dp[x][y] <= dp[x][y + 1] ? y : y + 1;
        if (y == h - 1)
            return dp[x][y - 1] <= dp[x][y] ? y - 1 : y;

        if (dp[x][y - 1] <= dp[x][y] && dp[x][y - 1] <= dp[x][y + 1])
            return y - 1;
        else if (dp[x][y] <= dp[x][y - 1] && dp[x][y] <= dp[x][y + 1])
            return y;
        else
            return y + 1;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        if (this.verticalSeam != null)
            return this.verticalSeam;

        int w = this.width();
        int h = this.height();
        if (w == 1)
            return new int[h];

        // Create the dp array and copy the first col (which is the first row
        // of the picture)
        double[][] dp = new double[w][h];
        for (int x = 0; x < w; x++)
            dp[x][0] = energyMatrix[x][0];

        // for each subsequent col (row in the picture)
        for (int y = 1; y < h; y++) {
            dp[0][y] = min(dp[0][y - 1], dp[1][y - 1]) + energyMatrix[0][y];
            for (int x = 1; x < w - 1; x++)
                dp[x][y] = min(dp[x - 1][y - 1], dp[x][y - 1], dp[x + 1][y - 1]) + energyMatrix[x][y];
            dp[w - 1][y] = min(dp[w - 2][y - 1], dp[w - 1][y - 1]) + energyMatrix[w - 1][y];
        }

        // Backtrack the path from the min dp value in the last row
        // of the picture to the top row
        this.verticalSeam = new int[h];
        int y = h - 1;
        int minIndex = 0;
        double minValue = dp[0][y];
        for (int x = 1; x < w; x++) {
            if (dp[x][h - 1] < minValue) {
                minIndex = x;
                minValue = dp[x][h - 1];
            }
        }
        this.verticalSeam[y] = minIndex;

        for (y = h - 2; y >= 0; y--) {
            int x = minIndex;
            minIndex = findMinIndexForVerticalSeam(dp, x, y);
            this.verticalSeam[y] = minIndex;
        }

        // print2dArray(dp);
        return Arrays.copyOf(this.verticalSeam, h);
    }

    // Returns either x-1 or x or x+1
    // depending on the min value among dp[x-1][y], dp[x][y], dp[x+1][y]
    // respectively
    private int findMinIndexForVerticalSeam(double[][] dp, int x, int y) {
        int w = this.width();

        if (x == 0)
            return dp[x][y] <= dp[x + 1][y] ? x : x + 1;
        if (x == w - 1)
            return dp[x - 1][y] <= dp[x][y] ? x - 1 : x;

        if (dp[x - 1][y] <= dp[x][y] && dp[x - 1][y] <= dp[x + 1][y])
            return x - 1;
        else if (dp[x][y] <= dp[x - 1][y] && dp[x][y] <= dp[x + 1][y])
            return x;
        else
            return x + 1;
    }

    private void print2dArray(double[][] arr) {
        for (int y = 0; y < this.height(); y++) {
            for (int x = 0; x < this.width(); x++) {
                System.out.print(String.format("%7.2f ", arr[x][y]));
            }
            System.out.println();
        }
        System.out.println();
    }

    private static double min(double x, double y) {
        return x < y ? x : y;
    }

    private static double min(double x, double y, double z) {
        return min(min(x, y), z);
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (this.height() <= 1)
            throw new IllegalArgumentException("cannot remove horizontal seam as height is <= 1");
        validateSeam(seam, this.width(), this.height());

        int w = this.width();
        int h = this.height();
        Picture newPicture = new Picture(w, h - 1);

        for (int x = 0; x < w; x++) {
            int y = 0;
            int oldY = 0;
            while (y < h - 1) {
                if (oldY == seam[x]) {
                    oldY++;
                    continue;
                }
                newPicture.setRGB(x, y++, this.picture.getRGB(x, oldY++));
            }
        }

        initialize(newPicture);
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (this.width() <= 1)
            throw new IllegalArgumentException("cannot remove vertical seam as width is <= 1");
        validateSeam(seam, this.height(), this.width());

        int w = this.width();
        int h = this.height();
        Picture newPicture = new Picture(w - 1, h);

        for (int y = 0; y < h; y++) {
            int x = 0;
            int oldX = 0;
            while (x < w - 1) {
                if (oldX == seam[y]) {
                    oldX++;
                    continue;
                }
                newPicture.setRGB(x++, y, this.picture.getRGB(oldX++, y));
            }
        }

        initialize(newPicture);
    }

    // unit testing (optional)
    public static void main(String[] args) {
        SeamCarver sc = new SeamCarver(new Picture("data/6x5.png"));
        int[] verticalSeam = sc.findVerticalSeam();
        System.out.println(Arrays.toString(verticalSeam));
        sc.removeVerticalSeam(verticalSeam);
        System.out.println("removed vertical seam");
    }
}
