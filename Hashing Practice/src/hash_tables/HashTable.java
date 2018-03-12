package hash_tables;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class HashTable<T>
{
    private static final int INITIAL_TABLE_SIZE = 10;
    private static final double MAX_LOAD_FACTOR = 0.6;
    private static final double RESIZE_RATE = 2.0;

    private HashTableElement[] table;
    private int size;
    private int usedSpace;
    private int modCount = 0;

    public HashTable()
    {
        table = new HashTableElement[INITIAL_TABLE_SIZE];
    }

    public boolean add(T element)
    {
        //have we exceeded our load factor
        if (exceededLoadFactor())
        {
            //System.out.println("Resize");
            rehash();
        }

        int code = element.hashCode();
        int index = code % table.length;

        //is there a collision
        /*if (table[index] != null)
        {
            System.out.println("Collision");
        }*/

        //check for collisions
        while (table[index] != null)
        {
            //stop if the element is already in the table
            if (table[index].data.equals(element))
            {

                //there is a duplicate
                if (table[index].isActive)
                {
                    return false;
                }

                //the element is here, but was previously deleted
                else
                {
                    //add the new element at this position
                    table[index].data = element;
                    table[index].isActive = true;
                    size++;
                    modCount++;
                    return true;
                }
            }

            //otherwise move to the next element
            index = index + 1;
            index = index % table.length;
        }

        size++;
        usedSpace++;
        modCount++;
        table[index] = new HashTableElement(element, true);
        return true;
    }

    private boolean exceededLoadFactor()
    {
        return (double) size / table.length >= MAX_LOAD_FACTOR;
    }

    private void rehash()
    {
        //save the data
        HashTableElement[] oldTable = table;

        //set our size and usedSpace to zero
        //so that the table can start a new
        size = 0;
        usedSpace = 0;

        //reassign our table array
        table = new HashTableElement[(int)(oldTable.length * RESIZE_RATE)];

        //loop over each element that is active and add() it
        for (HashTableElement element : oldTable)
        {
            if (element != null && element.isActive)
            {
                add((T)element);
            }
        }
    }

    public void printUsedSpace()
    {
        for (int i = 0; i < table.length; i++)
        {
            if (table[i] == null)
            {
                System.out.print("_");
            }
            else if (table[i].isActive)
            {
                System.out.print("A");
            }
            else
            {
                System.out.print("X");
            }
        }
    }

    public boolean contains(T element)
    {
        int index = find(element);

        if (index != -1)
        {
            return table[index].isActive;
        }

        return false;
    }

    public boolean remove(T element)
    {
        int index = find(element);

        if (index != -1)
        {
            if (table[index].isActive)
            {
                table[index].isActive = false;
                size--;
                modCount++;
                return true;
            }
        }

        return false;
    }

    //returns the index at which the element was found
    //regardless of whether it is active or not
    private int find(T element)
    {
        int code = element.hashCode();
        int index = code % table.length;

        while (table[index] != null)
        {
            if (table[index].data.equals(element))
            {
                if (table[index].isActive)
                {
                    return index;
                }
                else
                {
                    return index;
                }
            }

            index = (index + 1) % table.length;
        }

        return -1;
    }

    public int size()
    {
        return size;
    }

    public boolean isEmpty()
    {
        return size == 0;
    }

    public void clear()
    {
        size = 0;
        usedSpace = 0;
        table = new HashTableElement[INITIAL_TABLE_SIZE];
    }

    private class HashTableElement<T>
    {
        private T data;
        private boolean isActive;

        private HashTableElement(T data, boolean isActive)
        {
            this.data = data;
            this.isActive = isActive;
        }

        public String toString()
        {
            if (!isActive)
            {
                return "Inactive";
            }
            else
            {
                return data.toString();
            }
        }
    }

    public Iterator<T> iterator()
    {
        return new HashTableIterator(table);
    }

    private class HashTableIterator implements Iterator<T>
    {
        //data from the outer class
        private HashTableElement[] table;
        private int nextIndex = -1;

        public HashTableIterator(HashTableElement[] table)
        {
            this.table = table;
            findNext();
        }

        @Override
        public boolean hasNext()
        {
            return nextIndex != -1;
        }

        @Override
        public T next()
        {
            Object nextElement = table[nextIndex].data;
            findNext();
            return (T)nextElement;
        }

        private void findNext()
        {
            for (int i = nextIndex + 1; i < table.length; i++)
            {
                if (table[i] != null && table[i].isActive)
                {
                    nextIndex = i;
                    return;
                }
            }

            nextIndex = -1;
        }
    }
}