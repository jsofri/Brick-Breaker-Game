package brickbreaker.animation;


import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;


/**
 * This class takes an animation object and run it.
 */
public class AnimationRunner {


    /**
     * static field is relevant for Sleeper times.
     */
    static final int FRAMES_PER_SECOND = 60;
    static final int MILLISECONDES_IN_SECOND = 1000;


    /**
     * GUI is the window that the nimation run onto.
     */
    private GUI gui;


    /**
     * constructor.
     *
     * @param gui GUI object - the animation will be on it
     */
    public AnimationRunner(GUI gui) {
        this.gui = gui;
    }

    /**
     * @return GUI object of this instance.
     */
    public GUI getGui() {
        return this.gui;
    }

    /**
     * method run animation loop of an animation object.
     *
     * @param animation the Animation object to run it's animation
     */
    public void run(Animation animation) {
        int millisecondsPerFrame = MILLISECONDES_IN_SECOND / FRAMES_PER_SECOND;
        Sleeper sleeper = new Sleeper();

        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = this.getGui().getDrawSurface();

            animation.doOneFrame(d);

            this.getGui().show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}
