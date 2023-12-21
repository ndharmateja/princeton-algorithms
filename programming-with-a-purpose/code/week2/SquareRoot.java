public class SquareRoot {
    public static void main(String[] args) {
        double EPS = 1E-15;
        double x = Double.parseDouble(args[0]);

        // Newton-Raphson method
        // 1. t(0) = x
        double t = x;

        // 2. Repeat until t(i) = c/t(i) until a certain precision
        // ------ Set t(i+i) = average of t(i) and (x / t(i))
        while (Math.abs(t - (x / t)) > t * EPS) {
            t = (t + x / t) / 2;
        }

        System.out.println("Sqrt(" + x + ") = " + t);
    }
}
