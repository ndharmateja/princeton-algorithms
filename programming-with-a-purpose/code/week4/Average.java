import edu.princeton.cs.algs4.StdIn;

public class Average {
    public static void main(String[] args) {
        double sum = 0.0;
        int n = 0;
        while (!StdIn.isEmpty()) {
            sum += StdIn.readDouble();
            n++;
        }
        System.out.println("Average: " + (sum / n));
    }
}
