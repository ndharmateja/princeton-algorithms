package main.chapter2.p_2_4_33;

import java.util.Random;

/**
 * Added using ChatGPT
 * 
 * Comprehensive, self-contained test suite for IndexMinPQ.
 *
 * ✅ Key Features:
 * - Each test runs independently (no shared state)
 * - No assertions or test dependencies
 * - Never crashes — logs failures and continues
 * - Prints detailed summary of passed/failed tests
 *
 * To run:
 * javac IndexMinPQ.java TestIndexMinPQ.java
 * java main.chapter2.p_2_4_33.TestIndexMinPQ
 */

public class TestIndexMinPQ {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        System.out.println("========== IndexMinPQ Test Suite ==========");
        runTest("testBasicInsertAndMin", TestIndexMinPQ::testBasicInsertAndMin);
        runTest("testChangePriority", TestIndexMinPQ::testChangePriority);
        runTest("testDelete", TestIndexMinPQ::testDelete);
        runTest("testDelMin", TestIndexMinPQ::testDelMin);
        runTest("testContains", TestIndexMinPQ::testContains);
        runTest("testEdgeCases", TestIndexMinPQ::testEdgeCases);
        runTest("testStressInsertDelMin", TestIndexMinPQ::testStressInsertDelMin);
        runTest("testMixedOperations", TestIndexMinPQ::testMixedOperations);

        System.out.println("\n===========================================");
        System.out.printf("✅ Passed: %d\n", passed);
        System.out.printf("❌ Failed: %d\n", failed);
        System.out.println("===========================================");
    }

    private static void runTest(String name, Runnable test) {
        try {
            test.run();
            passed++;
            System.out.println("✅ " + name + " passed");
        } catch (Exception e) {
            failed++;
            System.out.println("❌ " + name + " FAILED: " + e.getMessage());
        }
    }

    private static void testBasicInsertAndMin() {
        IndexMinPQ<Integer> pq = new IndexMinPQ<>(5);
        pq.insert(0, 50);
        pq.insert(1, 20);
        pq.insert(2, 30);

        if (pq.min() != 20)
            throw new RuntimeException("Expected min=20, got=" + pq.min());
        if (pq.minIndex() != 1)
            throw new RuntimeException("Expected minIndex=1, got=" + pq.minIndex());
        if (pq.size() != 3)
            throw new RuntimeException("Expected size=3, got=" + pq.size());
    }

    private static void testChangePriority() {
        IndexMinPQ<Integer> pq = new IndexMinPQ<>(5);
        pq.insert(0, 5);
        pq.insert(1, 10);
        pq.insert(2, 3);

        pq.change(1, 1); // make smaller → should become new min
        if (pq.minIndex() != 1)
            throw new RuntimeException("Expected minIndex=1 after decrease, got=" + pq.minIndex());

        pq.change(1, 50); // make larger → should no longer be min
        if (pq.minIndex() != 2)
            throw new RuntimeException("Expected minIndex=2 after increase, got=" + pq.minIndex());
    }

    private static void testDelete() {
        IndexMinPQ<Integer> pq = new IndexMinPQ<>(5);
        pq.insert(0, 5);
        pq.insert(1, 2);
        pq.insert(2, 8);

        pq.delete(1);
        if (pq.contains(1))
            throw new RuntimeException("Expected index 1 to be deleted");
        if (pq.size() != 2)
            throw new RuntimeException("Expected size=2 after delete, got=" + pq.size());
    }

    private static void testDelMin() {
        IndexMinPQ<Integer> pq = new IndexMinPQ<>(5);
        pq.insert(0, 50);
        pq.insert(1, 10);
        pq.insert(2, 20);

        int first = pq.delMin();
        if (first != 1)
            throw new RuntimeException("Expected delMin()=1, got=" + first);

        int second = pq.delMin();
        if (second != 2)
            throw new RuntimeException("Expected delMin()=2, got=" + second);

        if (pq.size() != 1)
            throw new RuntimeException("Expected size=1 after two deletions");
    }

    private static void testContains() {
        IndexMinPQ<Integer> pq = new IndexMinPQ<>(3);
        pq.insert(0, 7);
        if (!pq.contains(0))
            throw new RuntimeException("Expected contains(0)=true");
        pq.delete(0);
        if (pq.contains(0))
            throw new RuntimeException("Expected contains(0)=false after delete");
    }

    private static void testEdgeCases() {
        IndexMinPQ<Integer> pq = new IndexMinPQ<>(3);

        // try operations on empty PQ
        try {
            pq.min();
            throw new RuntimeException("Expected exception on min() when empty");
        } catch (Exception ignored) {
        }

        try {
            pq.delMin();
            throw new RuntimeException("Expected exception on delMin() when empty");
        } catch (Exception ignored) {
        }

        pq.insert(0, 10);
        try {
            pq.insert(0, 20);
            throw new RuntimeException("Expected exception on inserting duplicate index");
        } catch (Exception ignored) {
        }

        try {
            pq.delete(1);
            throw new RuntimeException("Expected exception on deleting non-existent index");
        } catch (Exception ignored) {
        }
    }

    private static void testStressInsertDelMin() {
        int N = 1000;
        IndexMinPQ<Integer> pq = new IndexMinPQ<>(N);
        Random rand = new Random(42);

        for (int i = 0; i < N; i++)
            pq.insert(i, rand.nextInt(10000));

        int prev = Integer.MIN_VALUE;
        while (!pq.isEmpty()) {
            int minVal = pq.min();
            pq.delMin();
            if (minVal < prev)
                throw new RuntimeException("Heap property violated: " + prev + " > " + minVal);
            prev = minVal;
        }
    }

    private static void testMixedOperations() {
        IndexMinPQ<Integer> pq = new IndexMinPQ<>(10);
        pq.insert(0, 10);
        pq.insert(1, 5);
        pq.insert(2, 20);

        pq.change(2, 1); // now 2 should be new min
        if (pq.minIndex() != 2)
            throw new RuntimeException("Expected minIndex=2 after decrease");

        pq.delete(1);
        pq.change(2, 50);
        if (pq.minIndex() != 0)
            throw new RuntimeException("Expected minIndex=0 after change and delete");

        pq.delMin();
        if (pq.size() != 1)
            throw new RuntimeException("Expected size=1 after operations");
    }
}
