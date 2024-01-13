package ListsDavid;

import java.util.Iterator;
import Exceptions.EmptyCollectionException;
import Interfaces.OrderedListADT;

/**
 * 
 * @author David Francisco (8210088)
 * @author Guilherme Silva (8210190)
 */
public class LinkedList<T> implements OrderedListADT<T>{

    private int count;
    private LinearNode<T> head, tail;

    @Override
    public T removeFirst() {
        if(isEmpty()) {
            throw new EmptyCollectionException("Linked List");
        }

        T result = head.getElement();
        head = head.getNext();
        count--;
        if(isEmpty()) {
            tail = null;
        }
        return result;
    }

    @Override
    public T removeLast() {
        if(isEmpty()) {
            throw new EmptyCollectionException("Linked List");
        }
        T result = tail.getElement();
        LinearNode<T> current = head;
        while(current.getNext() != tail) {
            current = current.getNext();
        }
        tail = current;
        tail.setNext(null);
        count--;
        return result;
    }

    @Override
    public T remove(T element) {
        if(isEmpty()) {
            throw new EmptyCollectionException("Linked List");
        }
        boolean found = false;
        LinearNode<T> previous = null;
        LinearNode<T> current = head;
        while(current != null && !found) {
            if(element.equals(current.getElement())) {
                found = true;
            } else {
                previous = current;
                current = current.getNext();
            }
        }
        if(!found) {
            throw new EmptyCollectionException("Linked List");
        }
        if(size() == 1) {
            head = tail = null;
        } else if(current.equals(head)) {
            head = current.getNext();
        } else if(current.equals(tail)) {
            tail = previous;
            tail.setNext(null);
        } else {
            previous.setNext(current.getNext());
        }
        count--;
        return current.getElement();
    }

    @Override
    public T first() {
        if(isEmpty()) {
            throw new EmptyCollectionException("Linked List");
        }
        return head.getElement();
    }

    @Override
    public T last() {
        if(isEmpty()) {
            throw new EmptyCollectionException("Linked List");
        }
        return tail.getElement();
    }

    @Override
    public boolean contains(T target) {
        if(isEmpty()) {
            throw new EmptyCollectionException("Linked List");
        }
        boolean found = false;
        LinearNode<T> current = head;
        while(current != null && !found) {
            if(target.equals(current.getElement())) {
                found = true;
            } else {
                current = current.getNext();
            }
        }
        return found;
    }

    @Override
    public boolean isEmpty() {
        return (count == 0);
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator<T>(this.head, this.count);
    }

    @Override
    public void add(T element) {
        LinearNode<T> node = new LinearNode<T>(element);
        if(isEmpty()) {
            head = node;
            tail = node;
        } else {
            tail.setNext(node);
            tail = node;
        }
        count++;
        
    }

    private class LinkedListIterator<T> implements Iterator<T>{
        private LinearNode<T> current;
        private int count;
        private int index;

        public LinkedListIterator(LinearNode<T> head, int count) {
            this.current = head;
            this.count = count;
            this.index = 0;
        }

        
        public boolean hasNext() {
            return this.index < this.count;
        }

        
        public T next() {
            if (this.index < this.count) {
                T result = this.current.getElement();
                this.current = this.current.getNext();
                ++this.index;
                return result;
            } else {
                return null;
            }
        }

        
        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported.");
        }
    }

    public LinearNode<T> getHead() {
        return head;
    }
    
}
