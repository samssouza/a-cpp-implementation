package chinese.postman;

import java.util.HashMap;
import java.util.Map;
import javafx.util.Pair;

public class MainClass {

    public static void main(String[] args) {
     
        Graph graph = new Graph();
        graph.ReadGraph();
        
        new Solver().Solve(graph);
    }
    
}
