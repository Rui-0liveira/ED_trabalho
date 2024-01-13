package ClassImplementation;
/**
 * @author 8210311 Daniela Moreira
 * @author 8210367 Orlando Pires
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoubleIterator<T> implements Iterator{
    private DoubleNode<T> current;
    private DoubleNode<T> previous;
    private int count;
    private int modCount;

    public DoubleIterator(DoubleNode<T> head, int count) {
        this.current = head;
        this.count = count;
        this.modCount = 0;
    }

    public boolean hasNext() {
        return this.count != 0;
    }

    public T next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        } else {
            T result = this.current.getElement();
            this.previous = this.current;
            this.current = this.current.getNext();
            --this.count;
            return result;
        }
    }

    public void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
