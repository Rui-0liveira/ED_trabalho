package Exceptions;

/**
 * @author 8210311 Daniela Moreira
 * @author 8210367 Orlando Pires
 */
public class EmptyStackException extends RuntimeException{

    public EmptyStackException(){
        super ("The stack is empty.");
    }

    public EmptyStackException (String message){
        super (message);
    }
}