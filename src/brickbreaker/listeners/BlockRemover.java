package brickbreaker.listeners;


import brickbreaker.basics.Counter;
import brickbreaker.shapes.Block;
import brickbreaker.shapes.Ball;
import brickbreaker.animation.GameLevel;


/**
 * a BlockRemover is in charge of removing blocks from the gameLevel.
 * also, it's in charge of keeping count of the number of blocks that remain.
 */
public class BlockRemover implements HitListener {


    /**
     * an object is attached to a gameLevel and holds a counter
     * counter counts the number of remaining blocks.
     */
    private GameLevel gameLevel;
    private Counter remainingBlocks;


    /**
     * constructor.
     *
     * @param gameLevel GameLevel object - so instance could remove blocks
     * @param removedBlocks a Counter object
     */
    public BlockRemover(GameLevel gameLevel, Counter removedBlocks) {
        this.gameLevel = gameLevel;
        this.remainingBlocks = removedBlocks;
    }

    /**
     * method remove blocks that have 0 points from the gameLevel.
     * @param beingHit the Block that's being hit
     * @param hitter  the Ball that's doing the hitting
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        int number = beingHit.getNumber();

        if (number <= 0) {

            //remove this listener from the block that is being removed
            beingHit.removeFromGame(this.getGameLevel());
            beingHit.removeHitListener(this);
            this.getRemainingBlocks().decrease(1);
            beingHit.setNumber();
        }
    }

    /**
     * getter.
     *
     * @return Counter of this instance
     */
    private Counter getRemainingBlocks() {
        return this.remainingBlocks;
    }

    /**
     * getter.
     *
     * @return GameLevel object of this instance
     */
    private GameLevel getGameLevel() {
        return this.gameLevel;
    }
}
