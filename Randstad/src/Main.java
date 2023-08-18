import java.util.*;
import java.util.stream.*;

public class Main {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        //It reads the first line of input and splits it into two integers X and Y.
        String[] firstLine = sc.nextLine().split(" ");
        int x = Integer.parseInt(firstLine[0]); //3 <= X <= 10^7 (10,000,000)
        int y = Integer.parseInt(firstLine[1]); //1 <= Y <= 2*10^5 (200,000)

        int listResult = listSolution(x, y);
        int segmentTreeSolution = segmentTreeSolution(x, y);

        System.out.println("List Result: " + listResult);
        System.out.println("Segment Tree Result: " + segmentTreeSolution);

    }

    public static int listSolution(int listSize, int iterations){
        //It initializes a list of size X with all 0’s.
        List<Integer> arr = new ArrayList<>(Collections.nCopies(listSize, 0));

        //It reads Y lines of input and splits each line into three integers i, j and k
        IntStream.range(0, iterations).forEach(element -> {
            String[] line = sc.nextLine().split(" ");
            int i = Integer.parseInt(line[0]); //1 <= i <= X (10,000,000)
            int j = Integer.parseInt(line[1]); //1 <= j <= X (10,000,000)
            int k = Integer.parseInt(line[2]);// 0 <= k <= 10^9 (1,000,000,000)

            //For each line of input, it adds k to all the elements in the range of index i to j (both inclusive).
            IntStream.range(i - 1, j).forEach(num -> arr.set(num, arr.get(num) + k));
        });
       return  Collections.max(arr);
    }
    public static int segmentTreeSolution(int listSize, int iterations){

        //A constructor for the SegmentTree class that initializes the tree and lazy arrays to have length 4*n.
        //Also initializes all elements to 0.
        SegmentTree tree = new SegmentTree(listSize);
        //It reads Y lines of input and splits each line into three integers i, j and k
        for (int iteration = 0; iteration < iterations; iteration++) {
            String[] line = sc.nextLine().split(" ");
            int i = Integer.parseInt(line[0]);
            int j = Integer.parseInt(line[1]);
            int k = Integer.parseInt(line[2]);
            // Then update the segment tree with each line using the public update method.
            // Update the segment tree with a range addition query.
            // If the current node’s range is completely outside the query range, return without updating anything.
            // If the current node’s range is completely inside the query range,  update its value and propagate it lazy down to its children.
            // Otherwise, recursively update its left and right children and then update its own value in the tree.
            tree.update(i,j,k);
        }
        return tree.queryMax();
    }

    //Segment tree implementation taken from SO and modified to whatever I needed
    static class SegmentTree {
        int[] tree;
        int[] lazy;
        int n;

        public SegmentTree(int n) {
            this.n = n;
            tree = new int[4 * n];
            lazy = new int[4 * n];
        }

        private void push(int node, int left, int right) {
            if (lazy[node] != 0) {
                tree[node] += lazy[node];
                if (left != right) {
                    lazy[2 * node + 1] += lazy[node];
                    lazy[2 * node + 2] += lazy[node];
                }
                lazy[node] = 0;
            }
        }

        private void update(int node, int left, int right, int i, int j, int k) {
            push(node, left, right);
            if (left > j || right < i) {
                return;
            }
            if (left >= i && right <= j) {
                tree[node] += k;
                if (left != right) {
                    lazy[2 * node + 1] += k;
                    lazy[2 * node + 2] += k;
                }
                return;
            }
            int mid = (left + right) / 2;
            update(2 * node + 1, left, mid, i, j, k);
            update(2 * node + 2, mid + 1, right, i, j, k);
            tree[node] = Math.max(tree[2 * node + 1], tree[2 * node + 2]);
        }

        private int queryMax(int node, int left, int right, int i, int j) {
            push(node, left, right);
            if (left > j || right < i) {
                return Integer.MIN_VALUE;
            }
            if (left >= i && right <= j) {
                return tree[node];
            }
            int mid = (left + right) / 2;
            return Math.max(queryMax(2 * node + 1, left, mid, i, j), queryMax(2 * node + 2, mid + 1, right, i, j));
        }

        public void update(int i, int j, int k) {
            update(0, 0, n - 1, i - 1, j - 1, k);
        }

        public int queryMax() {
            return queryMax(0, 0, n - 1, 0, n - 1);
        }
    }
}


/*
5 3
1 2 100
2 5 100
3 4 100
1 2 100
2 5 100
3 4 100
Time vs space complexity:
                          The segment tree solution has a time complexity of O(Ylog(X)),
                          where Y is the number of operations and X is the size of the list.
                          This is because each update operation takes O(log(X)) time to execute.
                          However, the space complexity is O(X) + O(log(X)), where O(log(X)) is the space required to store the segment tree.

                          The list solution has a time complexity of O(YX), but the space complexity is only O(X) + O(Y),
                          where O(Y) is the space required to store the stream of operations.

Ease of use vs efficiency: Another tradeoff is between ease of implementation and efficiency.

                           The list solution is easier to implement than the segment tree solution.
                           This is because it uses built-in Java data structures and streams.

                           However, the segment tree solution may be more efficient than the list solution for large values of X.
                           because it uses a more efficient data structure for range updates and queries.

Readability vs conciseness:
                            The list solution is concise and uses streams to achieve a functional style of programming.
                            However, it may be less readable than an imperative solution that uses for loops and if statements.

                            The segment tree solution is more verbose than the list solution,
                            making it less readable to people that don't know or haven't used the data structure.
* */
