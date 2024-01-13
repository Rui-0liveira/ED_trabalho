package ListsDavid;
import Exceptions.EmptyCollectionException;
import Interfaces.StackADT;

/**
 * 
 * @author David Francisco (8210088)
 * @author Guilherme Silva (8210190)
 */
public class Stack<T> implements StackADT<T> {
    private final int DEFAULT_CAPACITY = 100;
    private int top;
    private T[] list;

    public Stack() {
        top = 0;
        list = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    public Stack(int initialCapacity) {
        top = 0;
        list = (T[]) (new Object[initialCapacity]);
    }

    @Override
    public void push(T element) {
        if(top == list.length) {
            expandCapacity();
        }
        list[top] = element;
        top++;
    }

    @Override
    public T pop() {
        if(isEmpty()) {
            throw new EmptyCollectionException("Linked List");
        }
        T result = list[top - 1];
        top--;
        return result;
    }

    @Override
    public T peek() {
        if(isEmpty()) {
            throw new EmptyCollectionException("Linked List");
        }
        return list[top - 1];
    }

    @Override
    public boolean isEmpty() {
        return top == 0;
    }

    @Override
    public int size() {
        return top;
    }
    
    public void expandCapacity() {
        T[] larger = (T[]) (new Object[list.length * 2]);
        for(int i = 0; i < list.length; i++) {
            larger[i] = list[i];
        }
        list = larger;
    }
}
