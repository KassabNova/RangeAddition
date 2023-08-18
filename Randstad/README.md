# Time vs space complexity:
       The segment tree solution has a time complexity of O(Ylog(X)),
       where Y is the number of operations and X is the size of the list.
       This is because each update operation takes O(log(X)) time to execute.
       However, the space complexity is O(X) + O(log(X)), 
       where O(log(X)) is the space required to store the segment tree.

       The list solution has a time complexity of O(YX), but the space complexity is only O(X) + O(Y),
       where O(Y) is the space required to store the stream of operations.

# Ease of use vs efficiency: 

       Another tradeoff is between ease of implementation and efficiency.
       
       The list solution is easier to implement than the segment tree solution.
       This is because it uses built-in Java data structures and streams.

       However, the segment tree solution may be more efficient than the list solution for large values of X.
       because it uses a more efficient data structure for range updates and queries.

# Readability vs conciseness:
       The list solution is concise and uses streams to achieve a functional style of programming.
       However, it may be less readable than an imperative solution that uses for loops and if statements.

       The segment tree solution is more verbose than the list solution,
       making it less readable to people that don't know or haven't used the data structure.
