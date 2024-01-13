package Exceptions;

/**
 * @author 8210311 Daniela Moreira
 * @author 8210367 Orlando Pires
 */
public class ElementNotFoundException extends RuntimeException {

    public ElementNotFoundException (String collection){
        super ("The target element is not in this " + collection);
    }
}
