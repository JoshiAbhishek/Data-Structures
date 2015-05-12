/*
    Abhishek Joshi
    CSE 373 A
    HW 5
 */

/*
    A ChainingHash implements a hash table that resolves collisions using 
    quadratic probing.
 */
public class QPHash {

    private int currentIndex;   //Keeps track of the current index in the hash table for iterating through its keys
    private static int tableSize;   //Keeps track of the table's total size
    private Node[] myTable;   //The table
    private int numElements;    //Keeps track of the number of keys in the table

    /*
        Constructs the table with a default table size of 1,000,001.
     */
    public QPHash() {
        this(1000001);
    }

    /*
        Constructs the table with the given size.
     */
    public QPHash(int startSize) {
        tableSize = startSize;
        numElements = 0;
        currentIndex = 0;

        myTable = new Node[tableSize];

        for (int i = 0; i < myTable.length; i++) {
            myTable[i] = new Node();
        }
    }

    /**
     * This function allows rudimentary iteration through the QPHash. The
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

        Node nextKey = myTable[currentIndex];

        while (nextKey.data == null) {
            currentIndex++; //Increments the current index of the table

            if (currentIndex == tableSize - 1) {    //Returns null if the table has been entirely iterated through
                return null;
            }

            nextKey = myTable[currentIndex];
        }

        currentIndex++; //Increments the current index of the table

        return nextKey.data;    //Returns the next element in the table
    }

    /**
     * Adds the key to the hash table. If there is a collision, a new location
     * should be found using quadratic probing. If the key is already in the
     * hash table, it increments that key's counter.
     *
     * @param keyToAdd : the key which will be added to the hash table
     */
    /*
        Inserts the given String into the table. Utilizes quadratic probing if
        there is a collision. If the key is already present, increments the 
        key's counter.
     */
    public void insert(String keyToAdd) {
        int val = hash(keyToAdd, 0);

        int j = 0;

        //Increments the spot for the key using quadratic probing
        while (myTable[val].data != null && !myTable[val].data.equals(keyToAdd)) {
            j++;

            val = hash(keyToAdd, j);

            if (val >= tableSize) {
                val -= tableSize;
            }
        }

        //Increments the key's count if it is already presented in the table, or adds it otherwise
        if (myTable[val].data != null && myTable[val].data.equals(keyToAdd)) {
            myTable[val].countPlus();
        } else {
            myTable[val].data = keyToAdd;
            myTable[val].countPlus();
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
        int val = hash(keyToFind, 0);

        int j = 0;

        //Iterates through the table according to quadratic probing to find the key, if present
        while (myTable[val].data != null && j < tableSize) {
            if (myTable[val].data.equals(keyToFind)) {
                return myTable[val].getCount(); //Returns the key's count if it has been added to the table
            }

            j++;
            val = hash(keyToFind, j);

            if (val >= tableSize) {
                val -= tableSize;
            }
        }

        return 0;   //Returns 0 if the key has not been added to the table
    }

    /*
        Returns the total number of keys in the table.
     */
    public int getElementsNum() {
        return numElements;
    }

    /*
        Returns a hash value for the given key. 
     */
    private int hash(String keyToHash, int index) {
        int hashVal = 0;

        for (int i = 0; i < keyToHash.length(); i++) {
            hashVal = 37 * hashVal + keyToHash.charAt(i);
        }

        hashVal += (index * index);

        hashVal %= tableSize;

        return Math.abs(hashVal);
    }
}
