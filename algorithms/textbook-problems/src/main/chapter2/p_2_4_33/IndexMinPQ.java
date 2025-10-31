package main.chapter2.p_2_4_33;

import java.util.NoSuchElementException;

/**
 * An indexed minimum priority queue of generic keys.
 * 
 * Each key is associated with an integer index between 0 and maxN - 1.
 * The data structure supports insertion, deletion, key updates,
 * and retrieval/removal of the minimum key efficiently.
 *
 * @param <T> the generic type of keys on this priority queue,
 *            must implement Comparable<Item>.
 */
public class IndexMinPQ<T extends Comparable<T>> {
    private int maxN;
    private int N;
    private int[] qp;
    private int[] pq;
    private T[] keys;

    /**
     * Creates an empty indexed minimum priority queue with capacity for indices
     * between 0 and maxN - 1 (inclusive).
     *
     * @param maxN the maximum number of elements (and valid index range)
     * @throws IllegalArgumentException if maxN < 0
     */
    @SuppressWarnings("unchecked")
    public IndexMinPQ(int maxN) {
        if (maxN < 0)
            throw new IllegalArgumentException("maxN cannot be negative");

        this.maxN = maxN;
        qp = new int[maxN];
        for (int i = 0; i < maxN; i++)
            qp[i] = -1;

        pq = new int[maxN + 1];
        keys = (T[]) new Comparable[maxN + 1];
        N = 0;
    }

    private void swim(int k) {
        while (k > 1 && greater(k / 2, k)) {
            exch(k / 2, k);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k <= N) {
            int j = 2 * k;
            if (j < N && greater(j, j + 1))
                j++;
            if (!greater(k, j))
                break;
            exch(k, j);
            j = k;
        }
    }

    private boolean greater(int i, int j) {
        return keys[i].compareTo(keys[j]) > 0;
    }

    private void exch(int i, int j) {
        int tempIndex = pq[i];
        pq[i] = pq[j];
        pq[j] = tempIndex;

        T temp = keys[i];
        keys[i] = keys[j];
        keys[j] = temp;

        int ithItemIndex = pq[i];
        int jthItemIndex = pq[j];
        tempIndex = qp[ithItemIndex];
        qp[ithItemIndex] = qp[jthItemIndex];
        qp[jthItemIndex] = tempIndex;
    }

    private void validateIndex(int k) {
        if (k < 0 || k > maxN)
            throw new IllegalArgumentException("Index k out of bounds");
    }

    private void validateItem(T item) {
        if (item == null)
            throw new NullPointerException("Given item is null");
    }

    /**
     * Inserts a new item and associates it with the given index k.
     *
     * @param k    the index to associate with this item (0 ≤ k < maxN)
     * @param item the key to insert
     * @throws IllegalArgumentException if k is out of range
     * @throws IllegalArgumentException if index k is already in the priority queue
     * @throws NullPointerException     if item is null
     */
    public void insert(int k, T item) {
        validateIndex(k);
        validateItem(item);
        if (qp[k] != -1)
            throw new IllegalArgumentException("Index k not empty.");

        N++;
        pq[N] = k;
        keys[N] = item;
        qp[k] = N;

        swim(N);
    }

    /**
     * Changes the key associated with index k to the new given item.
     *
     * @param k    the index whose key should be changed
     * @param item the new key
     * @throws IllegalArgumentException if k is out of range
     * @throws NoSuchElementException   if index k is not currently in the PQ
     * @throws NullPointerException     if item is null
     */
    public void change(int k, T item) {
        validateIndex(k);
        validateItem(item);
        if (qp[k] == -1)
            throw new NoSuchElementException("Index k is currently not in the priority queue");

        int indexInPq = qp[k];
        keys[indexInPq] = item;

        swim(indexInPq);
        sink(indexInPq);
    }

    /**
     * Returns true if index k is currently associated with some item in the PQ.
     *
     * @param k the index to check
     * @return true if k is in the PQ; false otherwise
     * @throws IllegalArgumentException if k is out of range
     */
    public boolean contains(int k) {
        validateIndex(k);
        return qp[k] != -1;
    }

    /**
     * Removes the key and its associated index k from the priority queue.
     *
     * @param k the index to delete
     * @throws IllegalArgumentException if k is out of range
     * @throws NoSuchElementException   if index k is not in the PQ
     */
    public void delete(int k) {
        validateIndex(k);
        if (qp[k] == -1)
            throw new NoSuchElementException("Index k is currently not in the priority queue");

        // Swap root with the last element and decrement size
        int kthIndexInPQ = qp[k];
        exch(kthIndexInPQ, N);
        N--;

        // Prevent loitering
        qp[pq[N]] = -1;
        pq[N] = 0;
        keys[N] = null;

        // restore heap property
        swim(kthIndexInPQ);
        sink(kthIndexInPQ);
    }

    /**
     * Returns the minimal key in the PQ without removing it.
     *
     * @return the smallest key currently in the PQ
     * @throws NoSuchElementException if the PQ is empty
     */
    public T min() {
        if (isEmpty())
            throw new NoSuchElementException("Priority Queue is empty");
        return keys[1];
    }

    /**
     * Returns the index associated with the minimal key in the PQ.
     *
     * @return the index whose associated key is minimal
     * @throws NoSuchElementException if the PQ is empty
     */
    public int minIndex() {
        if (isEmpty())
            throw new NoSuchElementException("Priority Queue is empty");
        return pq[1];
    }

    /**
     * Removes the minimal key and returns its associated index.
     *
     * @return the index associated with the minimal key that was removed
     * @throws NoSuchElementException if the PQ is empty
     */
    public int delMin() {
        if (isEmpty())
            throw new NoSuchElementException("Priority Queue is empty");
        int index = pq[1];

        // Swap root with the last element and decrement size
        exch(1, N);
        N--;

        // Prevent loitering
        qp[pq[N]] = -1;
        pq[N] = 0;
        keys[N] = null;

        // restore heap property
        sink(1);

        // Return the index of the deleted element
        return index;
    }

    /**
     * Returns true if the PQ is empty.
     *
     * @return true if no keys are in the PQ
     */
    public boolean isEmpty() {
        return N == 0;
    }

    /**
     * Returns the number of elements currently in the PQ.
     *
     * @return number of (index, key) pairs
     */
    public int size() {
        return N;
    }
}
