package brickbreaker.io;


import biuoop.DrawSurface;
import java.awt.Color;


/**
 * this class implements filler, it's purpose is to be drawn on DrawSurfaces.
 * Color filler is a sprite that draw a colored rectangle on a DrawSurface.
 */
public class ColorFiller implements Filler {


    /**
     * data of object.
     */
    private Color color;
    private int xCor;
    private int yCor;
    private int width;
    private int height;


    /**
     * constructor.
     *
     * @param color Color object to set for object.
     */
    public ColorFiller(Color color) {
        this.color = color;
    }

    /**
     * method set position of an object.
     * this way, each time drawOn() is called all the data already exists.
     * names of parameters are for checkstyle reasons.
     *
     * @param x int, upper left point x coordinate
     * @param y int, upper left point y coordinate
     * @param wide int, width of object
     * @param high int, height of object
     */
    public void setPosition(int x, int y, int wide, int high) {
        this.xCor = x;
        this.yCor = y;
        this.width = wide;
        this.height = high;
    }

    @Override
    public Filler clone() {
        return new ColorFiller(this.color);
    }

    @Override
    public void drawOn(DrawSurface ds) {
        ds.setColor(this.color);
        ds.fillRectangle(this.xCor, this.yCor, this.width, this.height);
    }

    @Override
    public void timePassed() { }
}
