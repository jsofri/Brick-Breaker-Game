package brickbreaker.io;


import brickbreaker.sprites.Sprite;


/**
 * this interface is for drawing. this can be done in 2 ways.
 * Using an image or filling a rectangle using a fill color.
 */
public interface Filler extends Sprite, Cloneable {

    /**
     * set location of object.
     *
     * @param x int, upper left point x coordinate
     * @param y int, upper left point y coordinate
     * @param width int, width of object
     * @param height int, height of object
     */
    void setPosition(int x, int y, int width, int height);

    /**
     * get a clone of object.
     *
     * @return Filler - a clone of instance
     */
    Filler clone();
}
