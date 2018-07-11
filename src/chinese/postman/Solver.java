package chinese.postman;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import org.omg.PortableInterceptor.INACTIVE;

public class Solver {

    public static int INFINITE = Integer.MAX_VALUE / 2;

    public void Solve(int[][] costMatrix) {

        //Find odd vertices
        LinkedList<Integer> oddVertices = FindOddVertices(costMatrix);
        System.out.println("Odd Vertices: " + Arrays.toString(oddVertices.toArray()));
        //Run FloydWarshall
        Object[] results = FloydWarshall(costMatrix);
        int[][] distance = (int[][]) results[0];
        int[][] next = (int[][]) results[1];
        System.out.println("Distance Matrix: " + Arrays.deepToString(distance));
        System.out.println("Next Matrix: " + Arrays.deepToString(next));
        //Find edges of complete graph with oddVertices
        //ArrayList<Edge> edges = getAllEdges(oddVertices);
        //Find all matchings with edges
        LinkedList<LinkedList<Integer>> matchings = new LinkedList<>();
        findAllMatchings(matchings, oddVertices, new LinkedList<>());
        //Finds match with minimum summed weight
        for (LinkedList<Integer> matching : matchings) {
            System.out.print("Match: ");
        
            for (Integer integer : matching) {
                System.out.print(integer);
        
            }
            System.out.println("");
        
        }
        LinkedList<Integer> bestMatch = findPerfectMatch(matchings, distance);
        System.out.println("Best Odd Vertices match: " + Arrays.toString(bestMatch.toArray()));
        System.out.println("Match Cost: " + getCost(bestMatch, distance));
        
        Hierholzer(costMatrix, getCost(bestMatch, distance));
    }

    public LinkedList<Integer> FindOddVertices(int[][] costMatrix) {

        int n = costMatrix.length;
        LinkedList<Integer> oddVertices = new LinkedList<>();

        //Finds odd vertices
        for (int i = 0; i < n; i++) {
            int neighborsCount = 0;
            for (int j = 0; j < n; j++) {
                if ((costMatrix[i][j] != INFINITE)
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

    public Object[] FloydWarshall(int[][] costMatrix) {

        int n = costMatrix.length;
        int[][] distance = new int[n][n];
        //Maybe do path calculation separately
        int[][] next = new int[n][n];

        //Initialization 
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                distance[i][j] = costMatrix[i][j];
                if (costMatrix[i][j] != 0 && costMatrix[i][j] != INFINITE) {
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

    private void findAllMatchings(LinkedList<LinkedList<Integer>> matchings,
            LinkedList<Integer> vertices,
            LinkedList<Integer> visited) {

        if(vertices.isEmpty()){
            matchings.add(new LinkedList<>(visited));
            return;
        }
        if (vertices.size() % 2 == 0) {
            Integer visitVertice = vertices.getFirst();
            LinkedList<Integer> remainingVertices = new LinkedList<>(vertices);
            remainingVertices.remove(visitVertice);
            visited.add(visitVertice);
            findAllMatchings(matchings, remainingVertices, visited);
            visited.removeLast();
            
        } else if (vertices.size() % 2 != 0) {

            for (Integer visitVertice : vertices) {
                LinkedList<Integer> remainingVertices = new LinkedList<>(vertices);
                remainingVertices.remove(visitVertice);
                visited.add(visitVertice);
                findAllMatchings(matchings, remainingVertices, visited);
                visited.removeLast();

            }
        }

    }

    private LinkedList<Integer> findPerfectMatch(LinkedList<LinkedList<Integer>> matchings, int[][] distance) {

        LinkedList<Integer> bestMatching = null;
        int bestCost = Integer.MAX_VALUE;

        for (LinkedList<Integer> match : matchings) {
            int cost = 0;
            for (int i = 0; i < match.size() - 1; i+=2) {
                cost += distance[match.get(i)][match.get(i+1)];
            }
            
            if (cost < bestCost) {
                bestCost = cost;
                bestMatching = match;
            }

        }
        return bestMatching;

    }

    private int getCost(LinkedList<Integer> bestMatch, int[][] distance) {
        int cost = 0;
        for (int i = 0; i < bestMatch.size() - 1; i += 2) {
            cost += distance[bestMatch.get(i)][bestMatch.get(i + 1)];
        }
        return cost;
    }

    public void Hierholzer(int[][] costMatrix, int aditionalCost) {
        int n = costMatrix.length;;
        int totalCost = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (INFINITE != costMatrix[i][j]) {
                    totalCost += costMatrix[i][j];
                }
            }
        }
        System.out.println("Total Cost is: " + (totalCost / 2 + aditionalCost));
    }

}
