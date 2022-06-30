package data_structures.linked_list;

public class LinkedList<T> {
    private Node<T> first;

    public void add(T element) {
        Node<T> node = new Node<>(element);
        if (first == null) {
            first = node;
            return;
        }
        Node<T> lastElement = first;
        while (lastElement.getNext() != null) {
            lastElement = lastElement.getNext();
        }

        lastElement.setNext(node);
    }

    public boolean find(T element) {
        Node<T> current = first;
        while (current != null && !current.getElement().equals(element)) {
            current = current.getNext();
        }

        return current != null;
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
}
