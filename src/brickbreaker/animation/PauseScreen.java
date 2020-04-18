package brickbreaker.animation;


import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import brickbreaker.basics.Ass7Game;
import brickbreaker.io.BlockFromLine;
import brickbreaker.io.Filler;

import static brickbreaker.sprites.MenuBackground.IMAGE_OF_MENU;


/**
 * this class makes a pause screen.
 */
public class PauseScreen implements Animation {


    /**
     * details about printing a sentence on a DrawSurface object.
     */
    static final int START_OF_X = 100;
    static final int START_OF_Y = 50;
    static final int TEXT_SIZE = 32;


    /**
     * that's the String needed to be on screen.
     */
    static final String MESSAGE = "Game Paused - press space to continue";


    /**
     * data of class.
     */
    private KeyPressStoppableAnimation kpsAnimation;
    private Filler                     background;


    /**
     * Constructor.
     *
     * @param keyboardSensor KeyBoardSensor object
     */
    public PauseScreen(KeyboardSensor keyboardSensor) {
        this.kpsAnimation = new KeyPressStoppableAnimation(keyboardSensor);
        this.background = BlockFromLine.getFill(IMAGE_OF_MENU);
        this.background.setPosition(0, 0, Ass7Game.WIDTH, Ass7Game.HEIGHT);
    }

    @Override
    public void doOneFrame(DrawSurface ds) {
        this.kpsAnimation.doOneFrame(ds);
        this.background.drawOn(ds);
        ds.setColor(Ass7Game.TEXT_COLOR);
        ds.drawText(START_OF_X, START_OF_Y, MESSAGE, TEXT_SIZE);
    }

    @Override
    public boolean shouldStop() {
        return this.kpsAnimation.shouldStop();
    }
}
