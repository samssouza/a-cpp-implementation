package chinese.postman;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import javafx.util.Pair;

public class Graph {
    private ArrayList<String> vertices;
    private Map<Pair<String, String>, String> costMatrix;
    
    public void ReadGraph(){
        Scanner input = new Scanner(System.in);
        System.out.println("Type Graph Edge... ");
        System.out.println("Edges Format:(verticce1,vertice2, weight) ");
        String edge = "";
        
        while((edge = input.next("[a-zA-Z]*,*,*)")) != null){
            System.err.println(edge);
        }
    }
    
}
