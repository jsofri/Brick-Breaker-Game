package brickbreaker.io;


import brickbreaker.shapes.BlockBuilder;
import brickbreaker.shapes.BlockCreator;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;


/**
 * this class get a string and make a block creator object from data in string.
 */
public class BlockFromLine {


    /**
     * fields keep the data written in the input line.
     */
    private int                  height;
    private int                  width;
    private int                  hitPoints;
    private String               symbol;
    private Color                stroke;
    private Map<Integer, Filler> fillers;
    private Map<String, String>  defaults;


    /**
     * constructor.
     *
     * @param line String - line with data of a block definition
     * @param defaults a map of Strings with default settings for block
     */
    public BlockFromLine(String line, Map<String, String> defaults) {
        this.defaults = defaults;
        this.fillers = new TreeMap<>();

        this.setFields(line);
    }

    /**
     * getter.
     *
     * @return symbol of the block
     */
    public String getSymbol() {
        return this.symbol;
    }

    /**
     * method returns an object if all fields are defined.
     *
     * @return a BlockCreator object
     */
    public BlockCreator getBlockCreator() {
        if (!this.allSet()) {
            throw new RuntimeException("Cannot define a block from this line");
        }

        return new BlockBuilder(this.fillers, this.hitPoints, this.width,
                                this.height, this.stroke);
    }

    /**
     * method get a String and set fields of instance.
     * firstly, set from the default Map, secondly, set from data in line.
     *
     * @param line String - line with definitions for block
     */
    private void setFields(String line) {
        String[] strings = line.split(" ");

        this.setFromDefault();

        //strings[0] is "bdef"
        for (int i = 1; i < strings.length; i++) {
            this.set(strings[i]);
        }
        this.resetFillers();
    }

    /**
     * method run through all key of default map and set instance fields from.
     */
    private void setFromDefault() {
        for (String key : this.defaults.keySet()) {
            this.store(key, this.defaults.get(key));
        }
    }

    /**
     * get a String that contains a key and a value and make it a field.
     *
     * @param subString String that contains a key and a value in it
     */
    private void set(String subString) {
        String[] array = subString.split(":");
        String   key = array[0];
        String   value = array[1];

        this.store(key, value);
    }

    /**
     * get a key and a value and store them in an object's field.
     *
     * @param key String - name of the field
     * @param value String - content of field
     */
    private void store(String key, String value) {
        if (key.contains("height")) {
            this.height = Integer.parseInt(value);
        } else if (key.contains("width")) {
            this.width = Integer.parseInt(value);
        } else if (key.contains("symbol")) {
            this.symbol = value;
        } else if (key.contains("hit_points")) {
            this.hitPoints = Integer.parseInt(value);
        } else if (key.contains("fill")) {
            this.handleFiller(key, value);
        } else if (key.contains("stroke")) {
            this.setStroke(value);
        }
    }

    /**
     * methodset a filler in the correct position of the fillers Map.
     * @param key String, might be "fill" and might be "fill-k" (k is int)
     * @param value String, settings of color
     */
    private void handleFiller(String key, String value) {
        Integer index;
        Filler  filler;

        if (key.equals("fill")) {
            index = 0;
        } else {
            String[] strings = key.split("-");

            index = Integer.parseInt(strings[1]);
        }

        filler = getFill(value);
        this.fillers.put(index, filler);
    }

    /**
     * method get a String and returns a Filler object.
     * there are 2 posiible fillers - imageFiller and a ColorFiller.
     * method is static because it is used by LevelSpecificationReader.
     *
     * @param data String with definition for a filler
     * @return appropriate Filler object, depending on he input
     */
    public static Filler getFill(String data) {
        if (data.startsWith("color")) {
            Color color = ColorFromString.colorFromString(data);

            return new ColorFiller(color);
        }

        //filler is an image
        return getImageFiller(data);
    }

    /**
     * method get a string with a name of file and returns an imageFiller.
     * @param line String that contains a name of an image file
     * @return Filler object, according to data in the input String
     */
    public static Filler getImageFiller(String line) {
        String        fileName;
        Image         img;
        ImageFiller   imageFiller;

        //6 is the position of the char after "color("
        fileName = line.substring(6, line.length() - 1);

        try {
            img = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(fileName));
            imageFiller = new ImageFiller(img);
        } catch (IOException e) {
            throw new RuntimeException("Problem in reading image from file");
        }

        return imageFiller;
    }

    /**
     * method get a string with data about a color and set the object's stroke.
     *
     * @param string String with data about a color
     */
    private void setStroke(String string) {
        this.stroke = ColorFromString.colorFromString(string);
    }

    /**
     * adjust the values of the map of the fillers according to hit points.
     */
    private void resetFillers() {
        for (Integer num : this.fillers.keySet()) {
            if (num > this.hitPoints) {
                this.fillers.remove(num);
            }
        }
    }

    /**
     * check that all the fields are set and definitions are good to go.
     *
     * @return boolean - true if all is set
     */
    private boolean allSet() {
        if ((this.symbol != null && !this.symbol.isEmpty())
                && (this.height > 0) && (this.width > 0)
                && (this.hitPoints > 0) && (this.fillers.size() > 0)) {

            return true;
        }

        return false;
    }


}
