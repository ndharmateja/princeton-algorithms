public class UniformRandomNumbers {
    public static void main(String[] args) {
        double x0 = Math.random();
        double x1 = Math.random();
        double x2 = Math.random();
        double x3 = Math.random();
        double x4 = Math.random();

        System.out.println(x0);
        System.out.println(x1);
        System.out.println(x2);
        System.out.println(x3);
        System.out.println(x4);

        System.out.println("Min: " + Math.min(Math.min(x0, x1), Math.min(x2, Math.min(x3, x4))));
        System.out.println("Max: " + Math.max(Math.max(x0, x1), Math.max(x2, Math.max(x3, x4))));
        System.out.println("Average: " + ((x0 + x1 + x2 + x3 + x4) / 5));
    }
}
