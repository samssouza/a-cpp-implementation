package chinese.postman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import javafx.util.Pair;

public class MainClass {

    public static void main(String[] args) {
        int[][] costMatrix = ReadGraph();
        new Solver().Solve(costMatrix);
        
        
    }

    public static int[][] ReadGraph() {
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
            String[] costs = lines.get(i).split("\\s+");
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
        System.out.print("Cost Matrix:\n" + Arrays.deepToString(gCost).replace('[', ' ').replace(',', ' ').replace(']', '\n'));

        return gCost;

    }

}
