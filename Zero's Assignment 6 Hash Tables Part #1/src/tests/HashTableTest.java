package tests;

import hashing.HashTable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ConcurrentModificationException;

/*
 * Hanchen (Zero) Liu
 * 03/12/2018
 * HashTableTest.java
 * This is a custom test class,
 * that is built to test a custom hash table
 */

/**
 * This is a custom test class,
 * that is built to test a custom hash table
 *
 * @author Hanchen (Zero) Liu
 * @version 1.0
 */
public class HashTableTest
{
    private HashTable hashTable;

    /**
     * this method setup a test environment
     */
    @Before
    public void setup()
    {
        hashTable = new HashTable();
    }

    /**
     * this method test the add() from HashTable.java
     */
    @Test
    public void testAdd()
    {
        hashTable.add(1);
        hashTable.add(2);
        hashTable.add(3);
        hashTable.add(4);
        hashTable.add(5);
        hashTable.add(33);

        Assert.assertEquals("add() failed",6, hashTable.size());
    }

    /**
     * this method test the remove() from HashTable.java
     */
    @Test
    public void testRemove()
    {
        hashTable.add(1);
        hashTable.add(2);
        hashTable.add(3);
        hashTable.add(4);
        hashTable.add(5);
        hashTable.add(33);
        hashTable.remove(33);

        Assert.assertEquals("remove() failed",5, hashTable.size());
    }

    /**
     * this method test the contains() from HashTable.java
     */
    @Test
    public void testContains()
    {
        hashTable.add(1);
        hashTable.add(2);
        hashTable.add(3);
        hashTable.add(4);
        hashTable.add(5);
        hashTable.add(33);

        Assert.assertTrue("contains() failed", hashTable.contains(33));
    }

    /**
     * this method test the size() from HashTable.java
     */
    @Test
    public void testSize()
    {
        hashTable.add(1);
        hashTable.add(2);
        hashTable.add(3);
        hashTable.add(4);
        hashTable.add(5);
        hashTable.add(33);

        Assert.assertEquals("size() failed", 6, hashTable.size());
    }

    /**
     * this method test the isEmpty() from HashTable.java
     */
    @Test
    public void testIsEmpty()
    {
        Assert.assertEquals("isEmpty() failed", 0, hashTable.size());
    }

    /**
     * this method test the clear() from HashTable.java
     */
    @Test
    public void testClear()
    {
        hashTable.add(1);
        hashTable.add(2);
        hashTable.add(3);
        hashTable.add(4);
        hashTable.add(5);
        hashTable.add(33);

        hashTable.clear();

        Assert.assertEquals("Clear failed", 0, hashTable.size());
    }

    /**
     * this method test the get() from HashTable.java
     */
    @Test
    public void testGet()
    {
        hashTable.add(1);
        hashTable.add(2);
        hashTable.add(1);
        hashTable.add(2);
        hashTable.add(3);
        hashTable.add(33);

        Assert.assertEquals("Get failed", 33, hashTable.get(33));
    }

    /**
     * this method test the iteration from HashTable.java
     */
    @Test
    public void testIterator()
    {
        //iterator should still work with zero elements
        for (Object element : hashTable)
        {
            Assert.fail("Iterator returns an element from an empty table");
        }

        final int NUM_ELEMENTS = 5;

        //add a few items
        for (int i = 1; i <= NUM_ELEMENTS; i++)
        {
            hashTable.add(i);
        }

        //verify that we can use an iterator with the for-each loop
        int count = 1;
        for (Object element : hashTable)
        {
            Assert.assertEquals("Unexpected element found using an iterator after adding elements",
                    count, element);
            count++;
        }

        //verify that you cannot concurrently alter the structure
        try
        {
            boolean flag = true;
            for (Object element : hashTable)
            {
                if (flag)
                {
                    hashTable.add(element);
                    flag = false;
                }
            }
            Assert.fail("Concurrent modification allowed with iterator");
        }
        catch (ConcurrentModificationException ex)
        {
            //do nothing...
        }
    }
}