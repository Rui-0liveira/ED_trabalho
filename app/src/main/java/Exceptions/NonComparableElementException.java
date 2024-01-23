package Exceptions;

/**
 * @author 8210191 Rodrigo Lopes
 * @author 8210322 Rui Oliveira
 */
public class NonComparableElementException extends Throwable {
    public NonComparableElementException() {
    }

    public NonComparableElementException(String message) {
        super(message);
    }
}
