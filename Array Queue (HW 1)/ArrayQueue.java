package arrayqueue;

/*
    An ArrayQueue is a data structure that implements the queue ADT in form of a
    circular array
*/
public class ArrayQueue {

    private String[] queueArray; //Main array for the ArrayQueue
    private int size; //Number of elements in the ArrayQueue
    private int front; //Points to the front of the ArrayQueue
    private int back; //Points to the back of the ArrayQueue

    /*
        Constructs an ArrayQueue with default configurations
    */

    public ArrayQueue() {
        queueArray = new String[100];
        size = 0;
        front = 0;
        back = -1;
    }

    /*
        Constructs an ArrayQueue with default configurations except for the
        size, which is given as an argument
    */
    public ArrayQueue(int startSize) {
        queueArray = new String[startSize];
        size = 0;
        front = 0;
        back = -1;
    }

    /*
        @function returns the number of elements in the queue
        @return size
    */
    public int getSize() {
        return size;
    }

    /*
       @function adds a string to the end of the queue
       @param toEnqueue: the input to be inserted
    
       If the array is already full of elements, prints out an error message.
    */
    public void enqueue(String toEnqueue) {
        if (isFull()) {
            System.err.println("No more elements can be added");
        }
        else{
            size++;
            int temp = (back + 1) % queueArray.length;
            queueArray[temp] = toEnqueue;
            back = temp;
        }            
    }

    /*
        @function removes the string from the front of the queue
        @return the string from the front of the queue
    
        If the array is empty and cannot have any more elements dequeued,
        prints out an error message and returns null.
    */
    public String dequeue() {
        if (isEmpty()) {
            System.err.println("No elements that can be dequeued");
            return null;
        }
        else {
            size--;
            String toDequeue = queueArray[front];
            front = (front + 1) % queueArray.length;
            return toDequeue;
        }        
    }

    /*
        @return returns true if the queue is empty, false otherwise
    */
    public boolean isEmpty() {
        return size == 0;
    }

    /*
        @return returns true if the queue is full, false otherwise
    */
    public boolean isFull() {
        return queueArray.length - 1 == size;
    }
}
