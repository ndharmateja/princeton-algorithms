import edu.princeton.cs.algs4.Picture;
import java.awt.Color;

public class SeamCarver {
    private final Picture picture;
    private double[][] energyMatrix;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture);
        this.energyMatrix = new double[picture.width()][picture.height()];
        this.computeEnergies();
    }

    private void computeEnergies() {
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
        return new int[0];
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        return new int[0];
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (this.height() <= 1)
            throw new IllegalArgumentException("cannot remove horizontal seam as height is <= 1");
        validateSeam(seam, this.width(), this.height());
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (this.width() <= 1)
            throw new IllegalArgumentException("cannot remove vertical seam as width is <= 1");
        validateSeam(seam, this.height(), this.width());
    }

    // unit testing (optional)
    public static void main(String[] args) {
        SeamCarver sc = new SeamCarver(new Picture("data/6x5.png"));
        System.out.println(sc.energy(6, 4));
    }
}
