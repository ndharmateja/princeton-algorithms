package q8_stack_with_max;

import test_utils.TestUtils;

public class TestStackWithMax {
    private static void testStackWithMax(StackWithMax stack) {
        TestUtils.doAssertion(stack.isEmpty());
        TestUtils.doAssertion(stack.size() == 0);

        for (int i = 1; i <= 100; i++) {
            stack.push(i);
            TestUtils.doAssertion(stack.size() == i);
            TestUtils.doAssertion(stack.maxValue() == i);
        }

        for (int i = 1; i <= 10; i++) {
            stack.push(i);
            TestUtils.doAssertion(stack.size() == 100 + i);
            TestUtils.doAssertion(stack.maxValue() == 100);
        }

        for (int i = 1; i <= 10; i++) {
            stack.push(210 - i + 1);
            TestUtils.doAssertion(stack.size() == 110 + i,
                    "Expected size: " + (110 + i) + " Actual size: " + stack.size());
            TestUtils.doAssertion(stack.maxValue() == 210);
        }

        for (int i = 1; i <= 10; i++) {
            TestUtils.doAssertion(stack.maxValue() == 210);
            TestUtils.doAssertion(stack.pop() == 200 + i);
        }

        for (int i = 1; i <= 10; i++) {
            TestUtils.doAssertion(stack.maxValue() == 100);
            TestUtils.doAssertion(stack.pop() == 10 - i + 1);
        }

        for (int i = 100; i >= 1; i--) {
            TestUtils.doAssertion(stack.maxValue() == i);
            TestUtils.doAssertion(stack.pop() == i);
        }

    }

    public static void main(String[] args) {
        testStackWithMax(new StackWithMax());
        System.out.println("Testing complete");
    }
}
