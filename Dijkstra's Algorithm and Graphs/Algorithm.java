package dijkstra;

public class Algorithm {

    //INITIALIZE VALUES
    public static void main(String[] args) {
        //List of Vertexes
        Vertex[] list = new Vertex[7];

        //Adjacency Matrix
        int[][] matrix = {{0, 5, 3, 0, 0, 0, 0}, {0, 0, 2, 0, 3, 0, 1}, {0, 0, 0, 0, 7, 0, 0}, {2, 0, 0, 0, 0, 6, 0}, {0, 0, 0, 2, 0, 1, 0}, {0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 1, 0, 0}};

        for (int i = 0; i < list.length; i++) {
            list[i] = new Vertex();
            list[i].setLetter(i);
        }

        //PRINT INITIAL COSTS
        System.out.println("DISTANCES: " + "\n");
        for (int j = 0; j < list.length; j++) {
            System.out.print(list[j].letter + ": " + list[j].cost + " ");
        }
        System.out.println();
        
        list[0].cost = 0;
        
        //PRINT SECOND COSTS
        for (int j = 0; j < list.length; j++) {
            System.out.print(list[j].letter + ": " + list[j].cost + " ");
        }
        System.out.println();

        //FOR REMAINING NOT KNOWN NODES        
        for (int i = 0; i < list.length - 2; i++) {

            //FIND UNKOWN NODE WITH SMALLEST COST
            int min = 9999;
            int current = 0;

            for (int j = 0; j < list.length; j++) {
                if (!list[j].known && list[j].cost < min) {
                    current = j;
                    min = list[j].cost;
                }
            }

            list[current].known = true;

            //FIND + SET PATH
            for (int j = 0; j < list.length; j++) {
                if (!list[j].known && matrix[current][j] != 0) {
                    if (list[current].cost + matrix[current][j] < list[j].cost) {
                        list[j].cost = list[current].cost + matrix[current][j];
                        list[j].previous = current;
                    }
                }
            }

            //PRINT COSTS
            for (int j = 0; j < list.length; j++) {
                System.out.print(list[j].letter + ": " + list[j].cost + " ");
            }
            System.out.println();
        }

        //PRINT PATHS
        System.out.println("\n" + "PATHS: " + "\n");
        print(list);
        System.out.println();
        printReverse(list);
    }

    public static void print(Vertex[] list) {
        for(int i = 0; i < list.length; i++) {
            int value = list[i].previous;
            int total = list.length - 1;
            
            System.out.print(list[i].letter);
            
            while(value != -1 && total != 0) {
                System.out.print(" to " + list[value].letter);
                value = list[value].previous;
                total--;
            }
            
            System.out.println();
        }        
    }

    public static void printReverse(Vertex[] list) {
        for(int i = 0; i < list.length; i++) {
            printReverse(list, i, 0);
            System.out.println();
        }
    }
    
    private static void printReverse(Vertex[] list, int current, int count) {
        if(current != -1) {
            printReverse(list, list[current].previous, ++count);
            System.out.print(list[current].letter);
            
            if(count > 1) {
                System.out.print(" to ");
            }
        }
    }
}