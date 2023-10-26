package q7_queue_with_two_stacks;

public interface Queue<T> {
    public void enqueue(T t);

    public T dequeue();

    public T peek();

    public int size();

    public boolean isEmpty();
}
