package ClassImplementation;
/**
 * @author 8210311 Daniela Moreira
 * @author 8210367 Orlando Pires
 */
import Interfaces.ListADT;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoubleLinkedList<T> implements ListADT<T>,Iterable<T> {
    protected DoubleNode<T> head = null;
    protected DoubleNode<T> tail = null;
    protected int count = 0;
    protected int modCount = 0;

    public DoubleLinkedList() {
    }

    public T removeFirst() {
        if (this.isEmpty()) {
            return null;
        } else {
            T result = this.head.getElement();
            this.head = this.head.getNext();
            --this.count;
            if (this.isEmpty()) {
                this.tail = null;
            } else {
                this.head.setPrevious((DoubleNode)null);
            }

            ++this.modCount;
            return result;
        }
    }

    public T removeLast() {
        if (this.isEmpty()) {
            return null;
        } else {
            T result = this.tail.getElement();
            this.tail = this.tail.getPrevious();
            --this.count;
            if (this.isEmpty()) {
                this.head = null;
            } else {
                this.tail.setNext((DoubleNode) null);
            }

            ++this.modCount;
            return result;
        }
    }

    public T remove(T element) {
        if (this.isEmpty()) {
            return null;
        } else {
            boolean found = false;
            DoubleNode<T> current = this.head;

            while (current != null && !found) {
                if (element.equals(current.getElement())) {
                    found = true;
                } else {
                    current = current.getNext();
                }
            }

            if (!found) {
                return null;
            } else {
                if (this.size() == 1) {
                    this.head = this.tail = null;
                } else if (current.equals(this.head)) {
                    this.head = current.getNext();
                    this.head.setPrevious((DoubleNode) null);
                } else if (current.equals(this.tail)) {
                    this.tail = current.getPrevious();
                    this.tail.setNext((DoubleNode) null);
                } else {
                    current.getPrevious().setNext(current.getNext());
                    current.getNext().setPrevious(current.getPrevious());
                }

                --this.count;
                ++this.modCount;
                return current.getElement();
            }
        }
    }

    public T first() {
        return this.isEmpty() ? null : this.head.getElement();
    }

    public T last() {
        return this.isEmpty() ? null : this.tail.getElement();
    }

    public boolean contains(T target) {
        if (this.isEmpty()) {
            return false;
        } else {
            for (DoubleNode<T> current = this.head; current != null; current = current.getNext()) {
                if (target.equals(current.getElement())) {
                    return true;
                }
            }

            return false;
        }
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    public int size() {
        return this.count;
    }

    @Override
    public Iterator<T> iterator() {

        return new DoubleIterator<T>(this.head,this.count);

    }

    public String toString() {
        String result = "DoublyLinkedList { ";

        for (DoubleNode<T> current = this.head; current != null; current = current.getNext()) {
            result = result + String.valueOf(current.getElement()) + " ";
        }

        return result + "}";
    }
    public DoubleNode<T> getHead() {
        return this.head;
    }

    private class MyIterator<T> implements Iterator<T> {
        private int current = 0;
        private int expectedModCount;
        private boolean okToRemove = false;
        private DoubleNode<T> currentElement;

        public MyIterator() {
            this.current = 0;
            this.expectedModCount = modCount;
            this.currentElement = (DoubleNode<T>) head;
            this.okToRemove = false;
        }

        public boolean hasNext() {
            return (current != size());
        }

        public T next() {
            if (! hasNext()) {
                throw new NoSuchElementException();
            }
            T result = currentElement.getElement();
            currentElement = currentElement.getNext();
            current++;
            this.okToRemove = true;

            return result;
        }

        public void remove() {
            throw new UnsupportedOperationException();

        }
    }
}
