public class Ruler {
    public static String ruler(int n) {
        if (n == 0)
            return " ";
        return ruler(n - 1) + n + ruler(n - 1);
    }

    public static void main(String[] args) {
        System.out.println(ruler(4));
    }
}
