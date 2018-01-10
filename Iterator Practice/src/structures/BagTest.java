package structures;

import java.util.Iterator;

public class BagTest
{
    public static void main(String[] args)
    {
        Bag<String> words = new Bag<>(8);

        words.add("a");
        words.add("b");
        words.add("c");
        words.add("d");
        words.add("e");
        words.add("f");

        words.remove("c");

        //this uses our iterator
        for (String word : words)
        {
            System.out.println(word);
        }

        //the for-each loop above is converted to the
        // code segment below by the compiler
        Iterator<String> iterator = words.iterator();
        while (iterator.hasNext())
        {
            String word = iterator.next();
            System.out.println(word);
        }
    }
}
