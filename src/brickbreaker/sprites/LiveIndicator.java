package brickbreaker.sprites;

import biuoop.DrawSurface;
import brickbreaker.shapes.Block;
import brickbreaker.basics.Counter;
import brickbreaker.animation.GameLevel;

import java.awt.Color;


/**
 * This class is an object that follow the number of lives in a game.
 */
public class LiveIndicator implements Sprite {


    /**
     * fix place is for placing text correctly in a gui.
     */
    private static final int FIX_PLACE = 5;


    /**
     * this counter holds object's number of lives.
     */
    private Counter lives;


    /**
     * constructor.
     *
     * @param counter the counter to attach to this object
     */
    public LiveIndicator(Counter counter) {
        this.lives = counter;
    }

    /**
     * method draw the sprite to the screen.
     *
     * @param d DrawSurface object to draw on
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.WHITE);
        d.drawText(Block.BLOCK_THICKNESS,
                  (GameLevel.SCORE_BOARD_THICKNESS / 2) + FIX_PLACE,
                   "Lives: " + this.lives.getValue(), Block.TEXT_SIZE);
    }

    /**
     * notify the sprite that time has passed.
     */
    public void timePassed() { }

    /**
     * @return Counter of number of lives
     */
    public Counter getLives() {
        return this.lives;
    }
}
