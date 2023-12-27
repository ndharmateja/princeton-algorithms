public class Hanoi {
    public static int hanoi(int n, char a, char b, char c) {
        if (n == 0)
            return 0;

        // Initialize steps count
        int steps = 0;

        // Move top n-1 from a to b using c as temp
        steps += hanoi(n - 1, a, c, b);

        // Move the bottom disc from a to c
        System.out.println("Move disc " + n + ": " + a + " -> " + c);
        steps++;

        // Move the n-1 discs from b to c using a as temp
        steps += hanoi(n - 1, b, a, c);

        // Return total steps
        return steps;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int steps = hanoi(n, 'A', 'B', 'C');
        System.out.println("Total steps: " + steps);
    }
}
