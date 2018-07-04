package chinese.postman;

import java.util.ArrayList;
import java.util.Arrays;

public class Solver {

    public void Solve(Graph g) {
        //Find odd vertices
        ArrayList<Integer> oddVertices = FindOddVertices(g);
        FloydWarshall();
        //Run FloydWarshall
        //List all possible pairings
        //Find Perfect Matching with lowest value
        //Add edges to Graph
        //Run Hierholzer
    }

    public ArrayList<Integer> FindOddVertices(Graph g) {

        ArrayList<Integer> oddVertices = new ArrayList<>();
       
        //Finds odd vertices
        for (int i = 0; i < g.getVertices().length; i++) {
            int neighborsCount = 0;
            for (int j = 0; j < g.getVertices().length; j++) {
                if (g.getCostMatrix()[i][j] != Integer.MAX_VALUE) {
                    neighborsCount += 1;
                }
            }
            if (neighborsCount % 2 != 0)
            oddVertices.add(i);
        }
        return oddVertices;
        
        
    }

    public void FloydWarshall() {
        //Find lowest distance between any pair of vertices
    }

    public void Hierholzer() {
        //Find eulerian circle
    }
}
