package main.chapter2.p_2_4_33;

import java.util.Arrays;
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
    // qp - stores
    private int maxN;
    private final int[] qp;
    private final int[] pq;
    private final T[] keys;
    private int size;

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

        // maxN represents the number of elements our priority queue can store
        // We initialize qp to all -1 because initially no elements are associated
        // with any indices
        this.maxN = maxN;
        qp = new int[maxN];
        Arrays.fill(qp, -1);

        // Create the pq array with maxN+1 elements as index 0 is unused in our
        // priority queue implementation
        pq = new int[maxN + 1];
        Arrays.fill(pq, -1);

        // Initialize the keys array
        this.keys = (T[]) new Comparable[maxN];
        this.size = 0;
    }

    private void swim(int k) {
        while (k > 1 && greater(k / 2, k)) {
            exch(k / 2, k);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k <= size) {
            int j = 2 * k;
            if (j < size && greater(j, j + 1))
                j++;
            if (!greater(k, j))
                break;
            exch(k, j);
            k = j;
        }
    }

    private boolean greater(int i, int j) {
        return keys[pq[i]].compareTo(keys[pq[j]]) > 0;
    }

    // i and j are 1-based indices in the heap array pq
    private void exch(int i, int j) {
        // Swap the qp elements corresponding to i and j in the heap
        int ithIndex = pq[i];
        int jthIndex = pq[j];
        qp[ithIndex] = j;
        qp[jthIndex] = i;

        // Exchange the pq elements
        int temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }

    private void validateIndex(int k) {
        if (k < 0 || k >= maxN)
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
        // Validations
        validateIndex(k);
        validateItem(item);
        if (qp[k] != -1)
            throw new IllegalArgumentException("Index k not empty.");

        size++;
        pq[size] = k;
        keys[k] = item;
        qp[k] = size;

        swim(size);
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
        // Validations
        validateIndex(k);
        validateItem(item);
        if (qp[k] == -1)
            throw new NoSuchElementException("Index k is currently not in the priority queue");

        // Change the item in the keys array
        keys[k] = item;

        // Find where element corresponding to index 'k' is stored in the heap
        // and do swim if new item's priority is less than the old item's priority
        // or sink if the new item's priority is greater than the old item's
        // Instead of an if condition, we do both a sink and a swim
        // and only one of them will be executed appropriately
        int indexInPq = qp[k];
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
        // Validation
        validateIndex(k);

        // If qp[k] == -1, it means that no item was associated with index k
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
        // Validations
        validateIndex(k);
        if (qp[k] == -1)
            throw new NoSuchElementException("Index k is currently not in the priority queue");

        //
        int heapIndexOfK = qp[k];

        // Check if the element to delete is the last element in the heap
        // then we don't need to do any heap restoring operations
        if (heapIndexOfK == this.size) {
            // Prevent loitering
            qp[k] = -1;
            keys[k] = null;
            pq[this.size--] = -1;
            return;
        }

        exch(heapIndexOfK, this.size--);

        // Prevent loitering
        qp[k] = -1;
        keys[k] = null;
        pq[size + 1] = -1;

        // Restore heap property
        swim(heapIndexOfK);
        sink(heapIndexOfK);
    }

    /**
     * Returns the minimal key in the PQ without removing it.
     *
     * @return the smallest key currently in the PQ
     * @throws NoSuchElementException if the PQ is empty
     */
    public T min() {
        // Validation
        if (isEmpty())
            throw new NoSuchElementException("Priority Queue is empty");

        // Return the min element
        // pq[1] stores the index of the min element
        return keys[pq[1]];
    }

    /**
     * Returns the index associated with the minimal key in the PQ.
     *
     * @return the index whose associated key is minimal
     * @throws NoSuchElementException if the PQ is empty
     */
    public int minIndex() {
        // Validation
        if (isEmpty())
            throw new NoSuchElementException("Priority Queue is empty");

        // pq[1] stores the index of the min element
        return pq[1];
    }

    /**
     * Removes the minimal key and returns its associated index.
     *
     * @return the index associated with the minimal key that was removed
     * @throws NoSuchElementException if the PQ is empty
     */
    public int delMin() {
        // Validation
        if (isEmpty())
            throw new NoSuchElementException("Priority Queue is empty");

        // Get the index of the min element
        int indexOfMinElement = pq[1];

        // Exchange 1 and size in the heap and decrement the size
        exch(1, this.size--);

        // Prevent loitering
        qp[indexOfMinElement] = -1;
        keys[indexOfMinElement] = null;
        pq[size + 1] = -1;

        // Restore heap order
        sink(1);

        return indexOfMinElement;
    }

    /**
     * Returns true if the PQ is empty.
     *
     * @return true if no keys are in the PQ
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of elements currently in the PQ.
     *
     * @return number of (index, key) pairs
     */
    public int size() {
        return size;
    }
}
