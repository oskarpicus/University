package utils.linkedList;

import utils.interfaces.Updateable;

import java.util.Iterator;
import java.util.concurrent.Semaphore;

public class LinkedList<T extends Comparable<T> & Updateable<T>> {
    private Node<T> first;
    private final Semaphore semaphore = new Semaphore(1);

    public void add(T element) {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Node<T> node = new Node<>(element);
        if (first == null || first.getElement().compareTo(element) > 0) {
            node.setNext(first);
            first = node;
            semaphore.release();
            return;
        }

        if (first.getElement().compareTo(element) == 0) {
            first.getElement().update(element);
            semaphore.release();
            return;
        }

        boolean start = true;
        Node<T> elem = first, prev;
        elem.getLock().lock();
        while (elem.getNext() != null && elem.getNext().getElement().compareTo(node.getElement()) <= 0) {
            if (elem.getNext().getElement().equals(element)) {
                elem.getElement().update(element);
                elem.getLock().unlock();
                return;
            }

            prev = elem;
            elem = elem.getNext();
            elem.getLock().lock();
            prev.getLock().unlock();

            if (start) {
                semaphore.release();
                start = false;
            }
        }

        if (elem.getElement().equals(element)) {
            elem.getElement().update(element);
            elem.getLock().unlock();
            return;
        }

        if (start) {
            semaphore.release();
        }

        node.setNext(elem.getNext());
        elem.setNext(node);

        elem.getLock().unlock();
    }

    public Node<T> find(T element) {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Node<T> current = first;
        if (current != null) {
            current.getLock().lock();
        }

        boolean start = true;
        while (current != null && element.compareTo(current.getElement()) > 0) {
            Node<T> previous = current;
            current = current.getNext();

            if (current != null) {
                current.getLock().lock();
            }

            previous.getLock().unlock();

            if (start) {
                semaphore.release();
                start = false;
            }
        }

        if (start) {
            semaphore.release();
        }

        Node<T> result;
        if (current == null || !current.getElement().equals(element)) {
            result = null;
        } else {
            result = current;
        }

        if (current != null) {
            current.getLock().unlock();
        }
        return result;
    }

    public boolean delete(T val) {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (first == null) {  // empty list
            semaphore.release();
            return false;
        }

        boolean start = true;

        Node<T> prev = first;
        prev.getLock().lock();

        Node<T> elem = first.getNext();

        if (elem == null) {  // the list has size 1
            if (first.getElement().equals(val)) {
                first.getLock().unlock();
                semaphore.release();
                first = null;
                return true;
            }
            semaphore.release();
            return false;
        }

        elem.getLock().lock();

        while (elem.getNext() != null && elem.getElement().compareTo(val) <= 0) {
            if (elem.getElement().equals(val)) {
                prev.setNext(elem.getNext());
                elem.getLock().unlock();
                prev.getLock().unlock();

                if (start) {
                    semaphore.release();
                }

                return true;
            }

            prev.getLock().unlock();
            prev = elem;
            elem = elem.getNext();
            elem.getLock().lock();

            if (start) {
                semaphore.release();
                start = false;
            }
        }

        if (start) {
            semaphore.release();
        }

        if (elem.getElement().equals(val)) {  // tail
            prev.setNext(elem.getNext());
            elem.getLock().unlock();
            prev.getLock().unlock();
            return true;
        }

        elem.getLock().unlock();
        prev.getLock().unlock();
        return false;

    }

    public Iterator<Node<T>> getIterator() {
        return new MyIterator();
    }

    @Override
    public String toString() {
        Node<T> current = first;
        if (current == null) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        while (current != null) {
            builder.append(current.getElement())
                    .append("\n");
            current = current.getNext();
        }

        return builder.toString();
    }

    private class MyIterator implements Iterator<Node<T>> {
        private Node<T> current;

        public MyIterator() {
            current = LinkedList.this.first;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Node<T> next() {
            Node<T> previous = current;
            current = current.getNext();
            return previous;
        }
    }
}