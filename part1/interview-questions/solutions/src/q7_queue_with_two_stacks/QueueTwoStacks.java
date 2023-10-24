package q7_queue_with_two_stacks;

import java.util.Stack;

public class QueueTwoStacks<T> implements Queue<T> {
    private Stack<T> stack1;
    private Stack<T> stack2;

    public QueueTwoStacks() {
        stack1 = new Stack<>();
        stack2 = new Stack<>();
    }

    @Override
    public void enqueue(T t) {
        stack1.push(t);
    }

    @Override
    public T dequeue() {
        while (!stack1.isEmpty()) {
            stack2.push(stack1.pop());
        }
        T result = stack2.pop();
        while (!stack2.isEmpty()) {
            stack1.push(stack2.pop());
        }
        return result;
    }

    @Override
    public T peek() {
        while (!stack1.isEmpty()) {
            stack2.push(stack1.pop());
        }
        T result = stack2.peek();
        while (!stack2.isEmpty()) {
            stack1.push(stack2.pop());
        }
        return result;
    }

    @Override
    public int size() {
        return stack1.size();
    }

    @Override
    public boolean isEmpty() {
        return stack1.isEmpty();
    }
}
