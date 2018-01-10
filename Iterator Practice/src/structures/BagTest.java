package structures;

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
    }
}
