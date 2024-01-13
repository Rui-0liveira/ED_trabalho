package ClassImplementation;
/**
 * @author 8210311 Daniela Moreira
 * @author 8210367 Orlando Pires
 */
public class DoubleNode<T>{
    private T element;
    private DoubleNode<T> next;
    private DoubleNode<T> previous;

    public DoubleNode(){
        this.next = null;
        this.element = null;
        this.previous = null;
    }

    public DoubleNode (T element){
        this.next = null;
        this.element = element;
        this.previous = null;
    }

    public DoubleNode(T data, DoubleNode<T> previous, DoubleNode<T> next) {
        this.element = data;
        this.next = next;
        this.previous = previous;
    }

    public T getElement() {
        return this.element;
    }

    public void setElement (T elem) {
        this.element = elem;
    }

    public DoubleNode<T> getNext() {
        return this.next;
    }

    public void setNext (DoubleNode<T> dnode){
        this.next = dnode;
    }

    public DoubleNode<T> getPrevious() {
        return this.previous;
    }

    public void setPrevious (DoubleNode<T> dnode) {
        this.previous = dnode;
    }










}


