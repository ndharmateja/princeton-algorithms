package q8_stack_with_max;

public class StackWithMax implements Stack<Integer> {
    Stack<Integer> valueStack;
    Stack<Integer> maxStack;

    public StackWithMax() {
        valueStack = new ArrayStack<>();
        maxStack = new ArrayStack<>();
    }

    public Integer maxValue() {
        return maxStack.peek();
    }

    @Override
    public void push(Integer val) {
        valueStack.push(val);
        if (maxStack.isEmpty()) {
            maxStack.push(val);
        } else {
            maxStack.push(Integer.max(val, maxStack.peek()));
        }
    }

    @Override
    public Integer pop() {
        maxStack.pop();
        return valueStack.pop();
    }

    @Override
    public Integer peek() {
        return valueStack.peek();
    }

    @Override
    public boolean isEmpty() {
        return valueStack.isEmpty();
    }

    @Override
    public int size() {
        return valueStack.size();
    }
}
