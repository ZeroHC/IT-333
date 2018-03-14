package hash_tables;

import java.util.Random;

public class TestHashTable
{
    public static void main(String[] args)
    {
        //create a few objects
        Clown bozo = new Clown("Bozo", 1);
        Clown[] clowns = {new Clown("Bozo", 1),
                new Clown("Coco", 2),
                new Clown("Bobo", 1)};
        HashTable<Clown> table = new HashTable<>();

        //add them to the table
        for (Clown clown : clowns)
        {
            table.add(clown);
        }

        clowns[0].setName("Bozotto");

        //print them out
        for (Clown clown : table)
        {
            System.out.println(clown);
        }

        //check whether our elements are present
        for (Clown clown : clowns)
        {
            System.out.println(table.contains(clown));
        }
        System.out.println(table.contains(bozo));
    }

    private static void practiceWithHashTables()
    {
        Random random = new Random();

        HashTable<Integer> numberTable = new HashTable<>();

        for (int i = 1; i <= 100; i++)
        {
            numberTable.add(random.nextInt(1000));
        }

        numberTable.printUsedSpaces();

        for (int i = 0 ; i < 1000; i++)
        {
            System.out.println(i + ": " + numberTable.contains(i));
        }

        for (int i = 0; i < 500; i++)
        {
            numberTable.remove(i);
        }

        numberTable.printUsedSpaces();

        for (int i = 0 ; i < 1000; i++)
        {
            System.out.println(i + ": " + numberTable.contains(i));
        }

        for (int element : numberTable)
        {
            System.out.println(element);
        }
    }
}