
/******************************************************************************
 *  Compilation:  javac Potential.java
 *  Execution:    java Potential < input.txt
 *  Dependencies: Charge.java Picture.java StdIn.java
 *                https://introcs.cs.princeton.edu/java/31datatype/charges.txt
 *
 *  Potential value visualization for a set of charges.
 *
 *  % java Potential < charges.txt
 *
 *
 ******************************************************************************/

import java.awt.Color;

public class Potential {
    private static class Charge {
        private final double rx, ry; // position
        private final double q; // charge

        public Charge(double x0, double y0, double q0) {
            rx = x0;
            ry = y0;
            q = q0;
        }

        public double potentialAt(double x, double y) {
            double k = 8.99e09;
            double dx = x - rx;
            double dy = y - ry;
            return k * q / Math.sqrt(dx * dx + dy * dy);
        }

        public String toString() {
            return q + " at (" + rx + ", " + ry + ")";
        }
    }

    public static void main(String[] args) {

        // read in n point charges
        int n = StdIn.readInt();
        Charge[] a = new Charge[n];
        for (int k = 0; k < n; k++) {
            double x0 = StdIn.readDouble();
            double y0 = StdIn.readDouble();
            double q0 = StdIn.readDouble();
            a[k] = new Charge(x0, y0, q0);
        }

        // compute the potential at each point and plot
        int SIZE = 800;
        Picture pic = new Picture(SIZE, SIZE);
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                double potential = 0.0;
                for (int k = 0; k < n; k++) {
                    double x = 1.0 * i / SIZE;
                    double y = 1.0 * j / SIZE;
                    potential += a[k].potentialAt(x, y);
                }
                potential = 128 + potential / 2.0e10;
                int t = 0;
                if (potential < 0)
                    t = 0;
                else if (potential > 255)
                    t = 255;
                else
                    t = (int) potential;

                // Color maps
                // Color c = Color.getHSBColor(t / 255.0f, 0.9f, 0.9f);
                // Color c = Color.getHSBColor(0.75f * t / 40, 0.8f, 0.8f);

                // Discontinuous grayscale map
                // int gray = (t * 37) % 255;
                // Color c = new Color(gray, gray, gray);

                // Simple grayscale map
                Color c = new Color(t, t, t);

                // Set the pixel color
                pic.set(i, SIZE - 1 - j, c);
            }
        }
        pic.show();
    }
}