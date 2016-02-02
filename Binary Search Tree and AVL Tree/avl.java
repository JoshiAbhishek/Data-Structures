/*
 Abhishek Joshi
 CSE 373 A
 HW 3, avl.java
 */
/*
 An avl class is a self-balancing information retrieval system that allows 
 for users to retrieve and store articles according to their keywords.
 */

public class avl {

    private AvlNode root;   //The root of the avl tree
    private static final int ALLOWED_IMBALANCE = 1; //The allowed imbalance constant for the avl tree

    /*
     Constructs an avl instance
     */
    public avl() {
        this.root = null;
    }

    /*
     The Node class describes the nodes for use in a avl tree based
     information retrieval system for articles.
     */
    private class AvlNode implements Comparable<AvlNode> {

        String keyword; //The keyword for corresponding articles
        Record record;  //The Record object that stores information for an article of the keyword
        int size;   //The size of the node and its records
        int height; //The height of the node
        AvlNode l; //The left child node
        AvlNode r; //The right child node

        /*
         Constructs a node with a given keyword
         */
        private AvlNode(String k) {
            this(k, null, 0, 0, null, null);
        }

        /*
         Constructs a node with all attributes given
         */
        private AvlNode(String k, Record rec, int size, int height, AvlNode l, AvlNode r) {
            keyword = k;
            record = rec;
            this.size = size;
            this.height = height;
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
        public int compareTo(AvlNode other) {
            return keyword.compareTo(other.keyword);
        }
    }

    /*
     Returns true if the keyword for articles is in the avl, false otherwise
     */
    public boolean contains(String keyword) {
        return contains(keyword, root);
    }

    /*
     Returns true if the keyword for articles is in the avl, false otherwise
     */
    private boolean contains(String keyword, AvlNode current) {
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
     Returns the height of the current node, null if the node does not exist
     */
    public int height(AvlNode current) {
        if (current == null) {  //If node does not exist
            return -1;
        } else {
            return current.height;
        }
    }

    /*
     Adds the given record to the node with the associated keyword and self-balances. 
     If there is no node, the code adds one.
     */
    public void insert(String keyword, FileData fd) {
        Record recordToAdd = new Record(fd.id, fd.title, fd.author, null);
        root = insert(keyword, recordToAdd, root);
    }

    /*
     Adds the given record to the node with the associated keyword and self-balances. 
     If there is no node, the code adds one.
     */
    private AvlNode insert(String keyword, Record recordToAdd, AvlNode current) {
        if (current == null) {  //No node with keyword
            return new AvlNode(keyword);
        } else if (keyword.compareTo(current.keyword) < 0) {    //Go Left
            current.l = insert(keyword, recordToAdd, current.l);
        } else if (keyword.compareTo(current.keyword) > 0) {    //Go Right
            current.r = insert(keyword, recordToAdd, current.r);
        } else {    //Node with keyword found
            current.update(recordToAdd);
        }
        return balance(current);
    }

    /*
     Balances the given avl node and its subtrees. Assumes that the node is either
     balanced or within one of being balanced. 
     */
    private AvlNode balance(AvlNode current) {
        if (current == null) {  //If node is null
            return current;
        }
        if (current.l != null && current.r != null) {
            if ((current.l.height - current.r.height) > ALLOWED_IMBALANCE) {    //If left subtree is imbalanced
                if (current.l.l.height >= current.l.r.height) { //If the left subtree of the left child has a greater hieght than the right subtree
                    current = rotateLeftChild(current);
                } else {
                    current = doubleLeftChild(current);
                }
            } else if ((current.r.height - current.l.height) > ALLOWED_IMBALANCE) { //If right subtree is imbalanced
                if (current.r.r.height >= current.r.l.height) { ////If the right subtree of the right child has a greater hieght than the left subtree
                    current = rotateRightChild(current);
                } else {
                    current = doubleRightChild(current);
                }
            }

            current.height = Math.max(height(current.l), height(current.r));
        }

        return current;
    }

    /*
     Performs a single left child rotation of the given avl node and resets the hieght of the nodes    
     */
    private AvlNode rotateLeftChild(AvlNode initial) {
        AvlNode current = initial.l;
        initial.l = current.r;
        current.r = initial;
        initial.height = Math.max(height(initial.l), height(initial.r)) + 1; //WHY +1???
        current.height = Math.max(height(current.l), height(current.r)) + 1;

        return current;
    }

    /*
     Performs a single right child rotation of the given avl node and resets the hieght of the nodes    
     */
    private AvlNode rotateRightChild(AvlNode initial) {
        AvlNode current = initial.r;
        initial.r = current.l;
        current.l = initial;
        initial.height = Math.max(height(initial.l), height(initial.r)) + 1;
        current.height = Math.max(height(current.l), height(current.r)) + 1;

        return current;
    }

    /*
     Performs a double left child rotation of the given avl node and resets the hieght of the nodes    
     */
    private AvlNode doubleLeftChild(AvlNode initial) {
        initial.l = rotateRightChild(initial.l);    //Single right child rotation
        initial = rotateLeftChild(initial); //Single left child rotation

        return initial;
    }

    /*
     Performs a double right child rotation of the given avl node and resets the hieght of the nodes    
     */
    private AvlNode doubleRightChild(AvlNode initial) {
        initial.r = rotateLeftChild(initial.r); //Single left child rotation
        initial = rotateRightChild(initial);    //Single right child rotation

        return initial;
    }

    /*
     Removes the Node with the given keyword from the avl and self-balances. Does nothing
     if such a node does not exist.
     */
    public void delete(String keyword) {
        root = delete(keyword, root);
    }

    /*
     Removes the Node with the given keyword from the avl and self-balances. Does nothing
     if such a node does not exist.
     */
    private AvlNode delete(String keyword, AvlNode current) {
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

        return balance(current);
    }

    /*
     Shifts the nodes in the avl to accomodate the deletion of a node
     */
    private AvlNode deleteShift(AvlNode current) {
        if (current == null) {  //Node is null
            return null;
        } else if (current.l == null) { //Left child is null
            return current;
        }
        return deleteShift(current.l);
    }

    /*
     Returns the first record for the given keyword. If the keyword is not
     in the avl, returns null.
     */
    public Record get_Records(String keyword) {
        return get_Records(keyword, root);
    }

    /*
     Returns the first record for the given keyword. If the keyword is not
     in the avl, returns null.
     */
    private Record get_Records(String keyword, AvlNode current) {
        if (current == null) {  //Keyword not found
            return null;
        } else if (keyword.compareTo(current.keyword) < 0) {   //Go Left 
            return get_Records(keyword, current.l);
        } else if (keyword.compareTo(current.keyword) > 0) {    //Go Right
            return get_Records(keyword, current.r);
        } else {    //Keyword Found
            return current.record;
        }
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
    private void print(AvlNode current) {
        if (current != null) {
            print(current.l);
            System.out.println(current.keyword);
            Record r = current.record;
            while (r != null) {
                System.out.printf("\t%s\n", r.title);
                r = r.next;
            }
            print(current.r);
        }
    }
}
