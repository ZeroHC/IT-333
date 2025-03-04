package structures;

import exceptions.StackUnderflowException;

import java.util.*;

/*
 * Hanchen (Zero) Liu
 * 01/16/2018
 * Stack.java
 * This is a custom stack class,
 * that is built to support different types of elements
 */

/**
 * This is a custom stack class,
 * that is built to support different types of elements
 *
 * @author Hanchen (Zero) Liu
 * @version 1.0
 *
 * @param <T> generic type
 */
public class Stack<T> implements IStack<T>
{
    private static final int INITIAL_SIZE = 10;
    private T[] data = (T[])new Object[INITIAL_SIZE];
    private int lastIndex = 0;
    private int modCount = 0;

    /**
     * Adds a new element to the top of the stack.
     * The stack should grow to accommodate any number
     * of elements.
     *
     * @param element the new elements
     */
    @Override
    public void push(T element)
    {
        data[lastIndex] = element;
        lastIndex++;
        modCount++;
    }

    /**
     * Adds several elements to the top of the stack.
     * Elements are added starting with lower indices.
     * (i.e. the first element added should be index
     * zero and the last element added at index
     * length - 1). The stack should grow to accommodate
     * any number of elements.
     *
     * @param elements an array of elements
     */
    @Override
    public void pushAll(T[] elements)
    {
        if (data.length < elements.length)
        {
           increaseArraySize(elements.length);
        }

        int initialIndex = lastIndex;
        for (int i = 0; i < elements.length; i++)
        {
            data[initialIndex + i] = elements[i];
            lastIndex++;
        }

        modCount++;
    }

    //this method increases the size of the array
    private void increaseArraySize(int increment)
    {
        T[] temp = (T[])new Object[INITIAL_SIZE + increment];

        for (int i = 0; i < INITIAL_SIZE; i++){
            temp[i] = data[i];
        }
        data = temp;
    }

    /**
     * Removes and returns the top element of the stack.
     *
     * @return the top element of the stack
     */
    @Override
    public T pop()
    {
        exceptionChecker();

        T lastElement = data[lastIndex - 1];

        data[lastIndex - 1] = null;

        lastIndex--;

        modCount++;

        return lastElement;
    }

    /**
     * Removes and returns all elements of the stack as
     * a List<T> object. The elements should be returned
     * in the same order as they are located in the stack
     * itself (i.e. the element at index zero should be
     * the bottom of the stack and the element at index
     * size() - 1 should be the top of the stack)
     *
     * @throws exceptions.StackUnderflowException thrown when the
     * stack is empty and popAll() is called
     * @return a list of stack elements
     */
    @Override
    public List<T> popAll()
    {
        exceptionChecker();

        ArrayList<T> tempList = new ArrayList<>();

        int size = size();

        for (int i = 0; i < size; i++)
        {
            tempList.add(data[i]);
        }

        clear();

        lastIndex = 0;

        modCount++;

        return tempList;
    }

    //this method checks if the stack is empty, and throws exception accordingly
    private void exceptionChecker()
    {
        if (isEmpty())
        {
            throw new StackUnderflowException("Cannot retrieve the last element because the stack is empty!");
        }
    }

    /**
     * Returns the number of elements in the stack.
     *
     * @return the number of elements
     */
    @Override
    public int size()
    {
        return lastIndex;
    }

    /**
     * Reports if the stack is empty.
     *
     * @return true if the stack has no elements, otherwise
     * false
     */
    @Override
    public boolean isEmpty()
    {
        return lastIndex == 0;
    }

    /**
     * Returns an iterator over the elements in the stack.
     * It should not be possible to use the iterator while
     * making any changes to the stack itself.
     * @return an object using the Iterator<T> interface
     */
    @Override
    public Iterator<T> iterator()
    {
        return new StackIterator(data, modCount);
    }

    //this creates an inner iterator class which allows users to treat the stack as iterator
    private class StackIterator implements Iterator<T>
    {
        private T[] data;
        private int position;
        private int savedModCount;

        private StackIterator(T[] data, int savedModCount)
        {
            this.data = data;
            this.savedModCount = savedModCount;
        }

        //this method prevents user from trying to change the stack using an iterator
        private void checkConcurrentChanges()
        {
            //see if the saved mod count is different from
            //the current mod count in the stack
            if (savedModCount != Stack.this.modCount)
            {
                throw new ConcurrentModificationException("You cannot alter the stack while using an iterator");
            }
        }

        /**
         * this method checks if it is the end of the stack
         * @return if it is the end of stack
         */
        @Override
        public boolean hasNext()
        {
            checkConcurrentChanges();

            return position < data.length && data[position] != null;
        }

        /**
         * this method retrieves the current element in a stack
         * @return the current element in a stack
         */
        @Override
        public T next()
        {
            if (!hasNext())
            {
                throw new RuntimeException("Call hasNext() before next()");
            }

            T currentElement = data[position];

            position++;

            return currentElement;
        }
    }

    /**
     * Removes all elements from the stack.
     */
    @Override
    public void clear()
    {
        data = (T[])new Object[INITIAL_SIZE ];
        lastIndex = 0;
    }
}
