package dijkstra;

public class Vertex {

    public boolean known;
    public int previous;
    public int cost;
    public String letter;

    public Vertex() {
        known = false;
        previous = -1;
        cost = 9999;
        letter = null;
    }

    public void setLetter(int i) {
        if (i == 0) {
            letter = "A";
        } else if (i == 1) {
            letter = "B";
        } else if (i == 2) {
            letter = "C";
        } else if (i == 3) {
            letter = "D";
        } else if (i == 4) {
            letter = "E";
        } else if (i == 5) {
            letter = "F";
        } else if (i == 6) {
            letter = "G";
        }
    }
}
