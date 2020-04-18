package brickbreaker.animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import brickbreaker.basics.Ass7Game;
import brickbreaker.basics.Counter;
import brickbreaker.io.BlockFromLine;
import brickbreaker.io.Filler;
import static brickbreaker.sprites.MenuBackground.IMAGE_OF_MENU;


/**
 * this Class runs animation of a screen of game over for a loser player.
 */
public class GameOverScreen implements Animation {

    /**
     * details about printing a sentence on a DrawSurface object.
     * and the opening of the String needed to be on screen.
     */
    static final int    START_OF_X = 65;
    static final int    START_OF_Y = 400;
    static final int    TEXT_SIZE = 52;
    static final String HALF_MESSAGE = "Game Over. Your score is ";



    /**
     * message is the message that'll be on screen.
     */
    private String                     message;
    private KeyPressStoppableAnimation kpsAnimation;
    private Filler                     background;



    /**
     * Constructor.
     *
     * @param keyboardSensor KeyBoardSensor object
     * @param score Counter of the final score
     */
    public GameOverScreen(KeyboardSensor keyboardSensor, Counter score) {
        this.setMessage(score);
        this.kpsAnimation = new KeyPressStoppableAnimation(keyboardSensor);
        this.background = BlockFromLine.getFill(IMAGE_OF_MENU);
        this.background.setPosition(0, 0, Ass7Game.WIDTH, Ass7Game.HEIGHT);
    }

    /**
     * method set the string of the game score.
     *
     * @param score Counter of the score
     */
    private void setMessage(Counter score) {
        Integer number = score.getValue();

        this.message = HALF_MESSAGE + number.toString();
    }

    @Override
    public void doOneFrame(DrawSurface ds) {
        this.kpsAnimation.doOneFrame(ds);
        this.background.drawOn(ds);
        ds.setColor(Ass7Game.TEXT_COLOR);
        ds.drawText(START_OF_X, START_OF_Y, this.message, TEXT_SIZE);
    }

    @Override
    public boolean shouldStop() {
        return this.kpsAnimation.shouldStop();
    }
}
