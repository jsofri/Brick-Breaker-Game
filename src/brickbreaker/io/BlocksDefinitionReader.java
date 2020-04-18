package brickbreaker.io;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;


/**
 * This class get a file object and create a block factory from it.
 * It's main purpose is fromReader method.
 * final is for checkstyle reasons.
 */
public final class BlocksDefinitionReader {


    /**
     * factory holds all BlockCreator objects as defined in input.
     * defaults hold default settings in the file.
     */
    private BlocksFromSymbolsFactory factory;
    private Map<String, String>      defaults;


    /**
     * constructor.
     */
    private BlocksDefinitionReader() {
        this.defaults = new TreeMap<>();
        this.factory = new BlocksFromSymbolsFactory();
    }

    /**
     * method get an file reader and return a block factory, according to file.
     * @param reader BufferedReader to a file with block definitions
     * @return BlockFromSymbolFactory object, set blocks according to file.
     */
    public static BlocksFromSymbolsFactory fromReader(BufferedReader reader) {
        String                 line;
        BlocksDefinitionReader bdr;

        bdr = new BlocksDefinitionReader();

        try {
            while ((line = reader.readLine()) != null) {
                bdr.dealWithLine(line);
            }
            reader.close();
        } catch (IOException excpetion) {
            throw new RuntimeException("Problem in reading a line from file");
        }

        return bdr.factory;
    }

    /**
     * method get a line and send to helper methods according to it's content.
     *
     * @param line String - line that was read from a file.
     */
    private void dealWithLine(String line) {
        if (line.startsWith("default")) {
            this.setDefault(line);
        } else if (line.startsWith("bdef")) {
            this.setBlock(line);
        } else if (line.startsWith("sdef")) {
            this.setSpacer(line);
        }
    }

    /**
     * method set the default map.
     *
     * @param line String that contain the data about default settings
     */
    private void setDefault(String line) {
        String[] strings = line.split(" ");

        //first word is "default" - irrelevant
        for (int i = 1; i < strings.length; i++) {
            this.setDefaultKeyValue(strings[i]);
        }
    }

    /**
     * method set the default pairs of key value in the map.
     *
     * @param subString String that contain a key and a value from line.
     */
    private void setDefaultKeyValue(String subString) {
        String[] strings = subString.split(":");
        String key = strings[0];
        String value = strings[1];

        this.defaults.put(key, value);
    }

    /**
     * set a spacer in the factory of object.
     *
     * @param line String with data about a spacer
     */
    private void setSpacer(String line) {
        String   symbol;
        int      width;
        String[] strings;

        strings = line.split(" ");
        symbol = strings[1].substring(strings[1].indexOf(":") + 1);
        width = Integer.parseInt(strings[2].substring(strings[2].indexOf(":")
                                                      + 1));
        this.factory.addASpacer(symbol, width);
    }

    /**
     * set a block type and add it to the factory of instance.
     * @param line String with data about a block
     */
    private void setBlock(String line) {
        BlockFromLine bfl = new BlockFromLine(line, this.defaults);
        String symbol = bfl.getSymbol();

        this.factory.addABlockCreator(symbol, bfl.getBlockCreator());
    }
}
