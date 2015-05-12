package arrayqueue;
/*
    An Executor demonstrates a set of oracles whose job is to answer 
    specified questions. Each Oracle has one answer to all questions posed to them. 
*/
public class Executor {

    public static void main(String[] args) {
        Utility.init(); //initializes file readers
        String[] questions = Utility.readQuestions(); //reads question.txt file into questions array
        String[] answers = Utility.readAnswers(); // reads answers.txt file into answers array

        int numOracles = answers.length; //finds the number of oracles

        ArrayQueue[] oracles = new ArrayQueue[numOracles]; //Array of Oracles

        /*
            Creates an ArrayQueue for each Oracle according to the number of Oracles required
        */
        for (int i = 0; i < numOracles; i++) {
            oracles[i] = new ArrayQueue();
        }

        /*
            Sets a question for an Oracle, randomly. Every Oracle may not have
            a question or questions.
        */
        for (String s : questions) {
            int random = Utility.random(numOracles);
            oracles[random].enqueue(s);
        }

        int numOfQuestions = questions.length; //Tracks the number of questions left to answer

        /*
            Goes through every Oracle in a "round-robin" fasion, starting with 
            the first Oracle, then second, and on as each Oracle answers their first 
            question, if they have one. The process is repeated as long as 
            there are still a number of questions to be answered by the Oracles and until all 
            queues are empty, in the "round-robin" fashion.  Each Oracle has just
            one answer to the questions posed to it. 
        */
        while (numOfQuestions != 0) {
            for (int i = 0; i < oracles.length; i++) {
                ArrayQueue temp = oracles[i];
                
                if (!temp.isEmpty()) {
                    String dequeued = temp.dequeue();
                    System.out.println(dequeued + ": " + answers[i]);
                    numOfQuestions--;
                }
            }
        }
    }
}
