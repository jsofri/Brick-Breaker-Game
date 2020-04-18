package brickbreaker.animation;


import biuoop.DrawSurface;


/**
 * this interface is for classes that make animation.
 */
public interface Animation {


    /**
     * method is a helper to playOneTurn - draw all sprites on the gui.
     *
     * @param d DrawSurface object to draw onto
     */
    void doOneFrame(DrawSurface d);

    /**
     * this method check the condition of the animation loop.
     *
     * @return boolean - true if loop should stop
     */
    boolean shouldStop();
}
