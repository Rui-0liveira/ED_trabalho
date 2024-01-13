package ClassImplementation;
/**
 * @author 8210311 Daniela Moreira
 * @author 8210367 Orlando Pires
 */
public class LinkedList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public LinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (this.head == null) {
            this.head = newNode;
            this.tail = newNode;
        } else {
            this.tail.setNext(newNode);
            this.tail = newNode;
        }
        ++this.size;
    }

    public void add(int index, T data) {
        if (index >= 0 && index <= this.size) {
            Node<T> newNode = new Node(data);
            if (index == 0) {
                newNode.setNext(this.head);
                this.head = newNode;
            } else if (index == this.size) {
                this.tail.setNext(newNode);
                this.tail = newNode;
            } else {
                Node<T> prev = this.head;
                for(int i = 0; i < index - 1; ++i) {
                    prev = prev.getNext();
                }
                newNode.setNext(prev.getNext());
                prev.setNext(newNode);
            }
            ++this.size;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public T get(int index) {
        if (index >= 0 && index < this.size) {
            Node<T> current = this.head;
            for(int i = 0; i < index; ++i) {
                current = current.getNext();
            }
            return current.getElement();
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public void remove(int index) {
        if (index >= 0 && index < this.size) {
            if (index == 0) {
                this.head = this.head.getNext();
            } else {
                Node<T> prev = this.head;
                for(int i = 0; i < index - 1; ++i) {
                    prev = prev.getNext();
                }
                prev.setNext(prev.getNext().getNext());
            }
            --this.size;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public void remove(T data) {
        if (this.head != null) {
            if (this.head.getElement().equals(data)) {
                this.head = this.head.getNext();
                --this.size;
            } else {
                Node<T> prev = this.head;
                for(Node<T> current = this.head.getNext(); current != null; current = current.getNext()) {
                    if (current.getElement().equals(data)) {
                        prev.setNext(current.getNext());
                        --this.size;
                        return;
                    }
                    prev = current;
                }
            }
        }
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public Node<T> getFront() {
        return head;
    }

    public Node<T> getRear() {
        return tail;
    }

    public void clear() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public String toString() {
        String result = "";

        for(Node<T> current = this.head; current != null; current = current.getNext()) {
            result = result + current.getElement() + " ";
        }
        return result;
    }
}