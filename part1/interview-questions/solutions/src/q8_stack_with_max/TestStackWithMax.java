package q8_stack_with_max;

public class TestStackWithMax {
    private static void doAssertion(boolean expression) {
        if (!expression)
            doAssertion(expression, "");
    }

    private static void doAssertion(boolean expression, String message) {
        if (!expression)
            throw new AssertionError(message);
    }

    private static void testStackWithMax(StackWithMax stack) {
        doAssertion(stack.isEmpty());
        doAssertion(stack.size() == 0);

        for (int i = 1; i <= 100; i++) {
            stack.push(i);
            doAssertion(stack.size() == i);
            doAssertion(stack.maxValue() == i);
        }

        for (int i = 1; i <= 10; i++) {
            stack.push(i);
            doAssertion(stack.size() == 100 + i);
            doAssertion(stack.maxValue() == 100);
        }

        for (int i = 1; i <= 10; i++) {
            stack.push(210 - i + 1);
            doAssertion(stack.size() == 110 + i, "Expected size: " + (110 + i) + " Actual size: " + stack.size());
            doAssertion(stack.maxValue() == 210);
        }

        for (int i = 1; i <= 10; i++) {
            doAssertion(stack.maxValue() == 210);
            doAssertion(stack.pop() == 200 + i);
        }

        for (int i = 1; i <= 10; i++) {
            doAssertion(stack.maxValue() == 100);
            doAssertion(stack.pop() == 10 - i + 1);
        }

        for (int i = 100; i >= 1; i--) {
            doAssertion(stack.maxValue() == i);
            doAssertion(stack.pop() == i);
        }

    }

    public static void main(String[] args) {
        testStackWithMax(new StackWithMax());
        System.out.println("Testing complete");
    }
}
