package brickbreaker.io;


import java.awt.Color;


/**
 * Class make colors from strings. complementary to Block definition reader.
 */
public class ColorFromString {


    /**
     * method parse color definition and return the specified color.
     *
     * @param string String with data about a certain color
     * @return Color object according to the input data
     */
    public static java.awt.Color colorFromString(String string) {

        //6 is the position of the char after "color("
        string = string.substring(6, string.length() - 1);

        if (string.startsWith("RGB")) {
            String[] strings;
            int red;
            int green;
            int blue;

            string = string.substring(4, string.length() - 1);
            strings = string.split(",");
            red = Integer.parseInt(strings[0]);
            green = Integer.parseInt(strings[1]);
            blue = Integer.parseInt(strings[2]);

            return new Color(red, green, blue);
        }

        return getColorByName(string);
    }

    /**
     * method get a string with a name of color and returs a color object.
     *
     * @param string String with a name of color
     * @return Color object, according to input string
     */
    public static java.awt.Color getColorByName(String string) {
        if (string.contains("black")) {
            return Color.black;
        } else if (string.contains("blue")) {
            return Color.blue;
        } else if (string.contains("cyan")) {
            return Color.cyan;
        } else if (string.contains("lightGray")) {
            return Color.lightGray;
        } else if (string.contains("green")) {
            return Color.green;
        } else if (string.contains("gray")) {
            return Color.gray;
        } else if (string.contains("orange")) {
            return Color.orange;
        } else if (string.contains("pink")) {
            return Color.pink;
        } else if (string.contains("red")) {
            return Color.red;
        } else if (string.contains("white")) {
            return Color.white;
        } else if (string.contains("yellow")) {
            return Color.yellow;
        }

        throw new RuntimeException("Couldn't find color in block definition");
    }
}
