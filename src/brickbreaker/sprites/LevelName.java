package brickbreaker.sprites;

import biuoop.DrawSurface;
import brickbreaker.basics.Ass7Game;
import brickbreaker.shapes.Block;
import brickbreaker.animation.GameLevel;
import java.awt.Color;


/**
 * this class is a sprite that holds a level name.
 */
public class LevelName implements Sprite {


    /**
     * to place text in the DrawSurface.
     */
    private static final int FIX_PLACE = 5;


    /**
     * gameLevel of the instance.
     */
    private GameLevel gameLevel;


    /**
     * constructor.
     *
     * @param gameLevel GameLevel object - contain a field of a name
     */
    public LevelName(GameLevel gameLevel) {
        this.gameLevel = gameLevel;
    }

    /**
     * method draw the sprite to the screen.
     *
     * @param d DrawSurface object to draw on
     */
    public void drawOn(DrawSurface d) {
        String levelName = this.gameLevel.getLevelInformation().levelName();

        d.setColor(Color.white);
        d.drawText((Ass7Game.WIDTH - 250),
                   (GameLevel.SCORE_BOARD_THICKNESS / 2) + FIX_PLACE,
                   "Level Name: " + levelName, Block.TEXT_SIZE);
    }

    /**
     * notify the sprite that time has passed.
     */
    public void timePassed() {

    }
}
