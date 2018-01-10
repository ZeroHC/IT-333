package structures;

import exceptions.IteratorMisuseException;

import java.util.Iterator;

public class Bag<T> implements Iterable<T>
{
    private T[] data;
    private int size;

    public Bag(int size)
    {
        data = (T[])new Object[size];
    }

    public boolean add(T element)
    {
        if (isFull())
        {
            return false;
        }

        data[size++] = element;
        return true;

    }

    //removes the first occurrence of the element
    public boolean remove(T element)
    {
        int foundIndex = find(element);

        if (foundIndex != -1)
        {
            //shift elements into the open space of the array
            for (int i = foundIndex + 1; i < size; i++)
            {
                data[i - 1] = data[i];
            }
            //decrement our size and clear the removed element
            size--;
            data[size] = null;
        }

        return false;
    }

    public boolean contains(T element)
    {
        return find(element) != -1;
    }

    //returns the index where element was found, otherwise return -1
    private int find(T element)
    {
        //search for my element - should this be contains()?
        for(int i = 0; i < size; i++)
        {
            if (data[i] != null && data[i].equals(element))
            {
                return i;//found it
            }
        }

        //not found
        return -1;
    }

    public int size()
    {
        return size;
    }

    public boolean isFull()
    {
        return size >= data.length;
    }

    @Override
    public Iterator<T> iterator()
    {
        return new BagIterator(data);
    }

    private class BagIterator implements Iterator<T>
    {
        //data from the outer class
        private T[] data;
        private int position;

        public BagIterator(T[] data)
        {
            this.data = data;
        }

        @Override
        public boolean hasNext()
        {
            //is this a valid index with an object
            return position < data.length && data[position] != null;
        }

        @Override
        public T next()
        {
            //make sure we have a next element (sanity check)
            if (!hasNext())
            {
                throw new IteratorMisuseException("call hasNext() before next()");
            }

            //save our result
            T currentElement = data[position];

            position++;

            return currentElement;
        }
    }
}
