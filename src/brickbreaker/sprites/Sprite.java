package brickbreaker.sprites;


import biuoop.DrawSurface;


/**
 * this interface is used by objects that are drawn to a DrawSurface in game.
 */
public interface Sprite {


    /**
     * method draw the sprite to the screen.
     *
     * @param d DrawSurface object to draw on
     */
    void drawOn(DrawSurface d);

    /**
     * notify the sprite that time has passed.
     */
    void timePassed();
}
