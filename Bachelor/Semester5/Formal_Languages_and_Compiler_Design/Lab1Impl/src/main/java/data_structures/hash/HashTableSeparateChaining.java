package data_structures.hash;

import data_structures.linked_list.LinkedList;

public class HashTableSeparateChaining<T> implements HashTable<T> {
    private static final int INITIAL_CAPACITY = 13;
    private final int capacity;
    // will contain elements of type LinkedList<T>
    private final Object[] array;

    public HashTableSeparateChaining() {
        array = new Object[INITIAL_CAPACITY];
        capacity = INITIAL_CAPACITY;
    }

    @Override
    public int add(T element) {
        int bucket = d(element);
        @SuppressWarnings("unchecked")
        LinkedList<T> current = (LinkedList<T>) array[bucket];
        if (current == null) {
            current = new LinkedList<>();
            array[bucket] = current;
        }

        current.add(element);
        return bucket;
    }

    @Override
    public int find(T element) {
        int bucket = d(element);
        @SuppressWarnings("unchecked")
        LinkedList<T> current = (LinkedList<T>) array[bucket];
        return current != null && current.find(element) ? bucket : -1;
    }

    private int d(T element) {
        int remainder = element.hashCode() % capacity;
        return remainder < 0 ? remainder + capacity : remainder;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                @SuppressWarnings("unchecked")
                LinkedList<T> current = (LinkedList<T>) array[i];
                result.append(current.toString());
            }
        }
        return result.toString();
    }
}
