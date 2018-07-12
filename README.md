# a-cpp-implementation
This project is an implementation of the Chinese postman problem written in Java. This implementation only works with undirected graph.

## Configuration

1. Download java JDK
2. Download Netbeans IDE
3. Clone or download project to your local machine.
4. Open the project in Netbeans and run.

During run, do the following:
1. The algorithm requests cost matrix of graph.
2. Insert cost matrix as the format shown below (Leave line empty to stop typing).
3. The algorithm requests starting vertex of the postman.
4. Enter the starting vertex.

## Matrix Format
The matrix inserted during execution must have the following format where each entry is the edge cost. The entry with (\-) means there is no edge bewten the pair of vertices

\- 50 50 50 \- \- \- \-  
50 \- \- 50 70 50 \- \-  
50 \- \- 70 \- \- 70 120  
50 50 70 \- \- 60 \- \-  
\- 70 \-  \-  \- 70  \- \-  
\- 50 \- 60 70 \- \- 60  
\- \- 70 \- \- \- \- 70  
\- \- 120 \- \- 60 70 -  
