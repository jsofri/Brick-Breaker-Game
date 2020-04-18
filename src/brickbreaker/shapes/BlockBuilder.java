package brickbreaker.shapes;

import brickbreaker.io.Filler;

import java.awt.Color;
import java.util.Map;
import java.util.TreeMap;


/**
 * This class is an implementation for block creator interface.
 * it gets data and return a block creator object.
 */
public class BlockBuilder implements BlockCreator {


    /**
     * data of instance.
     */
    private int                  height;
    private int                  width;
    private int                  hitPoints;
    private Color                stroke;
    private Map<Integer, Filler> fillers;


    /**
     * constructor.
     *
     * @param fillers Map of fillers of block
     * @param hitPoints int - number of hit points of block
     * @param width int - width of block
     * @param height int - width of block
     * @param stroke Color of stroke
     */
    public BlockBuilder(Map<Integer, Filler> fillers, int hitPoints,
                        int width, int height, Color stroke) {
        this.height = height;
        this.width = width;
        this.hitPoints = hitPoints;
        this.stroke = stroke;
        this.fillers = fillers;
    }

    @Override
    public Block create(int xpos, int ypos) {
        Map<Integer, Filler> copy = this.makeFillers(xpos, ypos);

        return new Block(xpos, ypos, this.width, this.height, this.hitPoints,
                         this.stroke, copy);
    }

    //

    /**
     * method create a map copy and set position of each filler.
     *
     * @param xpos int - x coordinate of filler
     * @param ypos int - y coordinate of filler
     * @return a Map of setted Fillers
     */
    private Map<Integer, Filler> makeFillers(int xpos, int ypos) {
        Map<Integer, Filler> copy = new TreeMap<>();
        Filler tmpFiller;


        for (Integer key : this.fillers.keySet()) {
            tmpFiller = this.fillers.get(key).clone();
            tmpFiller.setPosition(xpos, ypos, this.width, this.height);
            copy.put(key, tmpFiller);
        }

        return copy;
    }
}
