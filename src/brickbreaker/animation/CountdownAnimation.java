package brickbreaker.animation;


import biuoop.DrawSurface;
import brickbreaker.basics.Ass7Game;
import brickbreaker.basics.Counter;
import brickbreaker.sprites.SpriteCollection;
import java.awt.Color;


/**
 * this class is a countdown animation.
 */
public class CountdownAnimation implements Animation {


    /**
     * static fields relevant for writing text.
     */
    private static final int TEXT_SIZE = 300;
    private static final int X_PLACE = Ass7Game.WIDTH / 2 - 100;
    private static final int Y_PLACE = 300;


    /**
     * gameScreen is all the sprites needed to be drawn on DrawSurface.
     */
    private double           numOfSeconds;
    private int              countFrom;
    private SpriteCollection gameScreen;
    private Counter          countDown;
    private Counter          timer;


    /**
     * Constructor.
     *
     * @param numOfSeconds double, #seconds to display each digit in countdown.
     * @param countFrom int, the first number in the countdown
     * @param gameScreen SpriteCollection of all the sprites needed to be shown
     */
    public CountdownAnimation(double numOfSeconds, int countFrom,
                              SpriteCollection gameScreen) {
        this.numOfSeconds = numOfSeconds;
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        this.countDown = new Counter(countFrom);
        this.setTimer();
    }

    /**
     * method draw One Frame on gui.
     *
     * @param d DrawSurface object to draw onto
     */
    public void doOneFrame(DrawSurface d) {
        this.getTimer().decrease();
        this.gameScreen.drawAllOn(d);
        this.drawCounter(d);

        if (this.getTimer().getValue() <= 0) {
            this.getCountDown().decrease();
            this.setTimer();
        }
    }

    /**
     * this method check the condition of the animation loop.
     *
     * @return boolean - true if loop should stop
     */
    public boolean shouldStop() {
        if (this.getCountDown().getValue() <= 0) {
            return true;
        }

        return false;
    }

    /**
     *draw the counter value on the screen.
     *
     * @param ds DrawSurface object to draw onto
     */
    private void drawCounter(DrawSurface ds) {
        Integer count = this.getCountDown().getValue();

        ds.setColor(Color.YELLOW);
        ds.drawText(X_PLACE, Y_PLACE, count.toString(), TEXT_SIZE);
    }

    /**
     * setter fot timer field (Counter).
     */
    private void setTimer() {
        double msForNum = (numOfSeconds * AnimationRunner.FRAMES_PER_SECOND)
                           / countFrom;

        this.timer = new Counter((int) (msForNum + 0.5));
    }

    /**
     * @return Counter, timer field
     */
    private Counter getTimer() {
        return this.timer;
    }

    /**
     * @return Counter of countDown
     */
    private Counter getCountDown() {
        return this.countDown;
    }
}
