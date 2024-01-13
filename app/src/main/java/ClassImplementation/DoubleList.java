package ClassImplementation;
/**
 * @author 8210311 Daniela Moreira
 * @author 8210367 Orlando Pires
 */

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import Interfaces.ListADT;

import java.util.Iterator;

public class DoubleList<T> implements ListADT<T>{
    protected DoubleNode<T> front,rear;
    protected int count;

    public DoubleList(){
        rear = null;
        front = null;
        count = 0;
    }

    public T removeLast () throws EmptyCollectionException {
        T result;

        if (isEmpty()) {
            throw new EmptyCollectionException("list");
        }
        result = rear.getElement();
        rear = rear.getPrevious();

        if (rear == null){
            front = null;
        } else{
            rear.setNext(null);
        }
        count--;

        return result;
    }

    public T removeFirst() throws EmptyCollectionException{
        if (isEmpty()) {
            throw new EmptyCollectionException("list");
        }
        T result = front.getElement();
        front = front.getNext();

        if (front==null) {
            rear = null;
        }else{
                front.setPrevious(null);
            }
        count--;

        return result;
    }

    public T remove (T element){
        T result;
        DoubleNode<T> nodeptr = find (element);


        if (nodeptr == null) {
            throw new ElementNotFoundException("list");
        }
        result = nodeptr.getElement();

        // check to see if front or rear
        if (nodeptr == front) {
            result = this.removeFirst();

        }else if (nodeptr == rear) {
            result = this.removeLast();

        }else{
            nodeptr.getNext().setPrevious(nodeptr.getPrevious());
            nodeptr.getPrevious().setNext(nodeptr.getNext());
            count--;
        }

        return result;
    }

    public T first() throws EmptyCollectionException {
        if (isEmpty()){
            throw new EmptyCollectionException("list");
        }
        return front.getElement();
    }

    public T last() throws EmptyCollectionException{
        if (isEmpty()) {
            throw new EmptyCollectionException("list");
        }
        return rear.getElement();
    }

    public boolean contains (T target) {
        return (find(target) != null);
    }

    private DoubleNode<T> find (T target){
        boolean found = false;
        DoubleNode<T> traverse = front;
        DoubleNode<T> result = null;

        if (! isEmpty())
            while (! found && traverse != null)
                if (target.equals(traverse.getElement()))
                    found = true;
                else
                    traverse = traverse.getNext();

        if (found)
            result = traverse;

        return result;
    }

    public boolean isEmpty(){
        return (count == 0);
    }

    public int size(){
        return count;
    }

    public Iterator<T> iterator(){
        return new DoubleIterator<T>(front, count);
    }

    public String toString()    {
        String result = "";
        DoubleNode<T> traverse = front;

        while (traverse != null)
        {
            result = result + (traverse.getElement()).toString() + "\n";
            traverse = traverse.getNext();
        }
        return result;
    }

}
