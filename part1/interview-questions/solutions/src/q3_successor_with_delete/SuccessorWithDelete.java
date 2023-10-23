package q3_successor_with_delete;

import q2_union_find_with_specific_canonical_element.UnionFindCanonical;

public class SuccessorWithDelete {
    private UnionFindCanonical ufc;
    private boolean[] isRemoved;

    public SuccessorWithDelete(int n) {
        ufc = new UnionFindCanonical(n);
        isRemoved = new boolean[n];

        // Mark all elements as not removed initially
        for (int i = 0; i < isRemoved.length; i++) {
            isRemoved[i] = false;
        }
    }

    /**
     * Variant: we have sets of deleted elements
     * We maintain those sets using union find canonical
     * for which each set has its root as the biggest value in that set
     * 
     * @param x
     * @return the value of the next unremoved element
     * @return n, if no such element exists
     */
    public int getSuccessorAfterDelete(int x) {
        // If not already removed
        // we mark as removed
        // and union it with left and right elements if they are
        // already removed
        if (!isRemoved[x]) {
            isRemoved[x] = true;
            if (x - 1 >= 0 && isRemoved[x - 1])
                ufc.union(x - 1, x);
            if (x + 1 < isRemoved.length && isRemoved[x + 1])
                ufc.union(x, x + 1);
        }

        // We get the largest element in that
        // set using the uf data structure and return the number after that
        return ufc.find(x) + 1;
    }

    public static void main(String[] args) {
        SuccessorWithDelete s = new SuccessorWithDelete(5);
        System.out.println(s.getSuccessorAfterDelete(0));
        System.out.println(s.getSuccessorAfterDelete(2));
        System.out.println(s.getSuccessorAfterDelete(4));
        System.out.println(s.getSuccessorAfterDelete(1));
        System.out.println(s.getSuccessorAfterDelete(3));
        System.out.println(s.getSuccessorAfterDelete(0));
    }
}
