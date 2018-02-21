package comparing_objects;

public class ColoredPencil implements Comparable<ColoredPencil>
{
    private String color;
    private int pointSize;
    private double price;

    public ColoredPencil(String color, int pointSize, double price)
    {

        this.color = color;
        this.pointSize = pointSize;
        this.price = price;
    }

    @Override
    public int compareTo(ColoredPencil other)
    {
        int colorComparison = this.color.compareTo(other.color);

        if (colorComparison == 0)
        {
            return this.pointSize - other.pointSize;
        }
        else
        {
            return colorComparison;
        }
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public int getPointSize()
    {
        return pointSize;
    }

    public void setPointSize(int pointSize)
    {
        this.pointSize = pointSize;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    @Override
    public String toString()
    {
        return "ColoredPencil{" + "color='" + color + '\'' + ", pointSize=" + pointSize + ", price=" + price + '}';
    }
}
