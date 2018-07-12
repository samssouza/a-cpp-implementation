package chinese.postman;

import java.util.Arrays;
import java.util.LinkedList;

public class Solver {

    public static int INFINITE = Integer.MAX_VALUE / 2;

    public void Solve(int[][] costMatrix, int startVertice) {

        //Find odd vertices
        LinkedList<Integer> oddVertices = FindOddVertices(costMatrix);
        System.out.println("Odd Vertices: " + Arrays.toString(oddVertices.toArray()));
        //Run FloydWarshall
        Object[] results = FloydWarshall(costMatrix);
        int[][] distance = (int[][]) results[0];
        int[][] next = (int[][]) results[1];
        LinkedList<LinkedList<Integer>> matchings = new LinkedList<>();
        //Find all matchings of graph with oddVertices
        findAllMatchings(matchings, oddVertices, new LinkedList<>());
        //Finds match with minimum summed weight
        LinkedList<Integer> bestMatch = findPerfectMatch(matchings, distance);
        int[][][] multiGraph = addEdgesGraph(bestMatch, distance, costMatrix);
        LinkedList<Integer> circuit = Hierholzer(multiGraph, startVertice, next);
        System.out.println("Circuit: " + Arrays.toString(circuit.toArray()));
        int totatCost = getTotalCost(circuit, costMatrix);
        System.out.println("Total Cost: " + totatCost);
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

        if (vertices.isEmpty()) {
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
            for (int i = 0; i < match.size() - 1; i += 2) {
                cost += distance[match.get(i)][match.get(i + 1)];
            }

            if (cost < bestCost) {
                bestCost = cost;
                bestMatching = match;
            }

        }
        return bestMatching;

    }

    private int[][][] addEdgesGraph(LinkedList<Integer> bestMatch,
            int[][] distance, int[][] costMatrix) {

        int n = costMatrix.length;
        int[][][] multiGraph = new int[n][n][2]; // Maximum 2 edges by pair (i,j)

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                multiGraph[i][j][0] = costMatrix[i][j];
                multiGraph[i][j][1] = (i == j) ? 0 : INFINITE;

            }
        }

        for (int i = 0; i < bestMatch.size(); i += 2) {
            multiGraph[bestMatch.get(i)][bestMatch.get(i + 1)][1]
                    = distance[bestMatch.get(i)][bestMatch.get(i + 1)];

            multiGraph[bestMatch.get(i + 1)][bestMatch.get(i)][1]
                    = distance[bestMatch.get(i)][bestMatch.get(i + 1)];

        }

        return multiGraph;
    }

    public LinkedList<Integer> Hierholzer(int[][][] costMatrix,
            Integer start, int[][] nextMatrix) {

        int n = costMatrix.length;;

        int[][][] visitedEdges = new int[n][n][2];
        LinkedList<Integer> circuit = new LinkedList();
        circuit.add(start);

        while (true) {
            Integer unvisited = -1;
            int index = 0;

            for (Integer vertice : circuit) {
                Integer[] nextEdge = findUnvisitedEdge(vertice, visitedEdges, costMatrix);
                if (nextEdge != null) {
                    unvisited = vertice;
                    break;
                }
                index += 1;
            }

            if (unvisited == -1) {
                break;
            }

            LinkedList<Integer> newCircle = new LinkedList<>();
            newCircle.add(unvisited);

            while (true) {

                Integer[] nextEdge = findUnvisitedEdge(unvisited, visitedEdges, costMatrix);

                if (nextEdge[2] == 0) {
                    newCircle.add(nextEdge[1]);

                } else if (nextEdge[2] == 1) {
                    newCircle.addAll(getPath(nextEdge[0], nextEdge[1], nextMatrix));

                }
             
                visitedEdges[nextEdge[0]][nextEdge[1]][nextEdge[2]] = 1;
                visitedEdges[nextEdge[1]][nextEdge[0]][nextEdge[2]] = 1;
                unvisited = nextEdge[1];

                if (newCircle.getFirst() == newCircle.getLast()) {
                    break;
                }
            }
            circuit.remove(index);
            circuit.addAll(index, newCircle);

        }
        return circuit;

    }

    //Finds first outgoing unvisited edge
    private Integer[] findUnvisitedEdge(Integer vertice,
            int[][][] visitedEdges, int[][][] costMatrix) {

        for (int j = 0; j < costMatrix.length; j++) {
            for (int k = 0; k < 2; k++) {
                int cost = costMatrix[vertice][j][k];
                if ((cost != INFINITE) && (cost != 0)
                        && (visitedEdges[vertice][j][k] != 1)) {

                    return new Integer[]{vertice, j, k};
                }
            }
        }
        return null;
    }

    //Floyd Warshall path reconstruction
    private LinkedList<Integer> getPath(Integer i, Integer j, int[][] next) {
        LinkedList<Integer> path = new LinkedList<>();
        while (i != j) {
            i = next[i][j];
            path.add(i);
        }
        return path;
    }

    private int getTotalCost(LinkedList<Integer> circuit, int[][] costMatrix) {
        int totalCost = 0;
        for (int i = 0; i < circuit.size() - 1; i++) {
            totalCost += costMatrix[circuit.get(i)][circuit.get(i + 1)];
        }
        return totalCost;
    }
}
