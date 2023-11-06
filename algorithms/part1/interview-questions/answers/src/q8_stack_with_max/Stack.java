package q8_stack_with_max;

public interface Stack<T> {
    public void push(T t);

    public T pop();

    public T peek();

    public boolean isEmpty();

    public int size();
}
