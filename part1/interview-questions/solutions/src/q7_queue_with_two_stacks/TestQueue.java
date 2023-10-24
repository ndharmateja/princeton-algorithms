package q7_queue_with_two_stacks;

public class TestQueue {
    private static void doAssertion(boolean expression) {
        if (!expression)
            doAssertion(expression, "");
    }

    private static void doAssertion(boolean expression, String message) {
        if (!expression)
            throw new AssertionError(message);
    }

    private static void testQueue(Queue<Integer> queue) {
        // Test size and isEmpty on empty Queue
        doAssertion(queue.isEmpty());
        doAssertion(queue.size() == 0, "Empty queue size should be 0");

        // Test push size and is Empty
        for (int i = 0; i < 100; i++) {
            queue.enqueue(i);
            doAssertion(queue.size() == i + 1);
            doAssertion(!queue.isEmpty());
        }

        // Test peek and pop
        doAssertion(queue.peek() == 0);
        for (int i = 0; i < 100; i++) {
            doAssertion(queue.dequeue() == i);
            doAssertion(queue.size() == 100 - i - 1,
                    "Expected size - " + (100 - i - 1) + " Actual size - " + queue.size());
        }

        // Size should be empty at the end
        doAssertion(queue.isEmpty());
        doAssertion(queue.size() == 0);
    }

    public static void main(String[] args) {
        testQueue(new QueueTwoStacks<>());
        System.out.println("Testing QueueTwoStacks complete");
    }
}
