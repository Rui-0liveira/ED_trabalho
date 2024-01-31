package Exceptions;

/**
 * @author 8210191 Rodrigo Lopes
 * @author 8210322 Rui Oliveira
 */
public class ConcurrentModificationException extends Throwable {
    public ConcurrentModificationException() {
    }

    public ConcurrentModificationException(String message) {
        super(message);
    }
}