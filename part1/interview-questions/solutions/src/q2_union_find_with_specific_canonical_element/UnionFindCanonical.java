package q2_union_find_with_specific_canonical_element;

public class UnionFindCanonical {
    private int numSets;
    private int[] id;
    private int[] size;
    private int[] biggestNumber;

    public UnionFindCanonical(int n) {
        this.numSets = n;
        this.id = new int[n];
        this.size = new int[n];
        this.biggestNumber = new int[n];

        for (int i = 0; i < n; i++) {
            id[i] = i;
            biggestNumber[i] = i;
            size[i] = 1;
        }
    }

    // Uses path compression
    private int getRoot(int i) {
        if (i != id[i]) {
            id[i] = getRoot(id[i]);
        }
        return id[i];
    }

    public int find(int i) {
        int root = getRoot(i);
        return biggestNumber[root];
    }

    // Weighted union
    public void union(int i, int j) {
        int iRoot = getRoot(i);
        int jRoot = getRoot(j);

        if (iRoot == jRoot)
            return;

        numSets--;
        int biggestNumberInBothTrees = Integer.max(biggestNumber[iRoot], biggestNumber[jRoot]);
        if (size[iRoot] < size[jRoot]) {
            id[iRoot] = jRoot;
            size[jRoot] += size[iRoot];
            biggestNumber[jRoot] = biggestNumberInBothTrees;
        } else {
            id[jRoot] = iRoot;
            size[iRoot] += size[jRoot];
            biggestNumber[iRoot] = biggestNumberInBothTrees;
        }
    }

    public int count() {
        return numSets;
    }

    public static void main(String[] args) {
        UnionFindCanonical ufc = new UnionFindCanonical(5);

        System.out.println(ufc.count());

        ufc.union(0, 1);

        System.out.println(ufc.find(0));
        System.out.println(ufc.find(1));

        ufc.union(0, 2);
        ufc.union(0, 4);

        System.out.println(ufc.find(0));
        System.out.println(ufc.find(1));
        System.out.println(ufc.find(2));
        System.out.println(ufc.find(4));

        System.out.println(ufc.count());
    }

}
