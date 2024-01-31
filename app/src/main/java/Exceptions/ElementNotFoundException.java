package Exceptions;

/**
 * @author 8210191 Rodrigo Lopes
 * @author 8210322 Rui Oliveira
 */
public class ElementNotFoundException extends RuntimeException {

    public ElementNotFoundException (String collection){
        super ("The target element is not in this " + collection);
    }
}
