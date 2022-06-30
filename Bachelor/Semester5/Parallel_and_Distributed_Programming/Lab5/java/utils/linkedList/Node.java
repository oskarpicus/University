package utils.linkedList;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Node<T> {
    private T element;
    private Node<T> next;
    private final Lock lock = new ReentrantLock();

    public Node(T element) {
        this.element = element;
    }

    public T getElement() {
        return element;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public Lock getLock() {
        return lock;
    }
}
