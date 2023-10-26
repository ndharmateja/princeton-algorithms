package test_utils;

public class TestUtils {
    public static void doAssertion(boolean expression) {
        if (!expression)
            doAssertion(expression, "");
    }

    public static void doAssertion(boolean expression, String message) {
        if (!expression)
            throw new AssertionError(message);
    }
}
