# Interview Questions

### 1. Social network connectivity

Given a social network containing $n$ members and a log file containing $m$ timestamps at which times pairs of members formed friendships, design an algorithm to determine the earliest time at which all members are connected (i.e., every member is a friend of a friend of a friend ... of a friend). Assume that the log file is sorted by timestamp and that friendship is an equivalence relation. The running time of your algorithm should be $m$ $log$ $n$ or better and use extra space proportional to $n$.

- <details>
    <summary>Hint</summary>
    Use union-find.
  </details>

- <details>
    <summary>Solution</summary>
    <ol>
      <li>Use union find and keep doing union until all elements get merged into one set and that is the resulting timestamp</li>
    </ol>
  </details>

### 2. Union-find with specific canonical element.

For example, if one of the connected components is $`\{1, 2, 6, 9\}`$, then the $find()$ method should return $9$ for each of the four elements in the connected components.

- <details>
    <summary>Hint</summary>
    Maintain an extra array to the weighted quick-union data structure that stores for each root i the large element in the connected component containing i.
  </details>

- <details>
    <summary>Solution</summary>
    <ol>
      <li>We do Weighted Union Find with Path Compression but we also store the roots of all trees also maintain the biggest number in an array along with the size array and the parent array.</li>
      <li>They won't be affected by path compression as the roots will still have their biggest number unchanged and once an element becomes a child in a tree, it will never become a root again. So it is okay if the biggest number for a child gets disturbed during path compression (just like for the size array).</li>
      <li>When doing the union, get the biggest number in both trees (from the biggest array for both the roots) and the biggest value for the new root will be updated to that value.</li>
    </ol>
  </details>

### 3. Successor with delete.

Given a set of n integers $S=$ $`\{0,1,...,n−1\}`$ and a sequence of requests of the following form:

- Remove $x$ from $S$.
- Find the successor of $x$: the smallest $y$ in $S$ such that $y≥x$.

Design a data type so that all operations (except construction) take logarithmic time or better in the worst case.

- <details>
    <summary>Hint</summary>
    Use the modification of the union−find data discussed in the previous question.
  </details>

- <details>
    <summary>Solution</summary>
    <ol>
      <li>We use the above Union Find Canonical data structure</li>
      <li>All consecutive removed elements go in a single set</li>
      <li>So we can easily find the next unremoved element (which is 1 greater than the root of that set which is the largest number in that set)</li>
      <li>So when we want to remove an element, we union it with its left and right elements if they are also removed</li>
    </ol>
  </details>
