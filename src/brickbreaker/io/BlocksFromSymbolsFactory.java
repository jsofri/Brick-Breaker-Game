package brickbreaker.io;


import brickbreaker.shapes.Block;
import brickbreaker.shapes.BlockCreator;
import java.util.Map;
import java.util.TreeMap;


/**
 * this Class is a factory of blocks from symbols.
 * it gets a symbol and return a block.
 */
public class BlocksFromSymbolsFactory {


    /**
     * Maps hold the data behind each string representation of a String.
     */
    private Map<String, Integer>      spacerWidths;
    private Map<String, BlockCreator> blockCreators;


    /**
     * constructor.
     */
    public BlocksFromSymbolsFactory() {
        this.spacerWidths = new TreeMap<>();
        this.blockCreators = new TreeMap<>();
    }

    /**
     * method returns a boolean according to the being in object's spacers Map.
     *
     * @param s String - a potential key
     * @return boolean true if 's' is a valid space symbol.
     */
    public boolean isSpaceSymbol(String s) {
        if (this.spacerWidths.containsKey(s)) {
            return true;
        }

        return false;
    }

    /**
     * get string and return a boolean according to it's presence in the.
     * blockCreators Map.
     *
     * @param s String - a potential key
     * @return boolean true if 's' is a valid block symbol.
     */
    public boolean isBlockSymbol(String s) {
        if (this.blockCreators.containsKey(s)) {
            return true;
        }

        return false;
    }

    /**
     * method get a string that is a potential symbol of a block.
     * Return a block according to the definitions associated with symbol s.
     * The block will be located at position (xpos, ypos).
     *
     * @param s String of a block symbol
     * @param xpos int, x coordinate in gui
     * @param ypos int, y coordinate in gui
     * @return a Block instance
     */
    public Block getBlock(String s, int xpos, int ypos) {
        return this.blockCreators.get(s).create(xpos, ypos);
    }

    /**
     * method get a potential symbol of a spacer.
     * method returns the width in pixels associated with the input.
     *
     * @param s String of a spacer symbol
     * @return int, width of spacer
     */
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }

    /**
     * add a spacer to the instance map of spacers.
     *
     * @param string String, symbol of a new spacer
     * @param num int, width of the new spacer
     */
    public void addASpacer(String string, int num) {
        this.spacerWidths.put(string, new Integer(num));
    }

    /**
     * add a blockCreator to the instance map of blockCreator.
     * @param string String, symbol of a new blockCreator
     * @param blockCreator BlockCreator object associated to the symbol
     */
    public void addABlockCreator(String string, BlockCreator blockCreator) {
        this.blockCreators.put(string, blockCreator);
    }
}
