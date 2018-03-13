package hashing;

import interfaces.ICollection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Hanchen (Zero) Liu
 * 03/12/2018
 * HashTable.java
 * This is a custom hash table class,
 * that is built to support different types of elements
 */

/**
 * This is a custom hash table class,
 * that is built to support different types of elements
 *
 * @author Hanchen (Zero) Liu
 * @version 1.0
 *
 * @param <T> generic type
 */
public class HashTable<T> implements ICollection<T>
{
    //initial table size
    private static final int INITIAL_TABLE_SIZE = 10;

    //max load factor
    private static final double MAX_LOAD_FACTOR = 2.5;

    //0.5 means shrinking the table size, so it should be 1.5
    private static final double RESIZE_RATE = 1.5;

    //private fields
    private Node[] table;
    private int size;
    private int modCount;

    /**
     * Initialize a HashTable
     */
    public HashTable()
    {
        table = new Node[INITIAL_TABLE_SIZE];
        size = 0;
    }

    /**
     * Adds an element to the collection. No specific ordering
     * is required. After the load factor has exceeded 250% the
     * table should rehash all elements into a table that is 50%
     * larger than the previous table size.
     *
     * @param element the new element to put in the collection
     */
    @Override
    public void add(T element)
    {
        Node tempNode1, tempNode2;

        tempNode1 = new Node(element, null);

        //have we exceeded our load factor
        if (exceededLoadFactor())
        {
            rehash();
        }

        int code = element.hashCode();
        int index = code % table.length;

        //if it is null at index, assign the new node to this location
        if (table[index] == null)
        {
            table[index] = tempNode1;
        }

        else
        {
            //traverse
            for (tempNode2 = table[index]; tempNode2.next != null; tempNode2 = tempNode2.next){}

            tempNode2.next = tempNode1;
        }

        size++;
        modCount++;
    }

    /**
     * Finds and removes an element from the collection.
     *
     * @param element the element to remove
     * @throws java.util.NoSuchElementException thrown when the
     * element is not found in the collection
     */
    @Override
    public void remove(T element)
    {
        int index = find(element);

        //throw exception when no element found in the hash table
        if (index == -1)
        {
            throw new NoSuchElementException("Element not found");
        }

        Node head = table[index];

        Node temp = head;
        Node prev = null;

        // If head node itself holds the key to be deleted
        if (temp != null && temp.data.equals(element))
        {
            head = temp.next; // Changed head
            return;
        }

        // Search for the key to be deleted, keep track of the
        // previous node as we need to change temp.next
        while (temp != null && !temp.data.equals(element))
        {
            prev = temp;
            temp = temp.next;
        }

        // If key was not present in linked list
        if (temp == null) return;

        // Unlink the node from linked list
        prev.next = temp.next;

        size--;
        modCount++;
    }

    /**
     * Reports whether the collection contains an element.
     *
     * @param element the element to search for.
     * @return true if the element is found, otherwise false
     */
    @Override
    public boolean contains(T element)
    {
        return find(element) != -1;
    }

    /**
     * Returns the number of elements in the collection.
     *
     * @return the number of elements
     */
    @Override
    public int size()
    {
        return size;
    }

    /**
     * Reports whether the collection is empty or not.
     *
     * @return true if the collection is empty, otherwise false
     */
    @Override
    public boolean isEmpty()
    {
        return size == 0;
    }

    /**
     * Removes all elements from the collection.
     */
    @Override
    public void clear()
    {
        size = 0;
        modCount++;
        table = new Node[INITIAL_TABLE_SIZE];
    }

    /**
     * Returns an element in the collection that matches the
     * input parameter according the equals() method of the
     * parameter.
     *
     * @param element an element to search for
     * @return a matching element
     */
    @Override
    public T get(T element)
    {
        int index = find(element);

        if (index != -1)
        {
            Node tempNode = table[index];

            if (tempNode.data.equals(element))
            {
                return (T) table[index].data;
            }

            while (tempNode != null)
            {
                if (tempNode.data.equals(element)) return (T) tempNode.data;
                tempNode = tempNode.next;
            }
        }

        return null;
    }

    //check if the table exceeded the load factor
    private boolean exceededLoadFactor()
    {
        return (double) size / table.length >= MAX_LOAD_FACTOR;
    }

    //rehash all the elements in the table
    //and add them back to the resized table
    private void rehash()
    {
        //save the data
        Node[] oldTable = table;

        //set our size and usedSpace to zero
        //so that the table can start a new
        size = 0;

        //reassign our table array
        table = new Node[(int)(oldTable.length * RESIZE_RATE)];

        //loop over each element that is active and add() it
        for (Node node : oldTable)
        {
            if (node != null)
            {
                add((T)node.data);
            }
        }
    }

    //returns the index at which the element was found
    //regardless of whether it is active or not
    private int find(T element)
    {
        int code = element.hashCode();
        int index = code % table.length;

        Node tempNode = table[index];

        if (tempNode.data.equals(element)) return index;

        while (tempNode != null)
        {
            if (tempNode.data.equals(element)) return index;
            tempNode = tempNode.next;
        }

        return -1;
    }

    /**
     * Returns an iterator over the collection.
     *
     * @return an object using the Iterator<T> interface
     */
    @Override
    public Iterator<T> iterator()
    {
        return new HashTableIterator(table, modCount);
    }

    //create a private iterator class
    private class HashTableIterator<T> implements Iterator<T>
    {
        private Node[] table;
        private Node nextNode;
        private int savedModCount;

        //constructor for initializing an iterator
        private HashTableIterator(Node[] table, int savedModCount)
        {
            this.table = table;
            this.savedModCount = savedModCount;

            for (int i = 0; i < table.length; i++)
            {
                if (table[i] != null)
                {
                    nextNode = table[i];
                    break;
                }
            }
        }

        //this method prevents user from trying to change the two way queue using an iterator
        private void checkConcurrentChanges()
        {
            //see if the saved mod count is different from
            //the current mod count in the two way queue
            if (savedModCount != HashTable.this.modCount)
            {
                throw new ConcurrentModificationException("You cannot alter the hash table while using an iterator");
            }
        }

        /**
         * this method checks if it is the last element of hash table
         * @return if it is the last element
         */
        @Override
        public boolean hasNext()
        {
            checkConcurrentChanges();
            return nextNode != null;
        }

        /**
         * this method retrieves the current element in the hash table
         * @return the current element in a the hash table
         */
        @Override
        public T next()
        {
            Object nextElement = nextNode.data;
            findNextNode();
            return (T) nextElement;
        }

        //finds next available element
        private void findNextNode()
        {
            checkConcurrentChanges();

            if (nextNode.next != null)
            {
                nextNode = nextNode.next;
            }

            int index = nextNode.data.hashCode() % table.length;

            for (int i = index + 1; i < table.length; i++)
            {
                if (table[i] != null)
                {
                    nextNode = table[i];
                    return;
                }
            }

            nextNode = null;
        }
    }

    //create a private node class
    private class Node<T>
    {
        private T data;
        private Node next;

        //constructors for initializing a node
        private Node(T data)
        {
            this.data = data;
        }

        private Node(T data, Node next)
        {
            this.data = data;
            this.next = next;
        }

        /**
         * toString method for a node
         * @return string of the node's data
         */
        public String toString()
        {
            return data.toString();
        }
    }
}
