import java.awt.Color;

public class KernelFilter {
    private static final double[][] IDENTITY_FILTER = {
            { 0, 0, 0 },
            { 0, 1, 0 },
            { 0, 0, 0 }
    };
    private static final double[][] GAUSSIAN_BLUR_FILTER = {
            { 1 / 16.0, 2 / 16.0, 1 / 16.0 },
            { 2 / 16.0, 4 / 16.0, 2 / 16.0 },
            { 1 / 16.0, 2 / 16.0, 1 / 16.0 }
    };
    private static final double[][] SHARPEN_FILTER = {
            { 0, -1, 0 },
            { -1, 5, -1 },
            { 0, -1, 0 }
    };
    private static final double[][] LAPLACIAN_FILTER = {
            { -1, -1, -1 },
            { -1, 8, -1 },
            { -1, -1, -1 }
    };
    private static final double[][] EMBOSS_FILTER = {
            { -2, -1, 0 },
            { -1, 1, 1 },
            { 0, 1, 2 }
    };
    private static final double[][] MOTION_BLUR_FILTER = {
            { 1.0 / 9, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 1.0 / 9, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 1.0 / 9, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 1.0 / 9, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 1.0 / 9, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 1.0 / 9, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 1.0 / 9, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 1.0 / 9, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 1.0 / 9 },
    };

    private static int clampAndRound(double val) {
        int output = (int) Math.round(val);
        if (output < 0)
            return 0;
        if (output > 255)
            return 255;
        return output;
    }

    // Apply the kernel filter
    // from startC column and startR row
    private static Color applyKernelToPixel(Picture picture, double[][] kernel, int startC, int startR) {
        int kernelSize = kernel.length;

        double newRed = 0;
        double newGreen = 0;
        double newBlue = 0;
        for (int r = 0; r < kernelSize; r++) {
            for (int c = 0; c < kernelSize; c++) {
                // Periodic boundary conditions
                // If overflow out of the picture to left or right
                // wrap around
                int pictureCol = (c + startC + picture.width()) % picture.width();
                int pictureRow = (r + startR + picture.height()) % picture.height();

                newRed += picture.get(pictureCol, pictureRow).getRed() * kernel[r][c];
                newGreen += picture.get(pictureCol, pictureRow).getGreen() * kernel[r][c];
                newBlue += picture.get(pictureCol, pictureRow).getBlue() * kernel[r][c];
            }
        }

        return new Color(
                clampAndRound(newRed),
                clampAndRound(newGreen),
                clampAndRound(newBlue));
    }

    private static Picture applyKernel(Picture picture, double[][] kernel) {
        int width = picture.width();
        int height = picture.height();
        int kernelSize = kernel.length;
        int halfKernelSize = kernelSize / 2;

        // Create empty output picture
        Picture output = new Picture(width, height);

        // Apply filter for each pixel in the output
        for (int c = 0; c < width; c++) {
            for (int r = 0; r < height; r++) {
                int startC = c - halfKernelSize;
                int startR = r - halfKernelSize;

                output.set(c, r, applyKernelToPixel(picture, kernel, startC, startR));
            }
        }

        return output;
    }

    // Returns a new picture that applies the identity filter to the given picture.
    public static Picture identity(Picture picture) {
        return applyKernel(picture, IDENTITY_FILTER);
    }

    // Returns a new picture that applies a Gaussian blur filter to the given
    // picture.
    public static Picture gaussian(Picture picture) {
        return applyKernel(picture, GAUSSIAN_BLUR_FILTER);
    }

    // Returns a new picture that applies a sharpen filter to the given picture.
    public static Picture sharpen(Picture picture) {
        return applyKernel(picture, SHARPEN_FILTER);
    }

    // Returns a new picture that applies an Laplacian filter to the given picture.
    public static Picture laplacian(Picture picture) {
        return applyKernel(picture, LAPLACIAN_FILTER);
    }

    // Returns a new picture that applies an emboss filter to the given picture.
    public static Picture emboss(Picture picture) {
        return applyKernel(picture, EMBOSS_FILTER);
    }

    // Returns a new picture that applies a motion blur filter to the given picture.
    public static Picture motionBlur(Picture picture) {
        return applyKernel(picture, MOTION_BLUR_FILTER);
    }

    // Test client (ungraded).
    public static void main(String[] args) {
        Picture input = new Picture(args.length > 0 ? args[0] : "../data/baboon.png");

        identity(input).show();
        gaussian(input).show();
        sharpen(input).show();
        laplacian(input).show();
        emboss(input).show();
        motionBlur(input).show();
    }
}