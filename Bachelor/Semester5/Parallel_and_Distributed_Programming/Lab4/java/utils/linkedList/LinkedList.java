package utils.linkedList;

import java.util.Iterator;

public class LinkedList<T extends Comparable<T>> {
    private Node<T> first;

    public synchronized void add(T element) {
        Node<T> node = new Node<>(element);
        if (first == null || first.getElement().compareTo(element) >= 0) {
            node.setNext(first);
            first = node;
            return;
        }

        Node<T> current = first;
        while (current.getNext() != null && current.getNext().getElement().compareTo(node.getElement()) < 0) {
            current = current.getNext();
        }

        node.setNext(current.getNext());
        current.setNext(node);
    }

    public synchronized Node<T> find(T element) {
        Node<T> current = first;
        while (current != null && element.compareTo(current.getElement()) > 0) {
            current = current.getNext();
        }

        if (current == null || !current.getElement().equals(element)) {
            return null;
        }
        return current;
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