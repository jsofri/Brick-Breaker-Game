package brickbreaker.listeners;


import brickbreaker.basics.Counter;
import brickbreaker.shapes.Block;
import brickbreaker.shapes.Ball;


/**
 * This is a listener to a Counter of scores in a game.
 */
public class ScoreTrackingListener implements HitListener {


    /**
     * these fields are the addition to a counter when hitting a block.
     */
    private static final int HIT_A_BLOCK = 5;
    private static final int KILL_A_BLOCK = 10;


    /**
     * this counter is the score of a game object.
     */
    private Counter currentScore;


    /**
     * constructor.
     *
     * @param scoreCounter the Counter to attach to the new instance.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * this method is callec when a block is hitted and change the score.
     *
     * @param beingHit the Block being hit
     * @param hitter  the Ball that is hitting the Block
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getNumber() <= 0) {
            this.destractionScore();
            return;
        }

        this.hitScore();
    }

    /**
     * @return Counter onject - the current score
     */
    public Counter getCurrentScore() {
        return this.currentScore;
    }

    /**
     * this method is a helper to hitEvent - add points of hitting a block.
     */
    private void hitScore() {
        this.currentScore.increase(HIT_A_BLOCK);
    }

    /**
     * this method is a helper to hitEvent - add points when a block is killed.
     */
    private void destractionScore() {
        this.currentScore.increase(KILL_A_BLOCK);
    }
}
