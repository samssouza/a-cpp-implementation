package chinese.postman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import javafx.util.Pair;

public class Graph {

    private int[][] costMatrix;
    private int order;

    public int getOrder() {
        return order;
    }

    public int[][] getCostMatrix() {
        return costMatrix;
    }

    public void ReadGraph() {
        System.out.println("Type Cost Matrix ");
        System.out.println("LEAVE lINE EMPTY AT ANY TIME TO STOP!!! ");

        Scanner scanner = new Scanner(System.in);
        ArrayList<String> lines = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String reade = scanner.nextLine();
            if (reade.matches("\\s*")) {
                break;
            }
            lines.add(reade);

        }

        int n = lines.size();
        int[][] gCost = new int[n][n];

        for (int i = 0; i < n; i++) {
            String[] costs = lines.get(i).split("\\s");
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    gCost[i][j] = 0;
                } else if (costs[j].equals("-")) {
                    gCost[i][j] = Solver.INFINITE;
                } else {
                    gCost[i][j] = Integer.parseInt(costs[j]);
                }
            }
        }

        System.out.println("Number of vertices: " + String.valueOf(n));
        System.out.println("Cost Matrix:" + Arrays.deepToString(gCost));

        this.costMatrix = gCost;
        this.order = n;

    }

}
