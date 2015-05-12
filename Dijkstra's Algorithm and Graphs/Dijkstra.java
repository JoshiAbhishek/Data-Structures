package dijkstra;

import java.util.*;

public class Dijkstra {

    private static Vertex[][] matrix = new Vertex[7][7];    //Matrix
    
    //Values to go into matrix
    private static int[][] costValues = {{0, 5, 3, 0, 0, 0, 0}, {0, 0, 2, 0, 3, 0, 1}, {0, 0, 0, 0, 7, 0, 0}, {2, 0, 0, 0, 0, 6, 0}, {0, 0, 0, 2, 0, 1, 0}, {0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 1, 0, 0}};
        
    public static void main(String[] args) {
        
        //Initializes Values
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = new Vertex();
                
                matrix[i][j].distance = 9999;
                matrix[i][j].known = false;
                matrix[i][j].previous = null;
                matrix[i][j].initialCost = costValues[i][j];
                matrix[i][j].setLetter(i);
            }
        }
        
        int total = matrix.length - 1;    //SUBTRACT 1????
        Vertex start = matrix[0][0];
        start.distance = start.initialCost;
        start.known = true;
        
        Vertex small = start;//Tracks Smallest Distance Vertex
        
        int first = 0;  //Tracks Matrix Column        
        
        while (total != 0) {
            //FIND SMALLEST DISTANCE
            
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    if(matrix[i][j].distance < small.distance) {
                        small = matrix[i][j];
                        first = i;
                    }                       
                }
            }
            
            for(int j = 0; j < matrix.length; j++) {
                Vertex w = matrix[first][j];    //Current Vertex
                
                //FIND PATH
                if(!w.known) {
                    if(small.distance < w.initialCost) {
                        w.distance = w.initialCost + small.distance;
                        w.previous = small;
                    }
                }
                
                System.out.println(w.distance);
            }    
            
            total--;    //Decrement loop
        }

        //PRINT PATHS
        System.out.println("PATHS:");
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix.length; j++) {
                print(matrix[i][j]);
                System.out.println();
                printReverse(matrix[i][j]);
                System.out.println();
            }
        }
    }

    public static void print(Vertex v) {
        if (v.previous != null) {
            print(v.previous);
            System.out.print(" to ");
        }
        System.out.print(v.letter);
    }

    public static void printReverse(Vertex v) {
        while (v != null) {
            System.out.print(v.letter);
            System.out.print(" to ");

            v = v.previous;
        }
    }
}