package ListsDavid;

import Exceptions.EmptyCollectionException;
import Interfaces.QueueADT;

/**
 * 
 * @author David Francisco (8210088)
 * @author Guilherme Silva (8210190)
 */
public class Queue<T> implements QueueADT<T> {
    private final int DEFAULT_CAPACITY = 100;
    private int rear;
    private T[] list;


    @Override
    public void enqueue(T element) {
        if(rear == list.length) {
            expandCapacity();
        }
        list[rear] = element;
        rear++;        
    }

    @Override
    public T dequeue() throws EmptyCollectionException {
        if(isEmpty()) {
            throw new EmptyCollectionException("Linked List");
        }
        T result = list[0];
        for(int i = 0; i < rear; i++) {
            list[i] = list[i + 1];
        }
        rear--;
        return result;
    }

    @Override
    public T first() throws EmptyCollectionException {
        if(isEmpty()) {
            throw new EmptyCollectionException("Linked List");
        }
        return list[0];
    }

    @Override
    public boolean isEmpty() {
        return rear == 0;
    }

    @Override
    public int size() {
        return rear;
    }
    
    public void expandCapacity() {
        T[] larger = (T[]) (new Object[list.length * 2]);
        for(int i = 0; i < list.length; i++) {
            larger[i] = list[i];
        }
        list = larger;
    }
}
