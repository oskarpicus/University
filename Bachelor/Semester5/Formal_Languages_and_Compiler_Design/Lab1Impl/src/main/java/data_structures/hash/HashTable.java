package data_structures.hash;

public interface HashTable<T> {

    /**
     * Method for adding an entry in the hash table
     * @param element: T, the element to add
     * @return the code generated after the insertion
     */
    int add(T element);

    /**
     * Method for searching for an entry in the hash table
     * @param element: T, the element to search
     * @return the code of the respective element in the hash table or -1 if it does not exist
     */
    int find(T element);
}
