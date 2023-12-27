public class RevesPuzzle {
    // Move 'n' discs from 'a' to 'c' using 'b' as temp
    private static void hanoi(int n, int topDisc, char a, char b, char c) {
        if (n == 0)
            return;

        // Recursively move the top n-1 discs from 'a' to 'b' using 'c' as temp
        // topDisc will still be the topDisc passed to this function
        hanoi(n - 1, topDisc, a, c, b);

        // Move the bottom most disc from 'a' to 'c'
        // If the top disc number in the n discs is 'topDisc'
        // then the bottom disc number will be 'topDisc + n - 1'
        System.out.println("Move disc " + (topDisc + n - 1) + " from " + a + " to " + c);

        // Recursively move the n-1 discs from 'b' to 'c' using 'a' as temp
        // topDisc will still be the topDisc passed to this function
        hanoi(n - 1, topDisc, b, a, c);
    }

    private static void reves(int n, int topDisc, char a, char b, char c, char d) {
        // Base case
        if (n == 0)
            return;

        // Move top k discs from 'a' to 'b' pole using reves
        // and temp poles as 'c' and 'd' as they are empty
        int k = (int) Math.round(n + 1 - Math.sqrt(2 * n + 1));
        reves(k, 1, a, c, d, b);

        // Move remaining 'n-k' discs from 'a' pole to 'd' pole using hanoi
        // and 'c' pole as temp (as 'b' already has the top k discs)
        // Top disc on those 'n-k' discs will be 'k+1'
        hanoi(n - k, k + 1, a, c, d);

        // Move the 'k' discs in 'b' pole to 'd' pole using reves
        // and temp poles as 'a' and 'c' as they are empty
        reves(k, 1, b, a, c, d);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        reves(n, 1, 'A', 'B', 'C', 'D');
    }
}
