package Interfaces;

/**
 * @author 8210191 Rodrigo Lopes
 * @author 8210322 Rui Oliveira
 */

import Exceptions.NoSuchElementException;

public interface UnorderedListADT<T> extends ListADT<T> {
    //  Adds the specified element to the front of this list
    void addToFront (T element);

    //  Adds the specified element to the rear of this list
    void addToRear (T element);

    //  Adds the specified element after the specified target
    void addAfter (T element, T target) throws NoSuchElementException;

}
