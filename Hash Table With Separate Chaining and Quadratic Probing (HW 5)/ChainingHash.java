/*
    Abhishek Joshi
    CSE 373 A
    HW 5
 */

/*
    A ChainingHash implements a hash table that resolves collisions using 
    separate chaining.
 */
public class ChainingHash {

    private int currentIndex;   //Keeps track of the current index in the hash table for iterating through its keys
    private static int tableSize;   //Keeps track of the table's total size
    private LinkedList[] myTable;   //The table
    private int numElements;    //Keeps track of the number of keys in the table

    /*
        Constructs the table with a default table size of 1,000,001.
     */
    public ChainingHash() {
        this(1000001);
    }

    /*
        Constructs the table with the given size.
     */
    public ChainingHash(int startSize) {
        tableSize = startSize;
        numElements = 0;
        currentIndex = 0;

        myTable = new LinkedList[tableSize];

        for (int i = 0; i < myTable.length; i++) {
            myTable[i] = new LinkedList();
        }
    }

    /**
     * This function allows rudimentary iteration through the ChainingHash. The
     * ordering is not important so long as all added elements are returned only
     * once. It should return null once it has gone through all elements
     *
     * @return Returns the next element of the hash table. Returns null if it is
     * at its end.
     */
    /*
        Iterates through the table, returning the next element in the table. 
        If the entire table has been iterated through, returns null. 
     */
    public String getNextKey() {
        if (currentIndex == tableSize - 1) {    //Returns null if the table has been entirely iterated through
            return null;
        }

        String nextKey = myTable[currentIndex].getNextKey();

        while (nextKey == null) {
            currentIndex++; //Increments the current index of the table

            if (currentIndex == tableSize - 1) {    //Returns null if the table has been entirely iterated through
                return null;
            }

            nextKey = myTable[currentIndex].getNextKey();
        }

        currentIndex++; //Increments the current index of the table
        
        return nextKey; //Returns the next element in the table
    }

    /**
     * Adds the key to the hash table. If there is a collision, it should be
     * dealt with by chaining the keys together. If the key is already in the
     * hash table, it increments that key's counter.
     *
     * @param keyToAdd : the key which will be added to the hash table
     */
    /*
        Inserts the given String into the table. Utilizes separate chaining if
        there is a collision. If the key is already present, increments the 
        key's counter.
     */
    public void insert(String keyToAdd) {
        LinkedList theList = myTable[hash(keyToAdd)];

        Node temp = theList.get(keyToAdd);

        if (temp == null) { //Adds the key if it is not already present
            theList.addData(keyToAdd);
        } else {
            temp.countPlus();   //Increments the key's count if it is already in the table
        }
        
        numElements++;
    }

    /**
     * Returns the number of times a key has been added to the hash table.
     *
     * @param keyToFind : The key being searched for
     * @return returns the number of times that key has been added.
     */
    /*
        Finds the number of times a key has been added to the table and returns
        the count.
     */
    public int findCount(String keyToFind) {
        LinkedList theList = myTable[hash(keyToFind)];

        Node temp = theList.get(keyToFind);

        if (temp == null) { //Returns 0 if the key has not been added to the table
            return 0;
        }

        return temp.getCount(); //Returns the key's count if it has been added to the table
    }

    /*
        Returns a hash value for the given key. 
     */
    private int hash(String keyToHash) {
        int hashVal = 0;

        for (int i = 0; i < keyToHash.length(); i++) {
            hashVal = 37 * hashVal + keyToHash.charAt(i);
        }

        hashVal %= tableSize;

        if (hashVal < 0) {
            hashVal += tableSize;
        }

        return Math.abs(hashVal);
    }
    
    /*
        Returns the total number of keys in the table.
     */
    public int getElementsNum() {
        return numElements;
    }

}
