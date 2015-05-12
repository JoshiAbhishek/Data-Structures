/*
    Abhishek Joshi
    CSE 373 A
    HW 5
 */

/*
    A Node defines a node for a Linked List object. 
 */
public class Node {

    public String data; //Keeps track of the node's key value
    public Node next;   //Keeps track of the node's pointer to the next node
    private int count;  //Keeps track of the number of times the key has been added

    /*
        Constructs the node with a default key value of null
     */
    public Node() {
        this(null);
    }

    /*
        Constructs the node with the given key value
     */
    public Node(String data) {
        this.data = data;
        count = 0;
        next = null;
    }

    /*
        Increments the count of the key by the given value
     */
    public void addCount(int toAdd) {
        count += toAdd;
    }

    /*
        Increments the count of the key by one
     */
    public void countPlus() {
        count++;
    }

    /*
        Returns the count of the key
     */
    public int getCount() {
        return count;
    }
}
