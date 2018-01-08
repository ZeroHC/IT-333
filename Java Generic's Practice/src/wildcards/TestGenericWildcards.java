package wildcards;

import java.awt.*;
import java.util.ArrayList;

public class TestGenericWildcards
{
    public static void main(String[] args)
    {
        ArrayList<Car> cars = new ArrayList<>();
        ArrayList<Shape> shapes = new ArrayList<>();

        //add a few elements...

        //this will not work because generic classes are not covariant
        //public static void colorize(ArrayList<IColorable> things)

        colorize(cars);
        colorize(shapes);
    }

    public static void colorize(ArrayList<? extends IColorable> things)
    {
        //color an element
        things.get(0).setColor(Color.BLUE);
    }
}
