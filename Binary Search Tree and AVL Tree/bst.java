/*
 Abhishek Joshi
 CSE 373 A
 HW 3, bst.java
 */
/*
 A bst class is an information retrieval system that allows for users to
 retrieve and store articles according to their keywords.
 */
public class bst {

    private Node root; //The root of the binary search tree

    /*
     The Node class describes the nodes for use in a binary search tree based
     information retrieval system for articles. 
     */
    private class Node implements Comparable<Node> {

        String keyword; //The keyword for corresponding articles
        Record record;  //The Record object that stores information for an article of the keyword
        int size;   //The size of the node and its records
        Node l; //The left child node
        Node r; //The right child node

        /*
         Constructs a node with a given keyword
         */
        private Node(String k) {
            this(k, null, 0, null, null);
        }

        /*
         Constructs a node with all attributes given
         */
        private Node(String k, Record rec, int size, Node l, Node r) {
            keyword = k;
            record = rec;
            this.size = size;
            this.l = l;
            this.r = r;
        }

        /*
         Adds the given record to the front of the current list of records held in the node
         */
        private void update(Record r) {
            Record temp = record;
            r.next = temp;
            record = r;

            size++;
        }

        /*
         Allows the node to be compared to another given node
         */
        public int compareTo(Node other) {
            return keyword.compareTo(other.keyword);
        }
    }

    /*
     Constructs a bst instance
     */
    public bst() {
        this.root = null;
    }

    /*
     Adds the given record to the node with the associated keyword. 
     If there is no node, the code adds one.
     */
    public void insert(String keyword, FileData fd) {
        Record recordToAdd = new Record(fd.id, fd.title, fd.author, null);

        root = insert(keyword, recordToAdd, root);
    }

    /*
     Adds the given record to the node with the associated keyword. 
     If there is no node, the code adds one.
     */
    private Node insert(String keyword, Record recordToAdd, Node current) {
        if (current == null) { //No node with keyword
            return new Node(keyword, recordToAdd, 1, null, null);
        } else if (keyword.compareTo(current.keyword) < 0) {    //Go Left
            current.l = insert(keyword, recordToAdd, current.l);
        } else if (keyword.compareTo(current.keyword) > 0) {    //Go Right
            current.r = insert(keyword, recordToAdd, current.r);
        } else {    //Node with keyword found
            current.update(recordToAdd);
        }
        return current;
    }

    /*
     Returns true if the keyword for articles is in the bst, false otherwise
     */
    public boolean contains(String keyword) {
        return contains(keyword, root);
    }

    /*
     Returns true if the keyword for articles is in the bst, false otherwise
     */
    private boolean contains(String keyword, Node current) {
        if (current == null) {  //No keyword for articles
            return false;
        } else if (keyword.compareTo(current.keyword) < 0) {    //Go Left
            return contains(keyword, current.l);
        } else if (keyword.compareTo(current.keyword) > 0) {    //Go Right
            return contains(keyword, current.r);
        } else {    //Keyword Found
            return true;
        }
    }

    /*
     Returns the first record for the given keyword. If the keyword is not
     in the bst, returns null.
     */
    public Record get_records(String keyword) {
        return get_records(keyword, root);
    }

    /*
     Returns the first record for the given keyword. If the keyword is not
     in the bst, returns null.
     */
    private Record get_records(String keyword, Node current) {
        if (current == null) {  //Keyword not found
            return null;
        } else if (keyword.compareTo(current.keyword) < 0) {    //Go Left
            return get_records(keyword, current.l);
        } else if (keyword.compareTo(current.keyword) > 0) {    //Go Right
            return get_records(keyword, current.r);
        } else {    //Keyword Found
            return current.record;
        }
    }

    /*
     Removes the Node with the given keyword from the bst. Does nothing
     if such a node does not exist.
     */
    public void delete(String keyword) {
        root = delete(keyword, root);
    }

    /*
     Removes the Node with the given keyword from the bst. Does nothing
     if such a node does not exist.
     */
    private Node delete(String keyword, Node current) {
        if (current == null) {  //Node does not exist
            return current;
        } else if (keyword.compareTo(current.keyword) < 0) { //Go Left
            current.l = delete(keyword, current.l);
        } else if (keyword.compareTo(current.keyword) > 0) { //Go Right
            current.r = delete(keyword, current.r);
        } else { //Node to delete has been found
            if (current.r == null && current.l == null) { //No Children
                current = null;
            } else if (current.r == null && current.l != null) { //One-Left Child
                current = current.l;
            } else if (current.r != null && current.l == null) { //One-Right Child
                current = current.r;
            } else { //Two Children
                current.record = deleteShift(current.r).record;
                current.r = delete(current.keyword, current.r);
            }
        }
        return current;
    }

    /*
     Shifts the nodes in the bst to accomodate the deletion of a node
     */
    private Node deleteShift(Node current) {
        if (current == null) {  //Node is null
            return null;
        } else if (current.l == null) { //Left child is null
            return current;
        }
        return deleteShift(current.l);
    }

    /*
     Prints out the given node
     */
    public void print() {
        print(root);
    }

    /*
     Prints out the given node
     */
    private void print(Node t) {
        if (t != null) {
            print(t.l);
            System.out.println(t.keyword);
            Record r = t.record;
            while (r != null) {
                System.out.printf("\t%s\n", r.title);
                r = r.next;
            }
            print(t.r);
        }
    }
}
