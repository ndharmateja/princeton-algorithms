package main.chapter2.p_2_4_33;

import java.util.*;

/**
 * Comprehensive, safe, and self-reporting test suite for IndexMinPQ.
 *
 * ✅ Features:
 * 1. No assertions — uses check() to log results.
 * 2. Never crashes — all test failures are logged.
 * 3. Prints summary of passed/failed tests with names.
 *
 * To run:
 * javac IndexMinPQ.java TestIndexMinPQ.java
 * java main.chapter2.p_2_4_33.TestIndexMinPQ
 */
public class TestIndexMinPQ {

    private static int passed = 0;
    private static int failed = 0;
    private static final List<String> failedTests = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("🔍 Starting IndexMinPQ Test Suite...\n");

        run("Basic Insert and Min Retrieval", TestIndexMinPQ::testBasicInsertAndMin);
        run("Change Key", TestIndexMinPQ::testChangeKey);
        run("Delete by Index", TestIndexMinPQ::testDelete);
        run("delMin and Order", TestIndexMinPQ::testDelMinAndOrder);
        run("contains() and size()", TestIndexMinPQ::testContainsAndSize);
        run("Edge Cases", TestIndexMinPQ::testEdgeCases);
        run("Stress Random", TestIndexMinPQ::testStressRandom);
        run("Invalid Operations", TestIndexMinPQ::testInvalidOperations);

