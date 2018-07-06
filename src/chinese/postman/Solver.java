package chinese.postman;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import javafx.util.Pair;
import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;

public class Solver {

    public void Solve(Graph g) {

        //Find odd vertices
        HashSet<Integer> oddVertices = FindOddVertices(g);
        System.out.println("Odd Vertices: " + Arrays.toString(oddVertices.toArray()));
        //Run FloydWarshall
        Object[] results = FloydWarshall(g);
        int[][] distance = (int[][]) results[0];
        int[][] next = (int[][]) results[1];
        System.out.println("Distance Matrix: " + Arrays.deepToString(distance));
        System.out.println("Next Matrix: " + Arrays.deepToString(next));
        //Find edges of complete graph with oddVertices
        ArrayList<Edge> edges = getAllEdges(oddVertices);
        //Find all matchings with edges
        LinkedList<Edge[]> matchings = new LinkedList<>();
        addMatchings(matchings, edges, new Edge[oddVertices.size()/ 2], oddVertices.size()/ 2);
        System.out.println("All Odd Vertices Matchs: " + Arrays.deepToString(matchings.toArray()));
        //Finds match with minimum summed weight
        Edge[] bestMatch = findPerfectMatch(matchings, distance);
        System.out.println("Best Odd Vertices match: "+ Arrays.toString(bestMatch));
        //Add of best match to graph
        //Run Hierholzer and genereta cicle

    }

    public HashSet<Integer> FindOddVertices(Graph g) {

        int n = g.getVertices().length;
        int[][] costMatrix = g.getCostMatrix();
        HashSet<Integer> oddVertices = new HashSet<>();

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

    private Edge[] findPerfectMatch(LinkedList<Edge[]> matchings, int [][] distance) {
        
        Edge[] bestMatching = null;
        int bestCost = Integer.MAX_VALUE;
            
        for (Edge[] match : matchings) {
            int cost =0;
            for (Edge edge : match) {
                cost += distance[edge.init][edge.end];
            }
            if(cost < bestCost){
                bestMatching = match;
            }
            
        }
        return bestMatching;

    }
    
    ////Adds to matchings all matching  begin with  edgeSequence edges
    //plus all matchings that can be generated with edges
    private void addMatchings(LinkedList<Edge[]> matchings,
            ArrayList<Edge> edges,
            Edge[] edgesSequence,
            int n) {

        //Check if a sequence of edges(ex:(1,2)(0,3)) is already added as a match
        //This also discarts isomorph matches ex: (1,2)(0,3) amd (0,3)(1,2)
        if (edges.isEmpty()) {
            List edgesSequenceCol = Arrays.asList(edgesSequence);
       
            for (Edge[] match : matchings) {
                
                if (Arrays.asList(match).containsAll(edgesSequenceCol)) {
                    return;
                }
            }
            matchings.add(Arrays.copyOf(edgesSequence, edgesSequence.length));
        }

        for (Edge edge1 : edges) {

            ArrayList<Edge> newEdges = new ArrayList<>();
            for (Edge edge2 : edges) {
                if (edge2.toltallyDiferent(edge1)) {
                    newEdges.add(edge2);
                }
            }
            
            edgesSequence[edgesSequence.length - n] = edge1;
            addMatchings(matchings, newEdges, edgesSequence, n - 1);
            
        }
    }
    
    private  ArrayList<Edge> getAllEdges(HashSet<Integer> oddVertices) {
       
        HashSet<Edge> edgesSet = new HashSet<>();

        //Enumerates all graph edges
        for (Integer v1 : oddVertices) {
            for (Integer v2 : oddVertices) {

                if (v1 == v2) {
                    continue;
                }

                if (!edgesSet.contains(new Edge(v1, v2))
                        && !edgesSet.contains(new Edge(v2, v1))) {

                    edgesSet.add(new Edge(v1, v2));

                }
            }

        }

        return new ArrayList<>(edgesSet);

    }
    public void Hierholzer() {
        //Find eulerian circle
    }

    //This class represents undirected edges
    private class Edge {

        public int init;
        public int end;

        public Edge(int init, int end) {
            this.init = init;
            this.end = end;
        }

        @Override
        public boolean equals(Object obj) {
            if (!Edge.class.isInstance(obj)) {
                return false;
            }

            Edge edg = (Edge) obj;
            return (edg.init == this.init && edg.end == this.end)
                    || (edg.init == this.end && edg.end == this.init);

        }

        public boolean toltallyDiferent(Edge edge) {
            //Returns if edges have no vertex in common
            return ((this.init != edge.init)
                    && (this.end != edge.end)
                    && (this.init != edge.end)
                    && (this.end != edge.init));
        }

        @Override
        public int hashCode() {
            int[] parameters = new int[]{init, end};
            Arrays.sort(parameters);
            return Objects.hash(parameters[0], parameters[1]);
        }

        @Override
        public String toString() {
            return "(" + Integer.toString(init)
                    + "," + Integer.toString(end) + ")";
        }

    }
}
