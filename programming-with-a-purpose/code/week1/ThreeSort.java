public class ThreeSort {
    public static void main(String[] args) {
        int x0 = Integer.parseInt(args[0]);
        int x1 = Integer.parseInt(args[1]);
        int x2 = Integer.parseInt(args[2]);

        int min = Math.min(x0, Math.min(x1, x2));
        int max = Math.max(x0, Math.max(x1, x2));
        int mid = x0 + x1 + x2 - min - max;

        System.out.println(min + " " + mid + " " + max);
    }
}
