package ClassImplementation;

import Exceptions.EmptyCollectionException;
import Interfaces.QueueADT;

/**
 * @author 8210311 Daniela Moreira
 * @author 8210367 Orlando Pires
 */
public class LinkedQueue<T> implements QueueADT<T> {

    private int count = 0;
    private Node<T> front = null;
    private Node<T> rear =null;

    public LinkedQueue(){
        this.count = 0;
        this.front = null;
        this.rear = null;
    }

    public void enqueue(T element) {
        Node<T> node = new Node<T>(element);

        if (this.isEmpty()) {
            this.front = node;
            this.rear = node;
        } else {
            this.rear.setNext(node);
            this.rear = node;
        }

        ++this.count;

    }

    public T dequeue() throws EmptyCollectionException {
        if (this.isEmpty()) {
            throw new EmptyCollectionException("LinkedQueue");
        } else {
            T result = this.front.getElement();
            this.front = this.front.getNext();
            --this.count;
            return result;
        }
    }

    public T first() {
        if (this.isEmpty()) {
            throw new EmptyCollectionException("LinkedQueue");
        } else {
            return this.front.getElement();
        }
    }

    public boolean isEmpty() {
        return (this.count == 0);
    }

    public int size() {
        return this.count;
    }

    public String toString() {
        String result = "LinkedQueue {";
        Node<T> current = this.front;
        if (!this.isEmpty()) {
            do {
                result = result + " " + current.getElement();
            } while((current = current.getNext()) != null);
        }

        result = result + " }";
        return result;
    }

    public Node<T> getFront() {
        return front;
    }
    public Node<T> getRear() {
        return rear;
    }
}
