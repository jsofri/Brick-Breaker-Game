package brickbreaker.sprites;


import biuoop.DrawSurface;
import brickbreaker.basics.Ass7Game;
import brickbreaker.shapes.Block;
import brickbreaker.basics.Counter;
import brickbreaker.animation.GameLevel;

import java.awt.Color;


/**
 * This class is an object that follow the score in the game.
 */
public class ScoreIndicator implements Sprite {


    /**
     * these finals fix the position of the text on the gui.
     */
    private static final int FIX_X_AXIS = -25;
    private static final int FIX_Y_AXIS = 5;


    /**
     *
     */
    private Counter score;


    /**
     * constructor.
     *
     * @param counter a Counter of the game score
     */
    public ScoreIndicator(Counter counter) {
        this.score = counter;
    }

    /**
     * method draw the sprite to the screen.
     *
     * @param d DrawSurface object to draw on
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.DARK_GRAY);
        d.fillRectangle(0, 0, Ass7Game.WIDTH, GameLevel.SCORE_BOARD_THICKNESS);
        d.setColor(Color.WHITE);
        d.drawText((Ass7Game.WIDTH / 2) + FIX_X_AXIS,
                   GameLevel.SCORE_BOARD_THICKNESS / 2 + FIX_Y_AXIS,
                   "Score: " + this.score.getValue(), Block.TEXT_SIZE);
    }

    /**
     * notify the sprite that time has passed.
     */
    public void timePassed() { }
}
