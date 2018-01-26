package sorted;

import java.util.Random;

public class TestSortedList
{
    public static void main(String[] args)
    {
        SortedLinkedList list = new SortedLinkedList();
        Random random = new Random();

        for (int i = 1; i <= 100; i++)
        {
            list.add(random.nextInt(40) + 1);
        }

        list.print();
    }
}