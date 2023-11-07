public class RightTriangle {
    public static void main(String[] args) {
        // Parse sides
        // long a = Long.parseLong(args[0]);
        // long b = Long.parseLong(args[1]);
        // long c = Long.parseLong(args[2]);

        // long maxSide = Math.max(a, Math.max(b, c));
        // long maxSquared = maxSide * maxSide;
        // long remainingSquaredSum = a * a + b * b + c * c - maxSquared;
        // System.out.println(a > 0 && b > 0 && c > 0 && maxSquared == remainingSquaredSum);

        int n = 123456789;
        int m = 0;
        while (n != 0) {
            m = (10 * m) + (n % 10);
            n = n / 10;
        }
        System.out.println(m);
    }
}
