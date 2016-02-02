/*
    Abhishek Joshi
    CSE 373 A
    HW 5
 */

/*
    Constructs two hash tables and inserts keys from Shakespeare and Bacon's works,
    printing out the total comparative error of the keys for both tables.
 */
public class Test {

    public static void main(String[] args) {
        FileInput.init();   //Initializes the input object for reading in the Shakespeare and Bacon works

        ChainingHash chain = new ChainingHash(1001);    //Initializes a hash table utilizing separate chaining
        QPHash quad = new QPHash(1001); //Initializes a hash table utilizing quadratic probing

        String[] shakespeare = FileInput.readShakespeare(); //Reads the keys from the Shakespeare work into an array

        String[] bacon = FileInput.readBacon(); //Reads the keys from the Bacon work into an array

        /*
            Inserts the values from the Shakespeare work into the separate chaining hash table.
         */
        for (String s : shakespeare) {
            chain.insert(s);
        }

        /*
            Inserts the values from the Bacon work into the quadratic probing hash table.
         */
        for (String s : bacon) {
            quad.insert(s);
        }

        double error = 0;  //Keeps track of the total comparative error
        String distant = null;    //Keeps track of the word with the highest difference in frequency
        double maxDiff = 0; //Keeps track of the difference for the word with the highest one in frequency

        /*
            Iterates through the first hash table and adds the sum of the squared error
            for the words. 
         */
        String fromS = chain.getNextKey();

        while (fromS != null) {
            double first = 1.0 * chain.findCount(fromS);
            first /= shakespeare.length;    //Computes the frequency of the word from the Shakespeare work

            double second = 1.0 * quad.findCount(fromS);
            second /= (double) bacon.length;    //Computes the frequency of the word from the Bacon work

            double diff = Math.abs(first - second); //Computes the difference between the two word frequencies

            double diffSquared = diff * diff;   //Squares the difference

            error += (diffSquared); //Adds the square of the difference to the total comparative error

            if (diffSquared > maxDiff) {    //Replaces and sets the word with the highest difference
                maxDiff = diffSquared;
                distant = fromS;
            }

            fromS = chain.getNextKey();
        }

        /*
            Iterates through the second hash table and adds the sum of the squared error
            for the words, only for the frequencies not computed already. 
         */
        String fromB = quad.getNextKey();

        while (fromB != null) {
            double first = 1.0 * quad.findCount(fromB);
            first /= (double) bacon.length; //Computes the frequency of the word from the Bacon work

            double second = 1.0 * chain.findCount(fromB);
            second /= (double) shakespeare.length;  //Computes the frequency of the word from the Shakespeare work

            double diff = Math.abs(first - second); //Computes the difference between the two word frequencies

            double diffSquared = diff * diff;   //Squares the difference
            
            if (second == 0) {  //Checks if the frequency has not already been computed
                error += diffSquared;   //Adds the square of the difference to the total comparative error
            }

            fromB = quad.getNextKey();
        }

        /*
            Prints the total computed error and the word with the highest difference, and its difference.
         */
        
        System.out.println("Total Computed Error: " + error);
        System.out.println("Maximum Square Difference: " + maxDiff);
        System.out.println("Most different word: " + distant);
    }
}
