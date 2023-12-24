public class Divisors {

    // Returns the greatest common divisor of a and b.
    public static int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int bCopy = b;
            b = a % b;
            a = bCopy;
        }
        return a;
    }

    // Returns the least common multiple of a and b.
    public static int lcm(int a, int b) {
        if (a == 0 || b == 0)
            return 0;
        return (Math.abs(a) / gcd(a, b)) * Math.abs(b);
    }

    // Returns true if a and b are relatively prime; false otherwise.
    public static boolean areRelativelyPrime(int a, int b) {
        return gcd(a, b) == 1;
    }

    // Returns the number of integers between 1 and n that are
    // relatively prime with n.
    public static int totient(int n) {
        if (n <= 0)
            return 0;
        if (n == 1)
            return 1;
        int count = 0;
        for (int i = 1; i < n; i++) {
            if (areRelativelyPrime(i, n))
                count++;
        }
        return count;
    }

    // Takes two integer command-line arguments a and b and prints
    // each function, evaluated in the format (and order) {} given below.
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);

        System.out.println("gcd(" + x + ", " + y + ") = " + gcd(x, y));
        System.out.println("lcm(" + x + ", " + y + ") = " + lcm(x, y));
        System.out.println("areRelativelyPrime(" + x + ", " + y + ") = " + areRelativelyPrime(x, y));
        System.out.println("totient(" + x + ") = " + totient(x));
        System.out.println("totient(" + y + ") = " + totient(y));
    }
}