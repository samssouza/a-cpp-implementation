package chinese.postman;


public class MainClass {

    public static void main(String[] args) {
     
        Graph graph = new Graph();
        graph.ReadGraph();
        new Solver().Solve(graph);
    }
    
}
