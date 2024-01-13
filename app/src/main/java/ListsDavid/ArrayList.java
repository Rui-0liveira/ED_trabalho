package ListsDavid;

import java.util.Iterator;

import Exceptions.EmptyCollectionException;
import Interfaces.OrderedListADT;

/**
 * 
 * @author David Francisco (8210088)
 * @author Guilherme Silva (8210190)
 */
public class ArrayList<T> implements OrderedListADT<T> {

    private final int DEFAULT_CAPACITY = 100;
    private int rear;
    private T[] list;

    public ArrayList() {
        rear = 0;
        list = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    @Override
    public T removeFirst() {
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
    public T removeLast() {
        if(isEmpty()) {
            throw new EmptyCollectionException("Linked List");
        }
        T result = list[rear - 1];
        rear--;
        return result;
    }

    @Override
    public T remove(T element) {
        if(isEmpty()) {
            throw new EmptyCollectionException("Linked List");
        }
        boolean found = false;
        int index = 0;
        while(index < rear && !found) {
            if(element.equals(list[index])) {
                found = true;
            } else {
                index++;
            }
        }
        if(!found) {
            throw new EmptyCollectionException("Linked List");
        }
        T result = list[index];
        for(int i = index; i < rear; i++) {
            list[i] = list[i + 1];
        }
        rear--;
        return result;
    }

    public T remove(int index) {
        if(isEmpty()) {
            throw new EmptyCollectionException("Linked List");
        }
        T result = list[index];
        for(int i = index; i < rear; i++) {
            list[i] = list[i + 1];
        }
        rear--;
        return result;
    }

    @Override
    public T first() {
        if(isEmpty()) {
            throw new EmptyCollectionException("Linked List");
        }
        return list[0];
    }

    @Override
    public T last() {
        if(isEmpty()) {
            throw new EmptyCollectionException("Linked List");
        }
        return list[rear - 1];
    }

    @Override
    public boolean contains(T target) {
        if(isEmpty()) {
			return false;
        }
        boolean found = false;
        int index = 0;
        while(index < rear && !found) {
            if(target.equals(list[index])) {
                found = true;
            } else {
                index++;
            }
        }
        return found;
    }

    public T get(int index) {
        if(isEmpty()) {
            throw new EmptyCollectionException("Linked List");
        }
        return list[index];
    }

    @Override
    public boolean isEmpty() {
        return rear == 0;
    }

    @Override
    public int size() {
        return rear;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayListIterator<T>(this.list, this.rear);
    }

    @Override
    public void add(T element) {
        if(rear == list.length) {
            expandCapacity();
        }
        list[rear] = element;
        rear++;
    }

    private class ArrayListIterator<T> implements Iterator<T>{
        private T[] list;
        private int count;
        private int index;

        public ArrayListIterator(T[] list, int count) {
            this.list = list;
            this.count = count;
            this.index = 0;
        }


        public boolean hasNext() {
            return this.index < this.count;
        }


        public T next() {
            if (this.index < this.count) {
                T result = this.list[this.index];
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

    public void expandCapacity() {
        T[] larger = (T[]) (new Object[list.length * 2]);
        for(int i = 0; i < list.length; i++) {
            larger[i] = list[i];
        }
        list = larger;
    }

    public void set(int index, T element) {
        list[index] = element;
    }
}
