package q15_shuffle_linked_list;

import edu.princeton.cs.algs4.StdRandom;

public class ShuffleLinkedList {
    private static <T> int size(Node<T> head) {
        int size = 0;
        Node<T> curr = head;
        while (curr != null) {
            size++;
            curr = curr.next;
        }

        return size;
    }

    private static <T> Node<T> shuffleMerge(Node<T> head1, Node<T> head2) {
        assert head1 != null;
        assert head2 != null;

        Node<T> curr1 = head1;
        Node<T> curr2 = head2;
        int numRemainingElements1 = size(head1);
        int numRemainingElements2 = size(head2);

        Node<T> dummyHead = new Node<>();
        Node<T> curr = dummyHead;
        while (curr1 != null && curr2 != null) {
            double randomValue = StdRandom.uniformDouble();
            double prob = (numRemainingElements1 * 1.0) / (numRemainingElements1 + numRemainingElements2);
            if (randomValue < prob) {
                curr.next = curr1;
                curr1 = curr1.next;
                numRemainingElements1--;
            } else {
                curr.next = curr2;
                curr2 = curr2.next;
                numRemainingElements2--;
            }
            curr = curr.next;
        }
        while (curr1 != null) {
            curr.next = curr1;
            curr1 = curr1.next;
            curr = curr.next;
        }
        while (curr2 != null) {
            curr.next = curr2;
            curr2 = curr2.next;
            curr = curr.next;
        }

        curr.next = null;
        return dummyHead.next;
    }

    private static <T> Node<T> breakListIntoTwoParts(Node<T> head) {
        Node<T> prev = null;
        Node<T> slow = head;
        Node<T> fast = head;
        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        prev.next = null;

        return slow;
    }

    public static <T> Node<T> shuffle(Node<T> head) {
        if (head == null || head.next == null)
            return head;

        Node<T> rightHead = breakListIntoTwoParts(head);
        Node<T> leftShuffleHead = shuffle(head);
        Node<T> rightShuffleHead = shuffle(rightHead);

        head = shuffleMerge(leftShuffleHead, rightShuffleHead);
        return head;
    }

    // public static void main(String[] args) {
    // Node<Integer> head = TestShuffleLinkedList.buildList(8);
    // head = shuffle(head);
    // System.out.println(TestShuffleLinkedList.toString(head));
    // }
}
