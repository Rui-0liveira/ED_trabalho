package Exceptions;

/**
 * @author 8210311 Daniela Moreira
 * @author 8210367 Orlando Pires
 */
public class EmptyCollectionException extends RuntimeException{

    public EmptyCollectionException (String collection)
    {
        super ("The " + collection + " is empty.");
    }
}
