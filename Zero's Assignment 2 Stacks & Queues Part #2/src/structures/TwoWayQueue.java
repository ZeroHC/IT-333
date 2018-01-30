package structures;

import exceptions.EmptyQueueException;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

/*
 * Hanchen (Zero) Liu
 * 01/29/2018
 * TwoWayQueue.java
 * This is a custom two way queue class,
 * that is built to support different types of elements
 */

/**
 * This is a custom two way queue class,
 * that is built to support different types of elements
 *
 * @author Hanchen (Zero) Liu
 * @version 1.0
 *
 * @param <T> generic type
 */
public class TwoWayQueue<T> implements ITwoWayQueue<T>
{
    //create a private doubly linked node class
    private class Node
    {
        private T data;
        private Node next;
        private Node prev;

        private Node(T data, Node prev, Node next)
        {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    private Node head = null;
    private Node tail = null;
    private int queueSize = 0;
    private int modCount = 0;


    /**
     * Removes and returns the first element in the queue.
     *
     * @throws exceptions.EmptyQueueException if the queue
     * is empty and dequeueFirst() is called
     * @return the first element
     */
    @Override
    public T dequeueFirst()
    {
        //if the queue is empty, throw a new EmptyQueueException
        if (isEmpty()) throw new EmptyQueueException("Can not dequeue from an empty queue");


        //else set the nodeToRemove to head
        //then set the new head to be the next node of the old head
        //then return the data from the nodeToRemove
        Node nodeToRemove = head;

        if (head.next == null)
        {
            head = null;
        }
        else
        {
            head = head.next;
            head.prev = null;
        }
        queueSize--;
        modCount++;
        return nodeToRemove.data;
    }

    /**
     * Removes and returns the last element in the queue.
     *
     * @throws exceptions.EmptyQueueException if the queue
     * is empty and dequeueLast() is called
     * @return the last element
     */
    @Override
    public T dequeueLast()
    {
        //if the queue is empty, throw a new EmptyQueueException
        if (isEmpty()) throw new EmptyQueueException("Can not dequeue from an empty queue");

        //else set the nodeToRemove to tail
        //then set the new tail to be the previous node of the old tail
        //then return the data from the nodeToRemove
        Node nodeToRemove = tail;

        if (tail.prev == null)
        {
            tail = null;
        }
        else
        {
            tail = tail.prev;
            tail.next = null;
        }
        queueSize--;
        modCount++;
        return nodeToRemove.data;
    }

    /**
     * Removes and returns all elements in the queue. The first
     * element in the queue should be located at the last index
     * of the resulting list (index size() - 1) and the last
     * element in the queue at index zero.
     *
     * @return a list of all elements in the queue
     */
    @Override
    public List<T> dequeueAll()
    {
        //if the queue is empty, throw a new EmptyQueueException
        if (isEmpty()) throw new EmptyQueueException("Can not dequeue from an empty queue");

        ArrayList<T> listToReturn = new ArrayList<>();

        int tempSize = queueSize;

        for (int i = 0; i < tempSize; i++)
        {
            listToReturn.add(dequeueLast());
        }

        clear();

        return listToReturn;
    }

    /**
     * Adds a new element to the front of the queue. The
     * queue should continually resize to make room for new
     * elements.
     *
     * @param element the new element
     */
    @Override
    public void enqueueFirst(T element)
    {
        //creates a tempNode to store the data that is going to be added
        Node tempNode = new Node(element, null, head);

        if(head != null )
        {
            head.prev = tempNode;
        }

        head = tempNode;

        if(tail == null)
        {
            tail = tempNode;
        }

        queueSize++;
        modCount++;
    }

    /**
     * Adds a new element to the end of the queue. The
     * queue should continually resize to make room for new
     * elements.
     *
     * @param element the new element
     */
    @Override
    public void enqueueLast(T element)
    {
        //creates a tempNode to store the data that is going to be added
        Node tempNode = new Node(element, tail, null);

        if(tail != null)
        {
            tail.next = tempNode;
        }

        tail = tempNode;

        if(head == null)
        {
            head = tempNode;
        }

        queueSize++;
        modCount++;
    }

    /**
     * Adds a group of elements to the front of the queue.
     *
     * @param elements an array of elements
     */
    @Override
    public void enqueueAllFirst(T[] elements)
    {
        for(T element : elements)
        {
            enqueueFirst(element);
        }
    }

    /**
     * Adds a group of elements to the end of the queue.
     *
     * @param elements an array of elements
     */
    @Override
    public void enqueueAllLast(T[] elements)
    {
        for(T element : elements)
        {
            enqueueLast(element);
        }
    }

    /**
     * Returns the number of elements in the queue.
     *
     * @return the number of elements
     */
    @Override
    public int size()
    {
        return queueSize;
    }

    /**
     * Reports whether the queue is empty or not.
     *
     * @return true if no elements are in the queue,
     * otherwise returns false
     */
    @Override
    public boolean isEmpty()
    {
        return head == null;
    }

    /**
     * Removes all elements from the queue.
     */
    @Override
    public void clear()
    {
        head = null;
        tail = null;
        queueSize = 0;
        modCount++;
    }

    /**
     * Returns an iterator over the elements of the
     * queue. It should not be possible to use the
     * iterator while making any changes to the two way queue
     * itself.
     *

     * The first element returned from the iterator should be
     * the front element of the queue (the first element
     * returned by dequeueLast()) and the last element
     * the ending element of the queue (the last element
     * returned by dequeueLast()).
     *
     * @return an object using the Iterator<T> interface
     */
    @Override
    public Iterator<T> iterator()
    {
        return new LinkedListIterator(tail, modCount);
    }

    //this creates an inner iterator class which allows users to treat the two way queue as iterator
    private class LinkedListIterator implements Iterator<T>
    {
        private Node linkedList;
        private int savedModCount;

        private LinkedListIterator(Node linkedList, int savedModCount)
        {
            this.linkedList = linkedList;
            this.savedModCount = savedModCount;
        }

        //this method prevents user from trying to change the two way queue using an iterator
        private void checkConcurrentChanges()
        {
            //see if the saved mod count is different from
            //the current mod count in the two way queue
            if (savedModCount != TwoWayQueue.this.modCount)
            {
                throw new ConcurrentModificationException("You cannot alter the two way queue while using an iterator");
            }
        }

        /**
         * this method checks if it is the end of the two way queue
         * @return if it is the end of stack
         */
        @Override
        public boolean hasNext()
        {
            checkConcurrentChanges();

            return linkedList != null;
        }

        /**
         * this method retrieves the current element in a stack
         * @return the current element in a two way queue
         */
        @Override
        public T next()
        {
            if (!hasNext())
            {
                throw new RuntimeException("Call hasNext() before next()");
            }

            Node tempNode = linkedList;
            T currentElement = tempNode.data;

            linkedList = linkedList.prev;

            return currentElement;
        }
    }
}