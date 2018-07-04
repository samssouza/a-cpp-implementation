package chinese.postman;

import java.util.ArrayList;
import java.util.Arrays;

public class Solver {

    public void Solve(Graph g) {
        //Find odd vertices
        ArrayList<Integer> oddVertices = FindOddVertices(g);
        System.out.println("Odd Vertices: " + Arrays.toString(oddVertices.toArray()));
        //Run FloydWarshall
        Object[] results = FloydWarshall(g);
        int[][] distance = (int[][]) results[0];
        int[][] next = (int[][]) results[1];
        System.out.println("Distance Matrix: " + Arrays.deepToString(distance));
        System.out.println("Next Matrix: " + Arrays.deepToString(next));
        //List all possible pairings
        listAllPairings();
        //Find Perfect Matching with lowest value
        //Add edges to Graph
        //Run Hierholzer
    }

    public ArrayList<Integer> FindOddVertices(Graph g) {

        int n = g.getVertices().length;
        int[][] costMatrix = g.getCostMatrix();
        ArrayList<Integer> oddVertices = new ArrayList<>();

        //Finds odd vertices
        for (int i = 0; i < n; i++) {
            int neighborsCount = 0;
            for (int j = 0; j < n; j++) {
                if ((costMatrix[i][j] != Integer.MAX_VALUE)
                        && (costMatrix[i][j] != 0)) {
                    neighborsCount += 1;

                }
            }
            if (neighborsCount % 2 != 0) {
                oddVertices.add(i);
            }
        }
        return oddVertices;

    }

    public Object[] FloydWarshall(Graph g) {
        int n = g.getCostMatrix().length;
        int[][] distance = new int[n][n];
        //Maybe do path calculation separately
        int[][] next = new int[n][n];

        //Initialization 
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                distance[i][j] = g.getCostMatrix()[i][j];
                if (g.getCostMatrix()[i][j] != 0
                        && g.getCostMatrix()[i][j] != Integer.MAX_VALUE) {
                    next[i][j] = j;
                }
            }
        }

        //Algorithm Body 
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (distance[i][j] > distance[i][k] + distance[k][j]) {
                        distance[i][j] = distance[i][k] + distance[k][j];
                        next[i][j] = next[i][k];

                    }
                }
            }

        }
        return new Object[]{distance, next};
    }
    
    private void listAllPairings() {
        
    }
    public void Hierholzer() {
        //Find eulerian circle
    }

   

}
