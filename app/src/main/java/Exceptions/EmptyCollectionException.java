package Exceptions;

/**
 * @author 8210191 Rodrigo Lopes
 * @author 8210322 Rui Oliveira
 */
public class EmptyCollectionException extends RuntimeException{

    public EmptyCollectionException (String collection)
    {
        super ("The " + collection + " is empty.");
    }
}
