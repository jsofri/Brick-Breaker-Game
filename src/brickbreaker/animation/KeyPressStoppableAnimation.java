package brickbreaker.animation;


import biuoop.DrawSurface;
import biuoop.KeyboardSensor;


/**
 * this class is an animation that can be stopped by pressing a key.
 */
public class KeyPressStoppableAnimation implements Animation {


    /**
     * fields of class.
     */
    private KeyboardSensor keyboard;
    private boolean        stop;
    private boolean        isAlreadyPressed;


    /**
     * constructor.
     *
     * @param sensor KeyboardSensor object
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor) {
        this.keyboard = sensor;
        this.stop = false;
        this.isAlreadyPressed = true;
    }

    /**
     * method is a helper to playOneTurn - draw all sprites on the gui.
     *
     * @param d DrawSurface object to draw onto
     */
    public void doOneFrame(DrawSurface d) {
        if (this.keyboard.isPressed(KeyboardSensor.SPACE_KEY)) {
            if (!isAlreadyPressed) {
                this.stop = true;
            }
        } else {
            this.isAlreadyPressed = false;
        }
    }

    /**
     * this method check the condition of the animation loop.
     *
     * @return boolean - true if loop should stop
     */
    public boolean shouldStop() {
        return this.stop;
    }
}