        System.out.println("\n------------------------------------");
        System.out.println("✅ Tests Passed: " + passed);
        System.out.println("❌ Tests Failed: " + failed);
        if (!failedTests.isEmpty()) {
            System.out.println("⚠️  Failed Tests: " + failedTests);
        } else {
            System.out.println("🎉 All tests passed successfully!");
        }
        System.out.println("------------------------------------");
    }

    // -------------------------------
    // Utility methods
    // -------------------------------
    private static void run(String testName, Runnable testMethod) {
        System.out.println("[Running] " + testName + "...");
        try {
            testMethod.run();
            System.out.println("   ✅ Passed\n");
            passed++;
        } catch (Exception e) {
            System.out.println("   ❌ Failed: " + e.getMessage() + "\n");
            failed++;
            failedTests.add(testName);
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition)
            throw new RuntimeException(message);
    }

    // -------------------------------
    // 1. Basic insert and min retrieval
    // -------------------------------
    private static void testBasicInsertAndMin() {
        IndexMinPQ<Integer> pq = new IndexMinPQ<>(5);
        check(pq.isEmpty(), "PQ should be empty initially");

        pq.insert(0, 50);
        pq.insert(1, 30);
        pq.insert(2, 40);

        check(!pq.isEmpty(), "PQ should not be empty after inserts");
        check(pq.size() == 3, "Size should be 3");
        check(pq.min() == 30, "Expected min = 30");
        check(pq.minIndex() == 1, "Expected min index = 1");
    }

    // -------------------------------
    // 2. Change key (increase/decrease)
    // -------------------------------
    private static void testChangeKey() {
        IndexMinPQ<Integer> pq = new IndexMinPQ<>(5);
        pq.insert(0, 50);
        pq.insert(1, 30);
        pq.insert(2, 40);

        pq.change(0, 10);
        check(pq.min() == 10 && pq.minIndex() == 0, "Min should now be 10 at index 0");

        pq.change(0, 100);
        check(pq.min() == 30 && pq.minIndex() == 1, "Min should revert to 30 at index 1");

        boolean caught = false;
        try {
            pq.change(4, 20);
        } catch (NoSuchElementException e) {
            caught = true;
        }
        check(caught, "Expected NoSuchElementException for non-existent index");
    }

    // -------------------------------
    // 3. Delete by index
    // -------------------------------
    private static void testDelete() {
        IndexMinPQ<Integer> pq = new IndexMinPQ<>(5);
        pq.insert(0, 10);
        pq.insert(1, 20);
        pq.insert(2, 5);

        pq.delete(1);
        check(!pq.contains(1), "Index 1 should be deleted");
        check(pq.size() == 2, "Size should be 2 after deletion");

        boolean caught = false;
        try {
            pq.delete(1);
        } catch (NoSuchElementException e) {
            caught = true;
        }
        check(caught, "Expected NoSuchElementException for deleting non-existent index");
    }

    // -------------------------------
    // 4. delMin correctness
    // -------------------------------
    private static void testDelMinAndOrder() {
        IndexMinPQ<Integer> pq = new IndexMinPQ<>(6);
        pq.insert(0, 90);
        pq.insert(1, 10);
        pq.insert(2, 50);
        pq.insert(3, 20);
        pq.insert(4, 30);

        int idx = pq.delMin();
        check(idx == 1, "Expected delMin to remove index 1");
        check(pq.min() == 20, "Next min should be 20");

        int[] expectedOrder = { 3, 4, 2, 0 };
        for (int i : expectedOrder) {
            int del = pq.delMin();
            check(del == i, "Expected next index " + i + ", got " + del);
        }

        check(pq.isEmpty(), "PQ should be empty after removing all elements");
    }

    // -------------------------------
    // 5. contains() and size()
    // -------------------------------
    private static void testContainsAndSize() {
        IndexMinPQ<Integer> pq = new IndexMinPQ<>(3);
        pq.insert(0, 5);
        pq.insert(1, 10);
        check(pq.contains(1), "PQ should contain index 1");
        pq.delete(0);
        check(!pq.contains(0), "Index 0 should not exist after deletion");
        check(pq.size() == 1, "Size should be 1 after deleting one item");
    }

    // -------------------------------
    // 6. Invalid operations
    // -------------------------------
    private static void testInvalidOperations() {
        IndexMinPQ<Integer> pq = new IndexMinPQ<>(3);

        boolean caught;

        // Invalid index insert
        caught = false;
        try {
            pq.insert(3, 100);
        } catch (IllegalArgumentException e) {
            caught = true;
        }
        check(caught, "Expected IllegalArgumentException for invalid index");

        // Duplicate insert
        pq.insert(0, 10);
        caught = false;
        try {
            pq.insert(0, 20);
        } catch (IllegalArgumentException e) {
            caught = true;
        }
        check(caught, "Expected IllegalArgumentException for duplicate index");

        // Null key
        caught = false;
        try {
            pq.insert(1, null);
        } catch (NullPointerException e) {
            caught = true;
        }
        check(caught, "Expected NullPointerException for null key");

        // Empty PQ operations
        IndexMinPQ<Integer> empty = new IndexMinPQ<>(2);
        caught = false;
        try {
            empty.min();
        } catch (NoSuchElementException e) {
            caught = true;
        }
        check(caught, "Expected NoSuchElementException on min() for empty PQ");

        caught = false;
        try {
            empty.delMin();
        } catch (NoSuchElementException e) {
            caught = true;
        }
        check(caught, "Expected NoSuchElementException on delMin() for empty PQ");
    }

    // -------------------------------
    // 7. Edge cases
    // -------------------------------
    private static void testEdgeCases() {
        IndexMinPQ<Integer> pq = new IndexMinPQ<>(2);
        pq.insert(0, 5);
        pq.insert(1, 3);
        pq.delMin();
        pq.delMin();
        check(pq.isEmpty(), "PQ should be empty after deleting all elements");

        boolean caught = false;
        try {
            pq.delMin();
        } catch (NoSuchElementException e) {
            caught = true;
        }
        check(caught, "Expected NoSuchElementException when delMin() on empty PQ");
    }

    // -------------------------------
    // 8. Randomized stress test
    // -------------------------------
    private static void testStressRandom() {
        Random rand = new Random(42);
        int N = 1000;
        IndexMinPQ<Integer> pq = new IndexMinPQ<>(N);

        for (int i = 0; i < N; i++) {
            pq.insert(i, rand.nextInt(10000));
        }
        check(pq.size() == N, "PQ should contain N elements");

        while (!pq.isEmpty()) {
            pq.delMin();
        }

        check(pq.size() == 0, "PQ should be empty after full deletion");
    }
}
