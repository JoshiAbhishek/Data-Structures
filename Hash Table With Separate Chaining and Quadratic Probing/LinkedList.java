/*
    Abhishek Joshi
    CSE 373 A
    HW 5
 */
/*
    A Linked List is a list data structure of linked Nodes.
 */
public class LinkedList {

    private int size;   //Keeps track of the size of the list
    private Node front; //Keeps track of the front of the list
    private Node currentNode;   //Keeps track of the current node in the list for iterating through it

    /*
        Constructs the list with a default size of 0 and the current node set to the list's front.
     */
    public LinkedList() {
        front = new Node();
        size = 0;

        currentNode = front;
    }

    /*
        Adds the given value to the front of the list.
     */
    public void addData(String data) {
        Node temp = new Node(data);

        addNode(temp);
    }

    /*
        Adds the given Node to the front of the list.
     */
    public void addNode(Node toAdd) {
        toAdd.countPlus();
        
        toAdd.next = front.next;

        front.next = toAdd;

        size++;
    }

    /*
        Returns true if the list has a size of 0, false otherwise. 
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /*
        Returns the size of the list.
     */
    public int getSize() {
        return size;
    }

    /*
        Returns true if the given key is in the list, false otherwise.
     */
    public boolean contains(String data) {
        Node current = front.next;

        while (current != null) {
            if (current.data.equals(data)) {    //Returns true if the Node's data equals the given key
                return true;
            }

            current = current.next;
        }

        return false;
    }

    /*
        Returns the Node whose data value equals the given key, null otherwise.
     */
    public Node get(String data) {
        Node current = front.next;

        while (current != null) {
            if (current.data.equals(data)) {    //Returns the Node if its data equals the given key
                return current;
            }

            current = current.next;
        }

        return null;
    }

    /*
        Returns the next key in the list by iterating through it, if all the Nodes
        have been iterated through, returns null. 
     */
    public String getNextKey() {
        currentNode = currentNode.next;

        if (currentNode == null) {
            return null;
        } else {
            return currentNode.data;
        }
    }
}
