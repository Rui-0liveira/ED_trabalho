package Interfaces;
/**
 * @author 8210191 Rodrigo Lopes
 * @author 8210322 Rui Oliveira
 */
import Exceptions.ConcurrentModificationException;
import Exceptions.EmptyCollectionException;
import Exceptions.NoSuchElementException;

public interface IteratorADT<T> {
    T next() throws ConcurrentModificationException, NoSuchElementException;

    boolean hasNext();

    void remove() throws ConcurrentModificationException, NoSuchElementException, UnsupportedOperationException, EmptyCollectionException;
}
