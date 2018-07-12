package chinese.postman;

import java.util.ArrayList;
import java.util.Scanner;

public class MainClass {

    public static void main(String[] args) {
        int[][] costMatrix = ReadGraph();
        Integer startV = readStartVertice(costMatrix.length-1);
        System.out.println("Graph Order: " + String.valueOf(costMatrix.length));
        new Solver().Solve(costMatrix, startV);
       
    }

    public static int[][] ReadGraph() {
        System.out.println("Type Cost Matrix. LEAVE lINE EMPTY AT ANY TIME TO STOP!!! ");

        Scanner scanner = new Scanner(System.in);
        ArrayList<String> lines = new ArrayList<>();
        
        //Skip white characters before matrix
        scanner.skip("\\s*");
        String line = scanner.nextLine();
        int n = line.split("\\s+").length;
        int linesCount = 0;

        while (true) {
            if (line.matches("\\s*")) 
                break;
            
            if (!line.matches("(\\d+\\s+|\\-\\s+)+(\\d+\\s*|\\-\\s*)")
                    || line.split("\\s+").length > n
                    || linesCount > n) {

                System.err.println("Matrix Does Not Match Pattern or Is Irregular...");
                System.exit(0);
                break;
            }
            linesCount += 1;
            lines.add(line);
            line = scanner.nextLine();
        }
        
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

        
        return gCost;
    }
    public static int readStartVertice(int maxValue) {
        System.out.print("Type Start Vertice: ");
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        line = line.replaceAll("\\s*", "");
        
        if(!line.matches("[0-9]+") || Integer.valueOf(line) > maxValue){
            System.err.println("Start Vertice Invalid!");
            System.exit(0);
        
        }
            
        return  Integer.valueOf(line);
    }
}
