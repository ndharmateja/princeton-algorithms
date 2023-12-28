public class Ramanujan {
    private static long cube(long i) {
        return i * i * i;
    }

    // Is n a Ramanujan number?
    public static boolean isRamanujan(long n) {
        int cubeRoot = (int) Math.floor(Math.cbrt(n));

        long a = -1, b = -1;
        for (long i = 1; i <= cubeRoot; i++) {
            // If we are looking at an i which is already a or b
            // then that pair is already found and we don't need to
            // look at this i
            // (initial values of -1 works when looking at every i for the first time)
            if (i == a || i == b)
                continue;

            // Find the compliment number of i = (n - i^3)^(1/3)
            int iCompliment = (int) Math.round(Math.cbrt(n - i * i * i));

            // If i^3 + iCompliment^3 != n
            // we can skip this i
            if (cube(i) + cube(iCompliment) != n)
                continue;

            // If there is a pair which is already found
            // which means this is the second distinct pair
            // we can return true
            if (a != -1)
                return true;

            // If finding for the first time
            // update the values of a and b
            a = i;
            b = iCompliment;
        }

        // If we reach here => two distinct pairs don't exist
        // and we return false
        return false;
    }

    // Takes a long integer command-line arguments n and prints true if
    // n is a Ramanujan number, and false otherwise.
    public static void main(String[] args) {
        long n = Long.parseLong(args[0]);
        // long n = 1729;
        StdOut.println(isRamanujan(n));
    }
}