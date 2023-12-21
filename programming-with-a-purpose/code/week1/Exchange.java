public class Exchange {
    public static void main(String[] args) {
        int a = 5;
        int b = 7;
        System.out.println(String.format("a: %d, b: %d", a, b));

        int t = a;
        a = b;
        b = t;
        System.out.println(String.format("a: %d, b: %d", a, b));
    }
}
