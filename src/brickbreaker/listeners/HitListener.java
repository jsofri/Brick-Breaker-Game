package brickbreaker.listeners;


import brickbreaker.shapes.Ball;
import brickbreaker.shapes.Block;

/**
 * This interface is for objects that want to be listeners of hit events.
 */
public interface HitListener {


    /**
     * This method is called whenever the beingHit object is hit.
     *
     * @param beingHit the block that is being hitted
     * @param hitter  the Ball that's doing the hitting
     */
    void hitEvent(Block beingHit, Ball hitter);
}
