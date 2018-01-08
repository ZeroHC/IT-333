package structures;

public class Bag<T>
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

        return false;

    }

    public void remove(T element)
    {

    }

    public int size()
    {
        return size;
    }

    public boolean isFull()
    {
        return size >= data.length;
    }
}
