package generics;

public class TestData
{
    public static void main(String[] args)
    {
        DataStorage<String> word = new DataStorage<>("Hello");
        DataStorage<Boolean> flag = new DataStorage<>(true);

        System.out.println(word.getData());
        System.out.println(flag.getData());
    }
}