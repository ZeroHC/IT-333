package hash_tables;

import java.util.Random;

public class TestHashTable
{
    public static void main(String[] args)
    {
        Random rand = new Random();

        HashTable<Integer> numberTable = new HashTable<>();

        for (int i = 0; i < 100; i++)
        {
            numberTable.add(rand.nextInt(1000));
        }

        numberTable.printUsedSpace();

        for (int i = 0; i < 1000; i ++)
        {
            System.out.println(i + ": " + numberTable.contains(i));
        }

        for (int i = 0; i < 500; i++)
        {
            numberTable.remove(i);
        }

        numberTable.printUsedSpace();
    }
}
