package q7_queue_with_two_stacks;

import test_utils.TestUtils;

public class TestQueue {
    private static void testQueue(Queue<Integer> queue) {
        // Test size and isEmpty on empty Queue
        TestUtils.doAssertion(queue.isEmpty());
        TestUtils.doAssertion(queue.size() == 0, "Empty queue size should be 0");

        // Test push size and is Empty
        for (int i = 0; i < 100; i++) {
            queue.enqueue(i);
            TestUtils.doAssertion(queue.size() == i + 1);
            TestUtils.doAssertion(!queue.isEmpty());
        }

        // Test peek and pop
        TestUtils.doAssertion(queue.peek() == 0);
        for (int i = 0; i < 100; i++) {
            TestUtils.doAssertion(queue.dequeue() == i);
            TestUtils.doAssertion(queue.size() == 100 - i - 1,
                    "Expected size - " + (100 - i - 1) + " Actual size - " + queue.size());
        }

        // Size should be empty at the end
        TestUtils.doAssertion(queue.isEmpty());
        TestUtils.doAssertion(queue.size() == 0);
    }

    public static void main(String[] args) {
        testQueue(new QueueTwoStacks<>());
        System.out.println("Testing QueueTwoStacks complete");
    }
}
