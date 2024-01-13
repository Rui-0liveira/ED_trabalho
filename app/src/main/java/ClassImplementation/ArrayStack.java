package ClassImplementation;
/**
 * @author 8210311 Daniela Moreira
 * @author 8210367 Orlando Pires
 */
import Interfaces.StackADT;
import Exceptions.EmptyCollectionException;

public class ArrayStack<T> implements StackADT<T> {

    private final int DEFAULT_CAPACITY = 100;
    private int top=0;
    private T[] stack;

    public ArrayStack() {
        top = 0;
        stack = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    public ArrayStack(int initialCapacity) {
        stack = (T[]) (new Object[initialCapacity]);
    }

    public void push(T element) {
        if (this.size() == stack.length) {
            this.expandCapacity();
        }

        this.stack[this.top++] = element;
    }

    private void expandCapacity() {
        T[] larger = (T[]) new Object[this.stack.length * 2];

        for (int i = 0; i < stack.length; i++) {
            larger[i] = stack[i];
        }
        this.stack = larger;
    }

    public T pop() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Stack");
        }

        T result = this.stack[--this.top];
        this.stack[this.top] = null;
        return result;
    }

    public T peek() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Stack");
        }
        return this.stack[this.top - 1];
    }

    @Override
    public boolean isEmpty() {

        return (this.top == 0);
    }

    @Override
    public int size() {

        return this.top;
    }

    @Override
    public String toString() {
        String result = "ArrayStack {";

        for (int i = 0; i < this.top; i++) {
            result = result + " " + this.stack[i];
        }
        result = result + " }";
        return result;
    }

}

