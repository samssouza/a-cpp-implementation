package chinese.postman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import javafx.util.Pair;

public class Graph {

    private String[] vertices;
    private int[][] costMatrix;

    public void ReadGraph() {
        System.out.println("Type Edges of Graph Separated by SemiColon.");
        System.out.println("Edges Format: (vertice1,vertice2, weight)");
        System.out.println("Ex: (a,b,18);(a,c,5);(a,d,5);\n");

        String inputString = new Scanner(System.in).nextLine();
        boolean isValid = false;
        isValid = inputString.matches("(\\(\\s*([a-zA-Z]+|[0-9]+)\\s*,"
                + "\\s*([a-zA-Z]+|[0-9]+)\\s*,\\s*[0-9]+\\s*\\)\\s*;)+");

        while (!isValid) {
            System.err.println("Edges Do Not Match pattern. Try again...");
            inputString = new Scanner(System.in).nextLine();
            isValid = inputString.matches("(\\(\\s*([a-zA-Z]+|[0-9]+)\\s*,"
                    + "\\s*([a-zA-Z]+|[0-9]+)\\s*,\\s*[0-9]+\\s*\\)\\s*;)+");

        }

        String[] edgesArray = inputString.split(";");

        HashSet<String> vSet = new HashSet();
        Map<Pair<String, String>, Integer> costsMap = new HashMap<>();

        for (String edge : edgesArray) {
            edge = edge.replaceAll("(\\(|\\))", "");
            
            String v1 = edge.split(",")[0];
            String v2 = edge.split(",")[1];
            int weight = Integer.valueOf(edge.split(",")[2].trim());
                    
            vSet.add(v1);
            vSet.add(v2);
            costsMap.put(new Pair<String, String>(v1,v2), weight); 
            costsMap.put(new Pair<String, String>(v2,v1), weight); 

        }

        String[] gVertices = vSet.toArray(new String[vSet.size()]);
        int[][] gCosts = new int[vSet.size()][vSet.size()];

        for (int i = 0; i < gVertices.length; i++) {
            for (int j = 0; j < gVertices.length; j++) {
                int cost;
                cost = costsMap.getOrDefault(new Pair<String, String>(
                        gVertices[i], gVertices[j]), Integer.MAX_VALUE);

                gCosts[i][j] = cost;
               
            }
        }

        System.out.println(Arrays.toString(gVertices));
        System.out.println(Arrays.deepToString(gCosts));

        this.vertices = gVertices;
        this.costMatrix = gCosts;
    
    }
    
}
