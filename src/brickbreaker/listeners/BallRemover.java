package brickbreaker.listeners;


import brickbreaker.shapes.Ball;
import brickbreaker.shapes.Block;
import brickbreaker.animation.GameLevel;


/**
 * This object is a listener that remove balls when it's needed.
 */
public class BallRemover implements HitListener {


    /**
     * the gameLevel that the object is attached to.
     */
    private GameLevel gameLevel;


    /**
     * constructor.
     *
     * @param gameLevel GameLevel object to link this object to
     */
    public BallRemover(GameLevel gameLevel) {
        this.gameLevel = gameLevel;
    }

    /**
     * This method is called whenever the implementing object is hit by ball.
     *
     * @param beingHit the object that is being hit
     * @param hitter  the Ball that's doing the hitting
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.gameLevel);
        this.gameLevel.getBallCounter().decrease();
    }
}
