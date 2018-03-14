package hash_tables;

public class Clown
{
    private String name;
    private int popularity; //scale of ten, 1 (best) - 10 (worst)

    public Clown(String name, int popularity)
    {
        this.name = name;
        this.popularity = popularity;
    }

    @Override
    public int hashCode()
    {
        int value = 0;

        for (int i = 0; i < name.length(); i++)
        {
            char character = name.charAt(i);

            value += character;
        }

        return popularity + value;
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == null)
        {
            return false;
        }
        else if (other == this)
        {
            return true;
        }
        else if (!this.getClass().equals(other.getClass()))
        {
            return false;
        }
        else
        {
            Clown otherClown = (Clown)other;

            return name.equals(otherClown.name) &&
                    popularity == otherClown.popularity;
        }
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getPopularity()
    {
        return popularity;
    }

    public void setPopularity(int popularity)
    {
        this.popularity = popularity;
    }

    @Override
    public String toString()
    {
        return "Clown{" +
                "name='" + name + '\'' +
                ", popularity=" + popularity +
                '}';
    }
}