package Exceptions;

/**
 * @author 8210191 Rodrigo Lopes
 * @author 8210322 Rui Oliveira
 */
public class EmptyStackException extends RuntimeException{

    public EmptyStackException(){
        super ("The stack is empty.");
    }

    public EmptyStackException (String message){
        super (message);
    }
}